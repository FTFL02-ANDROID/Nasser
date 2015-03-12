package com.ftfl.icare.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ftfl.icare.R;
import com.ftfl.icare.database.ImportantNoteDataSource;
import com.ftfl.icare.util.ImportantNotes;

public class FragmentViewNote extends Fragment {

	TextView mTvTitle = null;
	TextView mTvSubject = null;
	TextView mTvDescription = null;

	public FragmentViewNote() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_view_note,
				container, false);
		getActivity().invalidateOptionsMenu();
		mTvTitle = (TextView) view.findViewById(R.id.tvViewNoteTitle);
		mTvSubject = (TextView) view.findViewById(R.id.tvViewNoteSubject);
		mTvDescription = (TextView) view
				.findViewById(R.id.tvViewNoteDescription);

		String noteId = getArguments().getString("noteid");
		int lNoteId = Integer.parseInt(noteId);
		ImportantNoteDataSource noteDS = new ImportantNoteDataSource(
				getActivity());
		ImportantNotes note = noteDS.singleNoteData(lNoteId);

		mTvTitle.setText(note.getTitle());
		mTvSubject.setText(note.getSubject());
		mTvDescription.setText(note.getDescription());

		return view;
	}
}
