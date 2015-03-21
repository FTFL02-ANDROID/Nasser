package com.ftfl.icare.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.ftfl.icare.HomeActivity;
import com.ftfl.icare.R;
import com.ftfl.icare.database.ImportantNoteDataSource;
import com.ftfl.icare.util.ICareConstants;
import com.ftfl.icare.util.ImportantNotes;

public class FragmentNote extends Fragment {
	ImageButton mImgBtnNewNote = null;

	List<String> noteIdList = new ArrayList<String>();
	List<String> noteTitleList = new ArrayList<String>();
	Bundle bundle = new Bundle();

	public FragmentNote() {

	}

	public void setData() {
		ImportantNoteDataSource mNoteDatasource = new ImportantNoteDataSource(
				getActivity());
		List<ImportantNotes> noteList = mNoteDatasource.notesList();

		for (int i = 0; i < noteList.size(); i++) {
			noteIdList.add(noteList.get(i).getId());
			noteTitleList.add(noteList.get(i).getTitle());
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_note, container,
				false);
		noteIdList.clear();
		noteTitleList.clear();

		setData();
		ListView listview = (ListView) view.findViewById(R.id.list_note);

		mImgBtnNewNote = (ImageButton) view
				.findViewById(R.id.button_create_note);
		mImgBtnNewNote.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				((HomeActivity) getActivity()).SelectItem(30);
			}
		});

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.row_layout_note, R.id.label, noteTitleList);

		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent,
					android.view.View view, int position, long id) {

				// FragmentViewNote.NOTE_ID = position;
				// ((HomeActivity)getActivity()).SelectItem(31);

				((HomeActivity) getActivity()).mUpdatePageName = ICareConstants.UPDATE_NOTE;
				((HomeActivity) getActivity()).mId = noteIdList.get(position);

				FragmentViewNote fragmentViewNote = new FragmentViewNote();
				bundle.putString("noteid", noteIdList.get(position));
				fragmentViewNote.setArguments(bundle);

				ICareConstants.VIEW_MENU = true;

				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();
				fragmentTransaction.replace(R.id.content_frame,
						fragmentViewNote);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();

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
