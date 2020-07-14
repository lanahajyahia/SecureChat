package com.example.securechat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.securechat.Fragments.ChatsFragment;
import com.example.securechat.Fragments.UsersFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {
    TextView username;

    FirebaseUser firebaseUser;
    DatabaseReference reference;
//    public static final String METHOD = "AES";
//    public static final String CHAR_SET_NAME = "ISO-8859-1";
//
//
//    private EditText text;
//    private ListView list;
//    private Button sendBtn;
//
//    private DatabaseReference databaseReference;
//
//    private String message; //message incrypted
//    private byte encryptionKey[] = {9, 115, 51, 86, 105, 4, -31, -23, -68, 88, 17, 20, 3, -105, 119, -53};
//    private Cipher cipher, decipher;
//    private SecretKeySpec secretKeySpec;
//
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username =findViewById(R.id.username);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user =snapshot.getValue(User.class);
                username.setText(user.getUsername());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        TabLayout tabLayout =findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        ViewPagerAdapter viewPagerAdapter =new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new ChatsFragment(),"Chats");
        viewPagerAdapter.addFragments(new UsersFragment(),"Users");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this,StartActivity.class));
            finish();
            return true;
        }
        return false;
    }
    class ViewPagerAdapter extends FragmentPagerAdapter{
        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm){
            super(fm);
            this.fragments=new ArrayList<>();
            this.titles=new ArrayList<>();
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragments(Fragment fragment,String title){
            fragments.add(fragment);
            titles.add(title);
        }

        //cr  + o

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
//    public static final String METHOD = "AES";
//    public static final String CHAR_SET_NAME = "ISO-8859-1";
//
//
//    private EditText text;
//    private ListView list;
//    private Button sendBtn;
//
//    private DatabaseReference databaseReference;
//
//    private String message; //message incrypted
//    private byte encryptionKey[] = {9, 115, 51, 86, 105, 4, -31, -23, -68, 88, 17, 20, 3, -105, 119, -53};
//    private Cipher cipher, decipher;
//    private SecretKeySpec secretKeySpec;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        init();
//        try {
//            try {
//                cipher = Cipher.getInstance(METHOD);
//                decipher = Cipher.getInstance(METHOD);
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            } catch (NoSuchPaddingException e) {
//                e.printStackTrace();
//            }
//
//            secretKeySpec = new SecretKeySpec(encryptionKey, METHOD);
//            databaseReference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    try{
//                        message = snapshot.getValue().toString();
//                        message = message.substring(1, message.length() - 1);
//
//
//
//                    String[] stringMessageArray = message.split(", ");
//                    String[] stringFinal = new String[stringMessageArray.length * 2];
//                    try{
//                        for (int i = 0; i < stringMessageArray.length; i++) {
//                            String[] stringKeyValue = stringMessageArray[i].split("=", 2);
//                            stringFinal[2 * i] = (String) android.text.format.DateFormat.format("dd-MM-YYYY", Long.parseLong(stringKeyValue[0]));
//                            stringFinal[2 * i + 1] = AESDecryption(stringKeyValue[1]);
//                        }
//                        list.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, stringFinal));
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }}catch (Exception ex){
//
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        } catch (Exception ex) {
//            Toast.makeText(MainActivity.this,"no message", Toast.LENGTH_LONG).show();
//        }
//
//    }
//
//    private void init() {
//        text = findViewById(R.id.message);
//        list = findViewById(R.id.listView);
//        sendBtn = findViewById(R.id.button);
//
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("Message");
//
//        sendBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Date date = new Date();
//                databaseReference.child(String.valueOf(date.getTime())).setValue(AESEncryption(text.getText().toString()));//long.tostring()
//                text.setText("");
//            }
//        });
//
//    }
//
////    public void sendButton(View view) {
////        Date date = new Date();
////        databaseReference.child(String.valueOf(date.getTime())).setValue(AESEncryption(text.getText().toString()));//long.tostring()
////        text.setText("");
////    }
//
//    private String AESEncryption(String string) {
//        byte[] stringByte = string.getBytes();
//        byte[] encryptedByte = new byte[stringByte.length];
//
//        try {
//            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
//            encryptedByte = cipher.doFinal(stringByte);
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        } catch (BadPaddingException e) {
//            e.printStackTrace();
//        } catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
//        }
//        String returnString = "";
//        try {
//            returnString = new String(encryptedByte, CHAR_SET_NAME);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return returnString;
//    }
//
//    private String AESDecryption(String string) throws UnsupportedEncodingException {
//
//        byte[] EncryptedByte = string.getBytes(CHAR_SET_NAME);
//        String decryptedString = string;
//
//        byte[] decryption;
//
//        try {
//            decipher.init(cipher.DECRYPT_MODE, secretKeySpec);
//            decryption = decipher.doFinal(EncryptedByte);
//            decryptedString = new String(decryption);
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        } catch (BadPaddingException e) {
//            e.printStackTrace();
//        } catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
//        }
//        return decryptedString;
//
//    }


