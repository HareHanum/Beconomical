package com.apps.harel.beconomical.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.apps.harel.beconomical.R;
import com.apps.harel.beconomical.dialogs.IncomeDialog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class IncomesFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    final static String SLASH = "/";
    final static String DOTS = ":";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    public static final int PICK_IMAGE = 2;

    String mCurrentPhotoPath;
    TextView selectCategory, date, hour;
    View linearLayout;
    ImageView cameraPicture;
    DatePickerDialog datePickerDialog;

    TimePickerDialog timePickerDialog;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_incomes, container, false);

        cameraPicture = v.findViewById(R.id.camera_picture);

        linearLayout = v.findViewById(R.id.picture_layout);
        linearLayout.setOnClickListener(this);

        selectCategory = v.findViewById(R.id.select_category_incomes);
        selectCategory.setOnClickListener(this);

        date = v.findViewById(R.id.full_date);
        date.setOnClickListener(this);

        hour = v.findViewById(R.id.hour);
        hour.setOnClickListener(this);

        initDate();
        initHour();
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.select_category_incomes:
                new IncomeDialog(getContext(), selectCategory).show();
                break;

            case R.id.full_date:
                Calendar c = Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(
                        getContext(),
                        R.style.DialogThemeGreen,
                        this,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;

            case R.id.hour:
                Calendar h = Calendar.getInstance();
                timePickerDialog = new TimePickerDialog(getContext(),
                        R.style.DialogThemeGreen,
                        this,
                        h.get(Calendar.HOUR_OF_DAY), h.get(Calendar.MINUTE),
                        DateFormat.is24HourFormat(getActivity()));
                timePickerDialog.show();
                break;

            case R.id.picture_layout:
                //Creating the instance of PopupMenu
                PopupMenu popupMenu = new PopupMenu(getContext(), cameraPicture);
                //Inflating the Popup using xml file
                popupMenu.getMenuInflater()
                        .inflate(R.menu.pop_up_menu, popupMenu.getMenu());
                //registering popup with OnMenuItemClickListener
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.navigation_take_picture) {
                            dispatchTakePictureIntent();
                        } else {
                            loadFromGallery();
                        }
                        return true;
                    }
                });
                popupMenu.show();
                break;

            default:
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        month = month + 1;
        String newDate = day + SLASH + month + SLASH + year;
        date.setText(newDate);

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        String newHour = hourOfDay + DOTS + minute;
        hour.setText(newHour);
    }

    public void initDate() {
        Calendar current = Calendar.getInstance();
        int month = current.get(Calendar.MONTH) + 1;
        String newDate = String.valueOf(current.get(Calendar.DAY_OF_MONTH)) + SLASH
                + String.valueOf(month) + SLASH
                + String.valueOf(current.get(Calendar.YEAR));
        date.setText(newDate);
    }

    public void initHour() {
        Calendar current = Calendar.getInstance();
        String newHour = String.valueOf(current.get(Calendar.HOUR_OF_DAY)) + DOTS
                + String.valueOf(current.get(Calendar.MINUTE));
        hour.setText(newHour);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void loadFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            setPic();
        } else if (requestCode == PICK_IMAGE) {
            if (data == null) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                return;
            } else {
                try {
                    InputStream inputStream = getContext().getContentResolver().openInputStream(data.getData());
                    if (inputStream != null) {
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                        Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);
                        cameraPicture.setImageBitmap(bmp);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = cameraPicture.getWidth();
        int targetH = cameraPicture.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        cameraPicture.setImageBitmap(bitmap);
    }

}
