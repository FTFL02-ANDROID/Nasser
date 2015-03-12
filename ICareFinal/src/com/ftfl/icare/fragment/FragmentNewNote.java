package com.ftfl.icare.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ftfl.icare.HomeActivity;
import com.ftfl.icare.R;
import com.ftfl.icare.database.ImportantNoteDataSource;
import com.ftfl.icare.util.ICareConstants;
import com.ftfl.icare.util.ImportantNotes;

public class FragmentNewNote extends Fragment {

	EditText mEtNoteTitle = null;
	EditText mEtNoteSubject = null;
	EditText mEtNoteDescription = null;
	String noteId = null;

	Button mBtnNoteSave = null;

	ImportantNoteDataSource mNoteDatasource = null;

	public FragmentNewNote() {

	}

	public void viewPreviousData() {
		int lId = Integer.parseInt(noteId);
		mNoteDatasource = new ImportantNoteDataSource(getActivity());
		ImportantNotes noteView = mNoteDatasource.singleNoteData(lId);
		mEtNoteTitle.setText(noteView.getTitle());
		mEtNoteSubject.setText(noteView.getSubject());
		mEtNoteDescription.setText(noteView.getDescription());

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_new_notes,
				container, false);

		mEtNoteTitle = (EditText) view.findViewById(R.id.et_note_new_title);
		mEtNoteSubject = (EditText) view.findViewById(R.id.et_note_new_subject);
		mEtNoteDescription = (EditText) view
				.findViewById(R.id.et_note_new_description);
		mBtnNoteSave = (Button) view.findViewById(R.id.btn_note_save);

		noteId = getArguments().getString(ICareConstants.NOTE_ID);

		if (noteId != null)

		{
			viewPreviousData();
		}

		mBtnNoteSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mNoteDatasource = new ImportantNoteDataSource(getActivity());
				String title = mEtNoteTitle.getText().toString();
				String subject = mEtNoteSubject.getText().toString();
				String description = mEtNoteDescription.getText().toString();

				if (!(title.equals("") || subject.equals("") || description
						.equals(""))) {

					ImportantNotes noteInsert = new ImportantNotes(title,
							subject, description,
							ICareConstants.SELECTED_PROFILE_ID);

					if (noteId != null) {
						int lId = Integer.parseInt(noteId);
						if (mNoteDatasource.updateData(lId, noteInsert) == true) {
							Toast.makeText(getActivity(),
									getString(R.string.successfully_saved),
									Toast.LENGTH_LONG).show();

							FragmentNote fragmentNote = new FragmentNote();

							FragmentManager fragmentManager = getFragmentManager();
							FragmentTransaction fragmentTransaction = fragmentManager
									.beginTransaction();
							fragmentTransaction.replace(R.id.content_frame,
									fragmentNote);
							// fragmentTransaction.addToBackStack(null);
							fragmentTransaction.commit();

						} else {
							Toast.makeText(getActivity(),
									getString(R.string.not_saved),
									Toast.LENGTH_LONG).show();

						}
					}

					else {
						if (mNoteDatasource.insert(noteInsert) == true) {
							Toast.makeText(getActivity(),
									getString(R.string.successfully_saved),
									Toast.LENGTH_LONG).show();

							// ((HomeActivity)getActivity()).SelectItem(7);

							FragmentNote fragmentNote = new FragmentNote();

							FragmentManager fragmentManager = getFragmentManager();
							FragmentTransaction fragmentTransaction = fragmentManager
									.beginTransaction();
							fragmentTransaction.replace(R.id.content_frame,
									fragmentNote);
							// fragmentTransaction.addToBackStack(null);
							fragmentTransaction.commit();
						} else {
							Toast.makeText(getActivity(),
									getString(R.string.not_saved),
									Toast.LENGTH_LONG).show();

						}
					}

				}

			}
		});

		view.setFocusableInTouchMode(true);
		view.requestFocus();
		view.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (keyCode == KeyEvent.KEYCODE_BACK) {

					((HomeActivity) getActivity()).SelectItem(5);
					return true;
				} else {
					return false;
				}
			}
		});

		return view;
	}
}
