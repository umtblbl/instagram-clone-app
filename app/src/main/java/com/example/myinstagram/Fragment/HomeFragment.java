package com.example.myinstagram.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myinstagram.Adapter.PostAdapter;
import com.example.myinstagram.Adapter.StoryAdapter;
import com.example.myinstagram.Model.Post;
import com.example.myinstagram.Model.Story;
import com.example.myinstagram.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;

    private RecyclerView recyclerViewStory;
    private StoryAdapter storyAdapter;
    private List<Story> storyList;

    private List<String> followingList;

    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewPost);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        postList = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(), postList);
        recyclerView.setAdapter(postAdapter);

        recyclerViewStory = view.findViewById(R.id.recyclerViewStory);
        recyclerViewStory.setHasFixedSize(true);
        LinearLayoutManager storyLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewStory.setLayoutManager(storyLayoutManager);
        storyList = new ArrayList<>();
        storyAdapter = new StoryAdapter(getContext(), storyList);
        recyclerViewStory.setAdapter(storyAdapter);

        progressBar = view.findViewById(R.id.progress_circular);

        checkFollowing();

        return view;
    }


    private void checkFollowing() {
        followingList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Follow")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Following");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            followingList.clear();

                for(DataSnapshot snap: dataSnapshot.getChildren()){
                    followingList.add(snap.getKey());
                }
                ReadPosts();
                ReadStory();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void ReadPosts(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               postList.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);
                    for (String id : followingList) {
                        if(post.getPublisher().equals(id)) {
                            postList.add(post);
                        }
                    }

                }
                postAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Gönderiler yüklenirken hata!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void ReadStory() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Story");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long timeCurrent = System.currentTimeMillis();
                storyList.clear();
                storyList.add(new Story("", "", 0, 0,FirebaseAuth.getInstance().getUid()));

                for (String id:followingList) {
                    int countStory = 0;
                    Story story = null;

                    for(DataSnapshot snapshot: dataSnapshot.child(id).getChildren()){
                        story = snapshot.getValue(Story.class);
                        if(timeCurrent > story.getTimeStart() && timeCurrent < story.getTimeEnd()) {
                            countStory++;
                        }
                    }
                    if(countStory > 0) {
                        storyList.add(story);

                    }
                    storyAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
