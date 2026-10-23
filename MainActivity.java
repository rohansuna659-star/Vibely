package com.vibely.social;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {
    final int blue = Color.rgb(24,119,242);
    final int ink = Color.rgb(25,35,55);
    final int muted = Color.rgb(105,115,130);
    final int bg = Color.rgb(247,249,252);
    LinearLayout root, feed;

    GradientDrawable shape(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color); d.setCornerRadius(radius);
        return d;
    }

    TextView text(String value, int size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(value); t.setTextSize(size); t.setTextColor(color);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return t;
    }

    LinearLayout column() {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        return l;
    }

    LinearLayout row() {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.HORIZONTAL);
        l.setGravity(Gravity.CENTER_VERTICAL);
        return l;
    }

    void pad(View v, int left, int top, int right, int bottom) {
        v.setPadding(left, top, right, bottom);
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().setNavigationBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        buildUi();
    }

    void buildUi() {
        root = column(); root.setBackgroundColor(bg);
        setContentView(root);

        LinearLayout header = row();
        header.setBackgroundColor(Color.WHITE);
        pad(header, 18, 12, 18, 12);
        TextView logo = text("Vibely", 29, blue, true);
        header.addView(logo, new LinearLayout.LayoutParams(0, -2, 1));
        TextView search = text("⌕", 30, ink, false); search.setGravity(Gravity.CENTER);
        header.addView(search, new LinearLayout.LayoutParams(44, 44));
        TextView msg = text("✉", 25, ink, false); msg.setGravity(Gravity.CENTER);
        header.addView(msg, new LinearLayout.LayoutParams(44, 44));
        root.addView(header);

        ScrollView scroll = new ScrollView(this);
        feed = column(); pad(feed, 0, 8, 0, 12);
        scroll.addView(feed);
        root.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));

        addStories();
        addPost("rohan.vibes", "Just enjoying the little things ✨", "RV", "🌄", "1,204", "86");
        addPost("daily.inspo", "New day. New energy. Keep going 💙", "DI", "🌊", "842", "42");
        addPost("travel.diary", "Collect moments, not things ✈️", "TD", "🏔️", "2,091", "124");

        LinearLayout nav = row();
        nav.setBackgroundColor(Color.WHITE);
        pad(nav, 8, 9, 8, 9);
        String[] labels = {"⌂\nHome", "⌕\nExplore", "＋\nCreate", "♡\nActivity", "◉\nProfile"};
        for (int i=0; i<labels.length; i++) {
            final String label = labels[i];
            TextView item = text(label, 12, i == 0 ? blue : muted, i == 0);
            item.setGravity(Gravity.CENTER);
            item.setLineSpacing(1, 1);
            nav.addView(item, new LinearLayout.LayoutParams(0, 48, 1));
            item.setOnClickListener(v -> Toast.makeText(this, label.replace("\n","") + " — demo screen", Toast.LENGTH_SHORT).show());
        }
        root.addView(nav);
    }

    void addStories() {
        LinearLayout card = column();
        card.setBackgroundColor(Color.WHITE);
        pad(card, 12, 12, 12, 12);
        HorizontalScrollView hsv = new HorizontalScrollView(this);
        hsv.setHorizontalScrollBarEnabled(false);
        LinearLayout list = row();
        String[][] people = {{"＋","Your story"},{"AM","aman"},{"SJ","simran"},{"GK","gagan"},{"MK","mehak"},{"JS","jas"}};
        int[] colors = {blue, 0xff7c5cff, 0xffff8a65, 0xff21a67a, 0xffe45b93, 0xff4f7cac};
        for (int i=0; i<people.length; i++) {
            LinearLayout person = column();
            person.setGravity(Gravity.CENTER);
            pad(person, 8, 0, 8, 0);
            TextView avatar = text(people[i][0], 18, i==0 ? blue : Color.WHITE, true);
            avatar.setGravity(Gravity.CENTER);
            avatar.setBackground(shape(i==0 ? 0xffeef5ff : colors[i], 100));
            if (i>0) avatar.setBackground(shape(colors[i], 100));
            person.addView(avatar, new LinearLayout.LayoutParams(54,54));
            TextView name = text(people[i][1], 11, ink, false);
            name.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams np = new LinearLayout.LayoutParams(-2, -2);
            np.topMargin = 5; person.addView(name, np);
            list.addView(person);
        }
        hsv.addView(list); card.addView(hsv);
        feed.addView(card);
        TextView section = text("  For you", 18, ink, true);
        pad(section, 12, 14, 12, 8); feed.addView(section);
    }

    void addPost(String username, String caption, String initials, String art, String likes, String comments) {
        LinearLayout post = column();
        post.setBackground(shape(Color.WHITE, 22));
        post.setClipToOutline(true);
        LinearLayout.LayoutParams pp = new LinearLayout.LayoutParams(-1, -2);
        pp.setMargins(12, 7, 12, 8);
        post.setLayoutParams(pp);

        LinearLayout top = row(); pad(top, 14, 12, 14, 12);
        TextView avatar = text(initials, 13, Color.WHITE, true);
        avatar.setGravity(Gravity.CENTER); avatar.setBackground(shape(blue, 60));
        top.addView(avatar, new LinearLayout.LayoutParams(40,40));
        LinearLayout info = column(); pad(info, 10, 0, 0, 0);
        info.addView(text(username, 14, ink, true));
        info.addView(text("Suggested for you", 11, muted, false));
        top.addView(info, new LinearLayout.LayoutParams(0,-2,1));
        top.addView(text("•••", 18, muted, true));
        post.addView(top);

        TextView visual = text(art, 76, ink, false);
        visual.setGravity(Gravity.CENTER);
        visual.setBackground(shape(0xffeaf2ff, 0));
        visual.setHeight(245);
        post.addView(visual);

        LinearLayout actions = row(); pad(actions, 14, 10, 14, 2);
        TextView like = text("♡", 29, ink, false);
        TextView comment = text("  ◯", 23, ink, false);
        TextView share = text("  ↗", 24, ink, false);
        TextView bookmark = text("▱", 25, ink, false); bookmark.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
        actions.addView(like);
        actions.addView(comment);
        actions.addView(share);
        actions.addView(new View(this), new LinearLayout.LayoutParams(0,1,1));
        actions.addView(bookmark);
        post.addView(actions);

        like.setOnClickListener(v -> {
            if (like.getText().toString().equals("♡")) { like.setText("♥"); like.setTextColor(0xffe64670); }
            else { like.setText("♡"); like.setTextColor(ink); }
        });
        comment.setOnClickListener(v -> Toast.makeText(this, "Comments demo", Toast.LENGTH_SHORT).show());
        share.setOnClickListener(v -> Toast.makeText(this, "Share demo", Toast.LENGTH_SHORT).show());

        TextView likesText = text("  " + likes + " likes", 13, ink, true);
        pad(likesText, 14, 2, 14, 0); post.addView(likesText);
        TextView cap = text(username + "  " + caption, 13, ink, false);
        pad(cap, 14, 4, 14, 14); post.addView(cap);
        feed.addView(post);
    }
}
