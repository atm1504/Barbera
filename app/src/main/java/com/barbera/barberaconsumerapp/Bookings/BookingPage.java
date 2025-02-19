package com.barbera.barberaconsumerapp.Bookings;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.barbera.barberaconsumerapp.CheckTermDialog;
import com.barbera.barberaconsumerapp.MapSearchActivity;
import com.barbera.barberaconsumerapp.Profile.MyCoupons;
import com.barbera.barberaconsumerapp.R;
import com.barbera.barberaconsumerapp.Utils.CartItemModel;
import com.barbera.barberaconsumerapp.Utils.CouponItem;
import com.barbera.barberaconsumerapp.Utils.InstItem;
import com.barbera.barberaconsumerapp.network_aws.JsonPlaceHolderApi2;
import com.barbera.barberaconsumerapp.network_aws.RetrofitClientInstanceBooking;
import com.barbera.barberaconsumerapp.network_aws.RetrofitClientInstanceCoupon;
import com.barbera.barberaconsumerapp.network_email.Emailer;
import com.barbera.barberaconsumerapp.network_email.JsonPlaceHolderApi;
import com.barbera.barberaconsumerapp.network_email.RetrofitClientInstance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;


import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class BookingPage extends AppCompatActivity implements CheckTermDialog.CheckTerms {
    private String userAddress;//Address string to be stored in database
    public static EditText houseAddress;
    public static boolean inMap=false;
    private Button ConfirmBooking;
    private String email,token;
    private TextView totalAmount,couponInfo;
    private TextView changeLocation;
    private int region,typesel=0;
    private Boolean checkterms = false;
    private boolean isBarberFound=false;
    private double lat, lon;
    private List<CartItemModel> sidlist;
    private TextView btype1,btype2,btype3;
    private TextView day1, day2, day3, day4, day5, day6, day7;
    private CardView tim1,tim2,tim3,tim4,tim5,tim6;
    private CardView slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9, slot10,slot11,slot12,slot13;
    private String mon1, mon2, mon3, mon4, mon5, mon6, mon7, mon, day;
    private SharedPreferences sharedPreferences;
    private int array[];
    private String OrderSummary = "",time="",gender;
    public static String finalDate;
    public static String finalTime;
    private String barberIdRet,slotRet;
    private ProgressDialog progressDialog;
    public static boolean isCouponApplied;
    public static boolean isReferApplied;
    private String bookingType = "";
    private int listPosition;
    public int BookingTotalAmount;
    private int selectedYear;
    private static EditText couponcodeEditText;
    private List<String> users;
    private TextView BookingOrders;
    private long limit;
    private CardView couponApl;
    private TextView text;
    private boolean men = false, women = false;
    private int serviceTime, slotsBooked;
    private LinearLayout male_slots, female_slots, mf_slots,time_ll,progress_booking;
    private RelativeLayout rl;
    private CheckBox checkBox;
    private Button bookInst;
    private TextView InstText;
    private String couponServiceId="";
    private int upper=-1,lower=-1,curAmount;
    private String couponName="";
//    private ImageView drop;
    private RelativeLayout calendar;
    private LinearLayout slotBtn;
    private ScrollView scrollView;
    private DayItem blist1,blist2,blist3,blist4,blist5,blist6,blist7;

    @Override
    public void extractBool(Boolean selected) {
        checkterms = selected;
        if (checkterms) {
//            Toast.makeText(getApplicationContext(), "Checked", Toast.LENGTH_SHORT).show();
            if (checkUserData()) {

//                final ProgressDialog progressDialog = new ProgressDialog(BookingPage.this);
//                progressDialog.setMessage("Hold on for a moment...");
//                progressDialog.show();
//                progressDialog.setCancelable(false);
                //Toast.makeText(getApplicationContext(),"sd",Toast.LENGTH_SHORT).show();
                if (day.equals("1")) {
                    day = "01";
                }
                if (day.equals("2")) {
                    day = "02";
                }
                if (day.equals("3")) {
                    day = "03";
                }
                if (day.equals("4")) {
                    day = "04";
                }
                if (day.equals("5")) {
                    day = "05";
                }
                if (day.equals("6")) {
                    day = "06";
                }
                if (day.equals("7")) {
                    day = "07";
                }
                if (day.equals("8")) {
                    day = "08";
                }
                if (day.equals("9")) {
                    day = "09";
                }

                String dt = mon + " " + day + ", " + selectedYear;
                finalDate = dt;
                String mn = "";

                if (mon.equals("Jul")) {
                    mn = "07";
                } else if (mon.equals("Jan")) {
                    mn = "01";
                } else if (mon.equals("Feb")) {
                    mn = "02";
                } else if (mon.equals("Mar")) {
                    mn = "03";
                } else if (mon.equals("Apr")) {
                    mn = "04";
                } else if (mon.equals("May")) {
                    mn = "05";
                } else if (mon.equals("Jun")) {
                    mn = "06";
                } else if (mon.equals("Aug")) {
                    mn = "08";
                } else if (mon.equals("Sep")) {
                    mn = "09";
                } else if (mon.equals("Oct")) {
                    mn = "10";
                } else if (mon.equals("Nov")) {
                    mn = "11";
                } else if (mon.equals("Dec")) {
                    mn = "12";
                }
                String dat = day + "-" + mn + "-" + selectedYear;
                String slot = array[1] + time;
                finalTime = array[1] + ":00";
                userAddress = houseAddress.getText().toString();
                // Toast.makeText(getApplicationContext(),userAddress,Toast.LENGTH_SHORT).show();


                //addtoDatabase();
                //addTosheet();
//                    progressDialog.dismiss();
                //Toast.makeText(getApplicationContext(), "sd", Toast.LENGTH_SHORT).show();
                Retrofit retrofit1= RetrofitClientInstanceBooking.getRetrofitInstance();
                JsonPlaceHolderApi2 jsonPlaceHolderApi21=retrofit1.create(JsonPlaceHolderApi2.class);

                ProgressDialog progressDialog = new ProgressDialog(BookingPage.this);
                progressDialog.setMessage("Loading");
                progressDialog.setCancelable(true);
                progressDialog.show();
                Log.d("priceLast",curAmount+"");
                Call<InstItem> call = jsonPlaceHolderApi21.bookSlot(new ServiceIdList(sidlist, null, null, curAmount,couponName), dat, array[1] + "", "Bearer " + token);
                call.enqueue(new Callback<InstItem>() {
                    @Override
                    public void onResponse(Call<InstItem> call, retrofit2.Response<InstItem> response) {
                        if (response.code() == 200) {
                            if (response.body().isSuccess()) {
                                if (bookingType.equals("Cart")) {
                                    Call<Void> call1 = jsonPlaceHolderApi21.deleteCart(new ServiceIdList(sidlist, null, null, curAmount,couponName), "Bearer " + token);
                                    call1.enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                                            if (response.code() == 200) {
                                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Count", MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putInt("count", 0);
                                                editor.apply();
                                            } else {

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {

                                        }
                                    });
                                }
//                                if(isBarberFound){
//                                    Toast.makeText(BookingPage.this, "Barber found!", Toast.LENGTH_SHORT).show();
//                                    Call<Void> call1=jsonPlaceHolderApi21.revertBooking(new ServiceIdList(sidlist,barberIdRet,slotRet,curAmount,couponName),"Bearer "+token);
//                                    call1.enqueue(new Callback<Void>() {
//                                    @Override
//                                    public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
//                                        if(response.code()==200){
//
//                                        }
//                                        else{
//                                            Toast.makeText(getApplicationContext(),"Could not book slot",Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<Void> call, Throwable t) {
//                                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                                }

                                progressDialog.dismiss();
                                Intent intent1 = new Intent(BookingPage.this, CongratulationsPage.class);
                                intent1.putExtra("Booking Amount", curAmount);
                                intent1.putExtra("Order Summary", OrderSummary);
                                intent1.putExtra("date", dat);
                                intent1.putExtra("slot", slot);
                                intent1.putExtra("sidlist", (Serializable) sidlist);
                                startActivity(intent1);
                                finish();

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "This slot has already been booked", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Could not book slot", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<InstItem> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
//                        BookingsActivity.bookingActivityList.clear();

                });


            }
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_booking_page);
        int count = 0;
        Retrofit retrofit= RetrofitClientInstanceCoupon.getRetrofitInstance();
        Retrofit retrofit1= RetrofitClientInstanceBooking.getRetrofitInstance();
        JsonPlaceHolderApi2 jsonPlaceHolderApi2=retrofit.create(JsonPlaceHolderApi2.class);
        JsonPlaceHolderApi2 jsonPlaceHolderApi21=retrofit1.create(JsonPlaceHolderApi2.class);
        SharedPreferences preferences = getSharedPreferences("Token", MODE_PRIVATE);
        token = preferences.getString("token", "no");

        scrollView=findViewById(R.id.booking_scroll);
        bookInst=findViewById(R.id.book_inst);
        InstText=findViewById(R.id.inst_book_text);
        houseAddress = findViewById(R.id.booking_edit_address);
        ConfirmBooking = findViewById(R.id.booking_confirm_booking);
        totalAmount = findViewById(R.id.booking_total_amount);
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        couponcodeEditText = findViewById(R.id.booking_couponCode_editText);
        couponInfo =findViewById(R.id.couponInfo);
        progress_booking=findViewById(R.id.progress_booking);

        couponcodeEditText.setSelection(couponcodeEditText.getText().length());

        couponcodeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    couponcodeEditText.setHint("");
                }
                else{
                    couponcodeEditText.setHint("Enter Coupon Code");
                }
            }
        });

        Button couponApply = findViewById(R.id.booking_coupon_apply_button);
        slotBtn=findViewById(R.id.book_a_slot);
        isCouponApplied = false;
        BookingOrders = findViewById(R.id.booking_order_summary);
        //drop=findViewById(R.id.drop_down_arrow);
        calendar=findViewById(R.id.Calender);
//        final int[] a = {0};
//        drop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(a[0] ==0){
//                    couponcodeEditText.setVisibility(View.VISIBLE);
//                    couponApply.setVisibility(View.VISIBLE);
//
//                    a[0]++;
//                }
//                else{
//                    couponcodeEditText.setVisibility(View.GONE);
//                    couponApply.setVisibility(View.GONE);
//
//                    a[0] =0;
//                }
//            }
//        });
        final int[] b = {0};
//        slotBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(b[0] ==0){
//                    calendar.setVisibility(View.VISIBLE);
//                    ConfirmBooking.setVisibility(View.VISIBLE);
//                    b[0] =1;
//                }
//                else{
//                    calendar.setVisibility(View.GONE);
//                    ConfirmBooking.setVisibility(View.GONE);
//                    b[0]=0;
//                }
//            }
//        });
        male_slots = (LinearLayout) findViewById(R.id.dt);
//        time_ll=findViewById(R.id.llt);
//        tim1=findViewById(R.id.t6);
//        tim2=findViewById(R.id.t1);
//        tim3=findViewById(R.id.t2);
//        tim4=findViewById(R.id.t3);
//        tim5=findViewById(R.id.t4);
//        tim6=findViewById(R.id.t5);
//        female_slots = (LinearLayout) findViewById(R.id.ll2);
//        mf_slots = (LinearLayout) findViewById(R.id.ll3);
        Intent intent = getIntent();
        couponApl = findViewById(R.id.viscoup);
        text = findViewById(R.id.line_coupon_text);
        //or =(TextView)findViewById(R.id.or);
        rl=findViewById(R.id.dispose);
        checkBox = findViewById(R.id.apron);
        bookingType += intent.getStringExtra("BookingType");
        listPosition = intent.getIntExtra("Position", -1);
        BookingTotalAmount = intent.getIntExtra("Booking Amount", 0);
        OrderSummary = intent.getStringExtra("Order Summary");
        serviceTime = intent.getIntExtra("Time",0);
        sidlist = (List<CartItemModel>) intent.getSerializableExtra("sidlist");
        Log.d("Order", OrderSummary + "  " + serviceTime);
        curAmount=BookingTotalAmount;

//        Call<InstItem> call= jsonPlaceHolderApi21.bookInst(new ServiceIdList(sidlist,null,null,curAmount,couponName),"Bearer "+token);
//        call.enqueue(new Callback<InstItem>() {
//            @Override
//            public void onResponse(Call<InstItem> call, retrofit2.Response<InstItem> response) {
//                if(response.code()==200){
//                    InstItem instItem=response.body();
//                    barberIdRet=instItem.getId();
//                    slotRet=instItem.getSlot();
//                    if(!instItem.isSuccess()){
//                        isBarberFound=false;
//                        InstText.setText("No barber nearby");
//                        bookInst.setVisibility(View.GONE);
//                    }
//                    else{
//                        isBarberFound=true;
//                        bookInst.setVisibility(View.VISIBLE);
//                        InstText.setText("Nearest barber is "+instItem.getTime()+"min away.");
//                    }
//                }
//                else{
//                    InstText.setText("No bookings available now");
//                    bookInst.setVisibility(View.GONE);
//                    //Toast.makeText(getApplicationContext(),"Could not get instant booking",Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<InstItem> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
//            }
//        });
        progress_booking.setVisibility(View.VISIBLE);
        ConfirmBooking.setVisibility(View.GONE);
        scrollView.setVisibility(View.GONE);
        Call<BookedList> call= jsonPlaceHolderApi21.getSlots(new ServiceIdList(sidlist, null, null, curAmount,couponName),"Bearer "+token);
        call.enqueue(new Callback<BookedList>() {
            @Override
            public void onResponse(Call<BookedList> call, retrofit2.Response<BookedList> response) {
                if(response.code()==200){
                    BookedItem list=response.body().getList();
                    blist1=list.getDay1();
                    blist2=list.getDay2();
                    blist3=list.getDay3();
                    blist4=list.getDay4();
                    blist5=list.getDay5();
                    blist6=list.getDay6();
                    blist7=list.getDay7();
                    gender=response.body().getGender();
                    if(gender.equals("female")){
                        slot1.setVisibility(View.GONE);
                        slot2.setVisibility(View.GONE);
                        slot3.setVisibility(View.GONE);
                        slot13.setVisibility(View.GONE);
                    }
                    progress_booking.setVisibility(View.GONE);
                    ConfirmBooking.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Could not load slots",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookedList> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        if(bookingType.equals("trend")){
            if(OrderSummary.equals("(men)Simple Hair Cut  Rs79") || OrderSummary.equals("(men)Stylish Hair Cut  Rs99")
                || OrderSummary.equals("(women)Hair Cut(U , V , straight)  Rs99")
                    || OrderSummary.equals("(women)Stylish Hair Cut(StepCut,LayerCut)  Rs199")){
                rl.setVisibility(View.GONE);
                count =1;
            }
        }
        if(bookingType.equals("direct")){
            String[] Orders = OrderSummary.split("\n", 5);
            for(String Order: Orders) {
                if (Order.equals("(men)Simple Hair Cut\t\t\tRs79") || Order.equals("(men)Stylish Hair Cut\t\t\tRs99")
                        || Order.equals("(women)Hair Cut(U , V , straight)\t\t\tRs99") || Order.equals("(women)Stylish Hair Cut(StepCut, LayerCut)\t\t\tRs199")) {
                    if(count == 0){
                        rl.setVisibility(View.GONE);
                    }
                    count++;
                }
            }
        }
        if(bookingType.equals("Cart")){
            String[] Orders = OrderSummary.split("\n",10);
            for(String Order: Orders){
                if(Order.startsWith("(men)Simple Hair Cut") || Order.startsWith("(men)Stylish Hair Cut") || Order.startsWith("(women)Hair Cut(U , V , straight)") ||
                Order.startsWith("(women)Stylish Hair Cut(StepCut, LayerCut)")){
                    if(count == 0){
                        rl.setVisibility(View.GONE);
                    }
                    count+= Integer.parseInt(Order.substring(Order.lastIndexOf('(')+1,Order.lastIndexOf(')')));
//                    Toast.makeText(getApplicationContext(), "test 1 pass"+count, Toast.LENGTH_SHORT).show();
                }
            }
        }
        int finalCount = count;
        checkBox.setOnClickListener(v -> {
            if(checkBox.isChecked())
                BookingTotalAmount+= 10* finalCount;
            else
                BookingTotalAmount-=10* finalCount;
            totalAmount.setText("Total Amount Rs " + BookingTotalAmount);
        });

        String[] lines = OrderSummary.split("\n");
        for (String line : lines) {
            String sub = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
            Log.d("sub", sub);
            Log.d("line", line);
            if (sub.equals("men")) {
                men = true;
            }
            if (sub.equals("women")) {
                women = true;
            }
        }
        men=true;
        women=false;
        if (men && !women) {
            slot1 = findViewById(R.id.slot1);
            slot2 = findViewById(R.id.slot2);
            slot3 = findViewById(R.id.slot3);
            slot4 = findViewById(R.id.slot4);
            slot5 = findViewById(R.id.slot5);
            slot6 = findViewById(R.id.slot6);
            slot7 = findViewById(R.id.slot7);
            slot8 = findViewById(R.id.slot8);
            slot9 = findViewById(R.id.slot9);
            slot10 = findViewById(R.id.slot10);
            slot11=findViewById(R.id.slot11);
            slot12=findViewById(R.id.slot12);
            slot13=findViewById(R.id.slot13);
        }
        array = new int[2];
        array[0] = 100;
        array[1] = 100;
        //    private TextView date;
        //    private TextView chooseTime;
        //    private TextView time;
        //private CardView usecurrentAddress;
        TextView month = (TextView) findViewById(R.id.mon);

        changeLocation = findViewById(R.id.booking_choose_address);
        day1 = findViewById(R.id.day1);
        day2 = findViewById(R.id.day2);
        day3 = findViewById(R.id.day3);
        day4 = findViewById(R.id.day4);
        day5 = findViewById(R.id.day5);
        day6 = findViewById(R.id.day6);
        day7 = findViewById(R.id.day7);

        btype1=findViewById(R.id.daytoday);
        btype2=findViewById(R.id.weekly);
        btype3=findViewById(R.id.monthly);

//        String addres = sharedPreferences.getString("Address", "");
//
//        if (addres.equals("NA") || addres.equals("")) {
//            finish();
//        }
        totalAmount.setText("Total Amount Rs " + BookingTotalAmount);
        BookingOrders.setText(OrderSummary);

//        bookInst.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final AlertDialog.Builder builder=new AlertDialog.Builder(BookingPage.this);
//                builder.setMessage("Are you sure to book?");
//                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                    @SuppressLint("ResourceAsColor")
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Call<Void> call1=jsonPlaceHolderApi21.confirmBooking(new ServiceIdList(sidlist,barberIdRet,slotRet,curAmount,couponName),"Bearer "+token);
//                        call1.enqueue(new Callback<Void>() {
//                            @Override
//                            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
//                                if(response.code()==200){
//                                    Date c = Calendar.getInstance().getTime();
//                                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
//                                    String formattedDate = df.format(c);
//
//                                    Intent intent1 = new Intent(BookingPage.this, CongratulationsPage.class);
//                                    intent1.putExtra("Booking Amount", BookingTotalAmount);
//                                    intent1.putExtra("Order Summary", OrderSummary);
//                                    finalTime=slotRet;
//                                    finalDate=formattedDate;
//                                    startActivity(intent1);
//                                    finish();
//                                }
//                                else{
//                                    Toast.makeText(getApplicationContext(),"Could not confirm booking",Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<Void> call, Throwable t) {
//                                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                });
//                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Call<Void> call1=jsonPlaceHolderApi21.revertBooking(new ServiceIdList(sidlist,barberIdRet,slotRet,curAmount,couponName),"Bearer "+token);
//                        call1.enqueue(new Callback<Void>() {
//                            @Override
//                            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
//                                if(response.code()==200){
//
//                                }
//                                else{
//                                    Toast.makeText(getApplicationContext(),"Could not cancel booking",Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<Void> call, Throwable t) {
//                                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                });
//                AlertDialog dialog=builder.create();
//                dialog.show();
//            }
//        });

        Calendar calendar = Calendar.getInstance();
        selectedYear = calendar.get(Calendar.YEAR);
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        String cur_month = month_date.format(calendar.getTime());
        mon1 = cur_month;
        month.setText(cur_month + ", " + selectedYear);

        int selectedDay = calendar.get(Calendar.DAY_OF_MONTH);
        day1.setText(String.valueOf(selectedDay));

        int flag = 0;
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        selectedDay = calendar.get(Calendar.DAY_OF_MONTH);
        day2.setText(String.valueOf(selectedDay));
        String compMonth = month_date.format(calendar.getTime());
        mon2 = compMonth;
        if (!compMonth.equals(cur_month)) {
            month.setText(cur_month + "/" + compMonth + ", " + selectedYear);
            flag = 1;
        }

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        selectedDay = calendar.get(Calendar.DAY_OF_MONTH);
        day3.setText(String.valueOf(selectedDay));
        compMonth = month_date.format(calendar.getTime());
        mon3 = compMonth;
        if (!compMonth.equals(cur_month) && flag == 0) {
            month.setText(cur_month + "/" + compMonth + ", " + selectedYear);
            flag = 1;
        }

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        selectedDay = calendar.get(Calendar.DAY_OF_MONTH);
        day4.setText(String.valueOf(selectedDay));
        compMonth = month_date.format(calendar.getTime());
        mon4 = compMonth;
        if (!compMonth.equals(cur_month) && flag == 0) {
            month.setText(cur_month + "/" + compMonth + ", " + selectedYear);
            flag = 1;
        }

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        selectedDay = calendar.get(Calendar.DAY_OF_MONTH);
        day5.setText(String.valueOf(selectedDay));
        mon5 = compMonth;
        compMonth = month_date.format(calendar.getTime());
        if (!compMonth.equals(cur_month) && flag == 0) {
            month.setText(cur_month + "/" + compMonth + ", " + selectedYear);
            flag = 1;
        }

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        selectedDay = calendar.get(Calendar.DAY_OF_MONTH);
        day6.setText(String.valueOf(selectedDay));
        compMonth = month_date.format(calendar.getTime());
        mon6 = compMonth;
        if (!compMonth.equals(cur_month) && flag == 0) {
            month.setText(cur_month + "/" + compMonth + ", " + selectedYear);
            flag = 1;
        }

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        selectedDay = calendar.get(Calendar.DAY_OF_MONTH);
        day7.setText(String.valueOf(selectedDay));
        compMonth = month_date.format(calendar.getTime());
        mon7 = compMonth;
        if (!compMonth.equals(cur_month) && flag == 0) {
            month.setText(cur_month + "/" + compMonth + ", " + selectedYear);
            flag = 1;
        }
        progressDialog = new ProgressDialog(BookingPage.this);
        progressDialog.setMessage("Loading...");

        //fetchRegion();
        typesel=1;
//        btype1.setOnClickListener(v -> {
//            btype1.setTextColor(getResources().getColor(R.color.white));
//            btype1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//            btype2.setTextColor(getResources().getColor(R.color.colorAccent));
//            btype2.setBackgroundColor(getResources().getColor(R.color.white));
//            btype3.setTextColor(getResources().getColor(R.color.colorAccent));
//            btype3.setBackgroundColor(getResources().getColor(R.color.white));
//            typesel=1;
//        });
//        btype2.setOnClickListener(v -> {
//            btype2.setTextColor(getResources().getColor(R.color.white));
//            btype2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//            btype1.setTextColor(getResources().getColor(R.color.colorAccent));
//            btype1.setBackgroundColor(getResources().getColor(R.color.white));
//            btype3.setTextColor(getResources().getColor(R.color.colorAccent));
//            btype3.setBackgroundColor(getResources().getColor(R.color.white));
//            typesel=2;
//        });
//        btype3.setOnClickListener(v -> {
//            btype3.setTextColor(getResources().getColor(R.color.white));
//            btype3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//            btype2.setTextColor(getResources().getColor(R.color.colorAccent));
//            btype2.setBackgroundColor(getResources().getColor(R.color.white));
//            btype1.setTextColor(getResources().getColor(R.color.colorAccent));
//            btype1.setBackgroundColor(getResources().getColor(R.color.white));
//            typesel=3;
//        });

        day1.setOnClickListener(v -> {
            if(typesel==0){
                Toast.makeText(this,"Please select the type of booking you want to make",Toast.LENGTH_SHORT).show();
            }
            else {
                day1.setTextColor(getResources().getColor(R.color.white));
                day1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                mon = mon1;
                day = day1.getText().toString();
                male_slots.setVisibility(View.VISIBLE);
                //time_ll.setVisibility(View.VISIBLE);
                array[0] = 1;
                day2.setTextColor(getResources().getColor(R.color.colorAccent));
                day2.setBackgroundColor(getResources().getColor(R.color.white));
                day3.setTextColor(getResources().getColor(R.color.colorAccent));
                day3.setBackgroundColor(getResources().getColor(R.color.white));
                day4.setTextColor(getResources().getColor(R.color.colorAccent));
                day4.setBackgroundColor(getResources().getColor(R.color.white));
                day5.setTextColor(getResources().getColor(R.color.colorAccent));
                day5.setBackgroundColor(getResources().getColor(R.color.white));
                day6.setTextColor(getResources().getColor(R.color.colorAccent));
                day6.setBackgroundColor(getResources().getColor(R.color.white));
                day7.setTextColor(getResources().getColor(R.color.colorAccent));
                day7.setBackgroundColor(getResources().getColor(R.color.white));
            }
                enableAvialableSlots();
                disableUnavialableSlots(array[0]);

        });
        day2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(typesel==0){
                Toast.makeText(getApplicationContext(),"Please select the type of booking you want to make",Toast.LENGTH_SHORT).show();
            }
            else {
                    day2.setTextColor(getResources().getColor(R.color.white));
                    day2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    mon = mon2;
                    day = day2.getText().toString();

                    male_slots.setVisibility(View.VISIBLE);
                    //time_ll.setVisibility(View.VISIBLE);
                    //progressDialog.show();
//                setDefault();
                    array[0] = 2;
                    day1.setTextColor(getResources().getColor(R.color.colorAccent));
                    day1.setBackgroundColor(getResources().getColor(R.color.white));
                    day3.setTextColor(getResources().getColor(R.color.colorAccent));
                    day3.setBackgroundColor(getResources().getColor(R.color.white));
                    day4.setTextColor(getResources().getColor(R.color.colorAccent));
                    day4.setBackgroundColor(getResources().getColor(R.color.white));
                    day5.setTextColor(getResources().getColor(R.color.colorAccent));
                    day5.setBackgroundColor(getResources().getColor(R.color.white));
                    day6.setTextColor(getResources().getColor(R.color.colorAccent));
                    day6.setBackgroundColor(getResources().getColor(R.color.white));
                    day7.setTextColor(getResources().getColor(R.color.colorAccent));
                    day7.setBackgroundColor(getResources().getColor(R.color.white));

                }
                enableAvialableSlots();
                disableUnavialableSlots(array[0]);
            }
        });
        day3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(typesel==0){
                Toast.makeText(getApplicationContext(),"Please select the type of booking you want to make",Toast.LENGTH_SHORT).show();
            }
            else {
                    male_slots.setVisibility(View.VISIBLE);
                    //time_ll.setVisibility(View.VISIBLE);

                    day3.setTextColor(getResources().getColor(R.color.white));
                    day3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    mon = mon3;
                    day = day3.getText().toString();
                    //progressDialog.show();
//                setDefault();
                    array[0] = 3;
                    day1.setTextColor(getResources().getColor(R.color.colorAccent));
                    day1.setBackgroundColor(getResources().getColor(R.color.white));
                    day2.setTextColor(getResources().getColor(R.color.colorAccent));
                    day2.setBackgroundColor(getResources().getColor(R.color.white));
                    day4.setTextColor(getResources().getColor(R.color.colorAccent));
                    day4.setBackgroundColor(getResources().getColor(R.color.white));
                    day5.setTextColor(getResources().getColor(R.color.colorAccent));
                    day5.setBackgroundColor(getResources().getColor(R.color.white));
                    day6.setTextColor(getResources().getColor(R.color.colorAccent));
                    day6.setBackgroundColor(getResources().getColor(R.color.white));
                    day7.setTextColor(getResources().getColor(R.color.colorAccent));
                    day7.setBackgroundColor(getResources().getColor(R.color.white));

                }
                enableAvialableSlots();
                disableUnavialableSlots(array[0]);
            }
        });
        day4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(typesel==0){
                Toast.makeText(getApplicationContext(),"Please select the type of booking you want to make",Toast.LENGTH_SHORT).show();
            }
            else {
                    male_slots.setVisibility(View.VISIBLE);
                    //time_ll.setVisibility(View.VISIBLE);

                    day4.setTextColor(getResources().getColor(R.color.white));
                    day4.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    mon = mon4;
                    day = day4.getText().toString();
                    //progressDialog.show();
//                setDefault();
                    array[0] = 4;
                    day1.setTextColor(getResources().getColor(R.color.colorAccent));
                    day1.setBackgroundColor(getResources().getColor(R.color.white));
                    day2.setTextColor(getResources().getColor(R.color.colorAccent));
                    day2.setBackgroundColor(getResources().getColor(R.color.white));
                    day3.setTextColor(getResources().getColor(R.color.colorAccent));
                    day3.setBackgroundColor(getResources().getColor(R.color.white));
                    day5.setTextColor(getResources().getColor(R.color.colorAccent));
                    day5.setBackgroundColor(getResources().getColor(R.color.white));
                    day6.setTextColor(getResources().getColor(R.color.colorAccent));
                    day6.setBackgroundColor(getResources().getColor(R.color.white));
                    day7.setTextColor(getResources().getColor(R.color.colorAccent));
                    day7.setBackgroundColor(getResources().getColor(R.color.white));

                }
                enableAvialableSlots();
                disableUnavialableSlots(array[0]);
            }
        });
        day5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(typesel==0){
                Toast.makeText(getApplicationContext(),"Please select the type of booking you want to make",Toast.LENGTH_SHORT).show();
            }
            else {
                    male_slots.setVisibility(View.VISIBLE);
                    //time_ll.setVisibility(View.VISIBLE);

                    day5.setTextColor(getResources().getColor(R.color.white));
                    day5.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    mon = mon5;
                    day = day5.getText().toString();
                    //progressDialog.show();
//                setDefault();
                    array[0] = 5;
                    day1.setTextColor(getResources().getColor(R.color.colorAccent));
                    day1.setBackgroundColor(getResources().getColor(R.color.white));
                    day2.setTextColor(getResources().getColor(R.color.colorAccent));
                    day2.setBackgroundColor(getResources().getColor(R.color.white));
                    day3.setTextColor(getResources().getColor(R.color.colorAccent));
                    day3.setBackgroundColor(getResources().getColor(R.color.white));
                    day4.setTextColor(getResources().getColor(R.color.colorAccent));
                    day4.setBackgroundColor(getResources().getColor(R.color.white));
                    day6.setTextColor(getResources().getColor(R.color.colorAccent));
                    day6.setBackgroundColor(getResources().getColor(R.color.white));
                    day7.setTextColor(getResources().getColor(R.color.colorAccent));
                    day7.setBackgroundColor(getResources().getColor(R.color.white));

                }
                enableAvialableSlots();
                disableUnavialableSlots(array[0]);
            }
        });
        day6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(typesel==0){
                Toast.makeText(getApplicationContext(),"Please select the type of booking you want to make",Toast.LENGTH_SHORT).show();
            }
            else {
                    male_slots.setVisibility(View.VISIBLE);
                    //time_ll.setVisibility(View.VISIBLE);

                    day6.setTextColor(getResources().getColor(R.color.white));
                    day6.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    mon = mon6;
                    day = day6.getText().toString();

                    //progressDialog.show();
//                setDefault();
                    array[0] = 6;
                    day1.setTextColor(getResources().getColor(R.color.colorAccent));
                    day1.setBackgroundColor(getResources().getColor(R.color.white));
                    day2.setTextColor(getResources().getColor(R.color.colorAccent));
                    day2.setBackgroundColor(getResources().getColor(R.color.white));
                    day3.setTextColor(getResources().getColor(R.color.colorAccent));
                    day3.setBackgroundColor(getResources().getColor(R.color.white));
                    day5.setTextColor(getResources().getColor(R.color.colorAccent));
                    day5.setBackgroundColor(getResources().getColor(R.color.white));
                    day4.setTextColor(getResources().getColor(R.color.colorAccent));
                    day4.setBackgroundColor(getResources().getColor(R.color.white));
                    day7.setTextColor(getResources().getColor(R.color.colorAccent));
                    day7.setBackgroundColor(getResources().getColor(R.color.white));

                }
                enableAvialableSlots();
                disableUnavialableSlots(array[0]);
            }
        });
        day7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typesel == 0) {
                    Toast.makeText(getApplicationContext(), "Please select the type of booking you want to make", Toast.LENGTH_SHORT).show();
                } else {
                    male_slots.setVisibility(View.VISIBLE);
                    //time_ll.setVisibility(View.VISIBLE);

                    day7.setTextColor(getResources().getColor(R.color.white));
                    day7.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    mon = mon7;
                    day = day7.getText().toString();
                    //progressDialog.show();

                    array[0] = 7;
                    day1.setTextColor(getResources().getColor(R.color.colorAccent));
                    day1.setBackgroundColor(getResources().getColor(R.color.white));
                    day2.setTextColor(getResources().getColor(R.color.colorAccent));
                    day2.setBackgroundColor(getResources().getColor(R.color.white));
                    day3.setTextColor(getResources().getColor(R.color.colorAccent));
                    day3.setBackgroundColor(getResources().getColor(R.color.white));
                    day4.setTextColor(getResources().getColor(R.color.colorAccent));
                    day4.setBackgroundColor(getResources().getColor(R.color.white));
                    day5.setTextColor(getResources().getColor(R.color.colorAccent));
                    day5.setBackgroundColor(getResources().getColor(R.color.white));
                    day6.setTextColor(getResources().getColor(R.color.colorAccent));
                    day6.setBackgroundColor(getResources().getColor(R.color.white));

                }
                enableAvialableSlots();
                disableUnavialableSlots(array[0]);
            }
        });
        useCurrentAddress();

        changeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inMap=true;
                startActivity(new Intent(BookingPage.this, MapSearchActivity.class));
            }
        });

        slot1.setOnClickListener(v -> {
            slot1.setCardBackgroundColor(Color.parseColor("#27AE60"));
            slot2.setCardBackgroundColor(Color.BLACK);
            slot3.setCardBackgroundColor(Color.BLACK);
            slot4.setCardBackgroundColor(Color.BLACK);
            slot5.setCardBackgroundColor(Color.BLACK);
            slot6.setCardBackgroundColor(Color.BLACK);
            slot7.setCardBackgroundColor(Color.BLACK);
            slot8.setCardBackgroundColor(Color.BLACK);
            slot9.setCardBackgroundColor(Color.BLACK);
            slot10.setCardBackgroundColor(Color.BLACK);
            slot11.setCardBackgroundColor(Color.BLACK);
            slot12.setCardBackgroundColor(Color.BLACK);
            slot13.setCardBackgroundColor(Color.BLACK);
            if (men && !women) {
                array[1] = 6;
            } else {
                array[1] = 9;
            }
                disableUnavialableSlots(array[0]);
        });
//        tim1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tim1.setCardBackgroundColor(Color.parseColor("#27AE60"));
//                tim2.setCardBackgroundColor(Color.BLACK);
//                tim3.setCardBackgroundColor(Color.BLACK);
//                tim4.setCardBackgroundColor(Color.BLACK);
//                tim5.setCardBackgroundColor(Color.BLACK);
//                tim6.setCardBackgroundColor(Color.BLACK);
//                time="00";
//
//            }
//        });
        slot2.setOnClickListener(v -> {
            slot2.setCardBackgroundColor(Color.parseColor("#27AE60"));
            slot1.setCardBackgroundColor(Color.BLACK);
            slot3.setCardBackgroundColor(Color.BLACK);
            slot4.setCardBackgroundColor(Color.BLACK);
            slot5.setCardBackgroundColor(Color.BLACK);
            slot6.setCardBackgroundColor(Color.BLACK);
            slot7.setCardBackgroundColor(Color.BLACK);
            slot8.setCardBackgroundColor(Color.BLACK);
            slot9.setCardBackgroundColor(Color.BLACK);
            slot10.setCardBackgroundColor(Color.BLACK);
            slot11.setCardBackgroundColor(Color.BLACK);
            slot12.setCardBackgroundColor(Color.BLACK);
            slot13.setCardBackgroundColor(Color.BLACK);
            if (men && !women) {
                array[1] = 7;
            } else {
                array[1] = 10;
            }
                disableUnavialableSlots(array[0]);

        });
//        tim2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tim2.setCardBackgroundColor(Color.parseColor("#27AE60"));
//                tim1.setCardBackgroundColor(Color.BLACK);
//                tim3.setCardBackgroundColor(Color.BLACK);
//                tim4.setCardBackgroundColor(Color.BLACK);
//                tim5.setCardBackgroundColor(Color.BLACK);
//                tim6.setCardBackgroundColor(Color.BLACK);
//                time="10";
//
//            }
//        });
        slot3.setOnClickListener(v -> {
            slot3.setCardBackgroundColor(Color.parseColor("#27AE60"));
            slot2.setCardBackgroundColor(Color.BLACK);
            slot1.setCardBackgroundColor(Color.BLACK);
            slot4.setCardBackgroundColor(Color.BLACK);
            slot5.setCardBackgroundColor(Color.BLACK);
            slot6.setCardBackgroundColor(Color.BLACK);
            slot7.setCardBackgroundColor(Color.BLACK);
            slot8.setCardBackgroundColor(Color.BLACK);
            slot9.setCardBackgroundColor(Color.BLACK);
            slot10.setCardBackgroundColor(Color.BLACK);
            slot11.setCardBackgroundColor(Color.BLACK);
            slot12.setCardBackgroundColor(Color.BLACK);
            slot13.setCardBackgroundColor(Color.BLACK);
            if (men && !women) {
                array[1] = 8;
            } else {
                array[1] = 11;
            }
                disableUnavialableSlots(array[0]);
        });
//        tim3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tim3.setCardBackgroundColor(Color.parseColor("#27AE60"));
//                tim2.setCardBackgroundColor(Color.BLACK);
//                tim1.setCardBackgroundColor(Color.BLACK);
//                tim4.setCardBackgroundColor(Color.BLACK);
//                tim5.setCardBackgroundColor(Color.BLACK);
//                tim6.setCardBackgroundColor(Color.BLACK);
//                time="20";
//
//            }
//        });
        slot4.setOnClickListener(v -> {
            slot4.setCardBackgroundColor(Color.parseColor("#27AE60"));
            slot2.setCardBackgroundColor(Color.BLACK);
            slot3.setCardBackgroundColor(Color.BLACK);
            slot1.setCardBackgroundColor(Color.BLACK);
            slot5.setCardBackgroundColor(Color.BLACK);
            slot6.setCardBackgroundColor(Color.BLACK);
            slot7.setCardBackgroundColor(Color.BLACK);
            slot8.setCardBackgroundColor(Color.BLACK);
            slot9.setCardBackgroundColor(Color.BLACK);
            slot10.setCardBackgroundColor(Color.BLACK);
            slot11.setCardBackgroundColor(Color.BLACK);
            slot12.setCardBackgroundColor(Color.BLACK);
            slot13.setCardBackgroundColor(Color.BLACK);
            if (men && !women) {
                array[1] = 9;
            } else {
                array[1] = 12;
            }
                disableUnavialableSlots(array[0]);
        });
//        tim5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tim5.setCardBackgroundColor(Color.parseColor("#27AE60"));
//                tim2.setCardBackgroundColor(Color.BLACK);
//                tim3.setCardBackgroundColor(Color.BLACK);
//                tim4.setCardBackgroundColor(Color.BLACK);
//                tim1.setCardBackgroundColor(Color.BLACK);
//                tim6.setCardBackgroundColor(Color.BLACK);
//                time="40";
//
//            }
//        });
        slot5.setOnClickListener(v -> {
            slot5.setCardBackgroundColor(Color.parseColor("#27AE60"));
            slot2.setCardBackgroundColor(Color.BLACK);
            slot3.setCardBackgroundColor(Color.BLACK);
            slot4.setCardBackgroundColor(Color.BLACK);
            slot1.setCardBackgroundColor(Color.BLACK);
            slot6.setCardBackgroundColor(Color.BLACK);
            slot7.setCardBackgroundColor(Color.BLACK);
            slot8.setCardBackgroundColor(Color.BLACK);
            slot9.setCardBackgroundColor(Color.BLACK);
            slot10.setCardBackgroundColor(Color.BLACK);
            slot11.setCardBackgroundColor(Color.BLACK);
            slot12.setCardBackgroundColor(Color.BLACK);
            slot13.setCardBackgroundColor(Color.BLACK);
            if (men && !women) {
                array[1] = 10;
            } else if (women && !men) {
                array[1] = 13;
            } else {
                array[1] = 16;
            }
                disableUnavialableSlots(array[0]);

        });
//        tim4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tim4.setCardBackgroundColor(Color.parseColor("#27AE60"));
//                tim2.setCardBackgroundColor(Color.BLACK);
//                tim3.setCardBackgroundColor(Color.BLACK);
//                tim1.setCardBackgroundColor(Color.BLACK);
//                tim5.setCardBackgroundColor(Color.BLACK);
//                tim6.setCardBackgroundColor(Color.BLACK);
//                time="30";
//
//            }
//        });
        slot6.setOnClickListener(v -> {
            slot6.setCardBackgroundColor(Color.parseColor("#27AE60"));
            slot2.setCardBackgroundColor(Color.BLACK);
            slot3.setCardBackgroundColor(Color.BLACK);
            slot4.setCardBackgroundColor(Color.BLACK);
            slot5.setCardBackgroundColor(Color.BLACK);
            slot1.setCardBackgroundColor(Color.BLACK);
            slot7.setCardBackgroundColor(Color.BLACK);
            slot8.setCardBackgroundColor(Color.BLACK);
            slot9.setCardBackgroundColor(Color.BLACK);
            slot10.setCardBackgroundColor(Color.BLACK);
            slot11.setCardBackgroundColor(Color.BLACK);
            slot12.setCardBackgroundColor(Color.BLACK);
            slot13.setCardBackgroundColor(Color.BLACK);
            if (men && !women) {
                array[1] = 11;
            } else if (women && !men) {
                array[1] = 14;
            } else {
                array[1] = 17;
            }
                disableUnavialableSlots(array[0]);
        });
//        tim6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tim6.setCardBackgroundColor(Color.parseColor("#27AE60"));
//                tim2.setCardBackgroundColor(Color.BLACK);
//                tim3.setCardBackgroundColor(Color.BLACK);
//                tim4.setCardBackgroundColor(Color.BLACK);
//                tim5.setCardBackgroundColor(Color.BLACK);
//                tim1.setCardBackgroundColor(Color.BLACK);
//                time="50";
//
//            }
//        });
        if (!(men && women)) {
            slot7.setOnClickListener(v -> {
                slot7.setCardBackgroundColor(Color.parseColor("#27AE60"));
                slot2.setCardBackgroundColor(Color.BLACK);
                slot3.setCardBackgroundColor(Color.BLACK);
                slot4.setCardBackgroundColor(Color.BLACK);
                slot5.setCardBackgroundColor(Color.BLACK);
                slot6.setCardBackgroundColor(Color.BLACK);
                slot1.setCardBackgroundColor(Color.BLACK);
                slot8.setCardBackgroundColor(Color.BLACK);
                slot9.setCardBackgroundColor(Color.BLACK);
                slot10.setCardBackgroundColor(Color.BLACK);
                slot11.setCardBackgroundColor(Color.BLACK);
                slot12.setCardBackgroundColor(Color.BLACK);
                slot13.setCardBackgroundColor(Color.BLACK);
                if (men && !women) {
                    array[1] = 12;
                } else {
                    array[1] = 15;
                }
                disableUnavialableSlots(array[0]);

            });
            slot8.setOnClickListener(v -> {
                slot8.setCardBackgroundColor(Color.parseColor("#27AE60"));slot2.setCardBackgroundColor(Color.BLACK);
                slot3.setCardBackgroundColor(Color.BLACK);
                slot2.setCardBackgroundColor(Color.BLACK);
                slot4.setCardBackgroundColor(Color.BLACK);
                slot5.setCardBackgroundColor(Color.BLACK);
                slot6.setCardBackgroundColor(Color.BLACK);
                slot7.setCardBackgroundColor(Color.BLACK);
                slot1.setCardBackgroundColor(Color.BLACK);
                slot9.setCardBackgroundColor(Color.BLACK);
                slot10.setCardBackgroundColor(Color.BLACK);
                slot11.setCardBackgroundColor(Color.BLACK);
                slot12.setCardBackgroundColor(Color.BLACK);
                slot13.setCardBackgroundColor(Color.BLACK);
                if (men && !women) {
                    array[1] = 13;
                } else {
                    array[1] = 16;
                }

                disableUnavialableSlots(array[0]);

            });
            slot9.setOnClickListener(v -> {
                slot9.setCardBackgroundColor(Color.parseColor("#27AE60"));
                slot2.setCardBackgroundColor(Color.BLACK);
                slot3.setCardBackgroundColor(Color.BLACK);
                slot4.setCardBackgroundColor(Color.BLACK);
                slot5.setCardBackgroundColor(Color.BLACK);
                slot6.setCardBackgroundColor(Color.BLACK);
                slot7.setCardBackgroundColor(Color.BLACK);
                slot8.setCardBackgroundColor(Color.BLACK);
                slot1.setCardBackgroundColor(Color.BLACK);
                slot10.setCardBackgroundColor(Color.BLACK);
                slot11.setCardBackgroundColor(Color.BLACK);
                slot12.setCardBackgroundColor(Color.BLACK);
                slot13.setCardBackgroundColor(Color.BLACK);
                if (men && !women) {
                    array[1] = 14;
                } else {
                    array[1] = 17;
                }

                disableUnavialableSlots(array[0]);

            });
            if (men && !women) {
                slot10.setOnClickListener(v -> {
                    slot10.setCardBackgroundColor(Color.parseColor("#27AE60"));
                    slot2.setCardBackgroundColor(Color.BLACK);
                    slot3.setCardBackgroundColor(Color.BLACK);
                    slot4.setCardBackgroundColor(Color.BLACK);
                    slot5.setCardBackgroundColor(Color.BLACK);
                    slot6.setCardBackgroundColor(Color.BLACK);
                    slot7.setCardBackgroundColor(Color.BLACK);
                    slot8.setCardBackgroundColor(Color.BLACK);
                    slot1.setCardBackgroundColor(Color.BLACK);
                    slot9.setCardBackgroundColor(Color.BLACK);
                    slot11.setCardBackgroundColor(Color.BLACK);
                    slot12.setCardBackgroundColor(Color.BLACK);
                    slot13.setCardBackgroundColor(Color.BLACK);
                    array[1] = 15;

                disableUnavialableSlots(array[0]);

                });
                slot11.setOnClickListener(v -> {
                    slot11.setCardBackgroundColor(Color.parseColor("#27AE60"));
                    slot2.setCardBackgroundColor(Color.BLACK);
                    slot3.setCardBackgroundColor(Color.BLACK);
                    slot4.setCardBackgroundColor(Color.BLACK);
                    slot5.setCardBackgroundColor(Color.BLACK);
                    slot6.setCardBackgroundColor(Color.BLACK);
                    slot7.setCardBackgroundColor(Color.BLACK);
                    slot8.setCardBackgroundColor(Color.BLACK);
                    slot1.setCardBackgroundColor(Color.BLACK);
                    slot9.setCardBackgroundColor(Color.BLACK);
                    slot10.setCardBackgroundColor(Color.BLACK);
                    slot12.setCardBackgroundColor(Color.BLACK);
                    slot13.setCardBackgroundColor(Color.BLACK);
                    array[1] = 16;

                disableUnavialableSlots(array[0]);

                });
                slot12.setOnClickListener(v -> {
                    slot12.setCardBackgroundColor(Color.parseColor("#27AE60"));
                    slot2.setCardBackgroundColor(Color.BLACK);
                    slot3.setCardBackgroundColor(Color.BLACK);
                    slot4.setCardBackgroundColor(Color.BLACK);
                    slot5.setCardBackgroundColor(Color.BLACK);
                    slot6.setCardBackgroundColor(Color.BLACK);
                    slot7.setCardBackgroundColor(Color.BLACK);
                    slot8.setCardBackgroundColor(Color.BLACK);
                    slot1.setCardBackgroundColor(Color.BLACK);
                    slot9.setCardBackgroundColor(Color.BLACK);
                    slot11.setCardBackgroundColor(Color.BLACK);
                    slot10.setCardBackgroundColor(Color.BLACK);
                    slot13.setCardBackgroundColor(Color.BLACK);
                    array[1] = 17;

                disableUnavialableSlots(array[0]);

                });
                slot13.setOnClickListener(v -> {
                    slot13.setCardBackgroundColor(Color.parseColor("#27AE60"));
                    slot2.setCardBackgroundColor(Color.BLACK);
                    slot3.setCardBackgroundColor(Color.BLACK);
                    slot4.setCardBackgroundColor(Color.BLACK);
                    slot5.setCardBackgroundColor(Color.BLACK);
                    slot6.setCardBackgroundColor(Color.BLACK);
                    slot7.setCardBackgroundColor(Color.BLACK);
                    slot8.setCardBackgroundColor(Color.BLACK);
                    slot1.setCardBackgroundColor(Color.BLACK);
                    slot9.setCardBackgroundColor(Color.BLACK);
                    slot11.setCardBackgroundColor(Color.BLACK);
                    slot10.setCardBackgroundColor(Color.BLACK);
                    slot12.setCardBackgroundColor(Color.BLACK);
                    array[1] = 18;
                    disableUnavialableSlots(array[0]);

                });
            }
        }


        ConfirmBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDialog();
            }
        });

        couponApply.setOnClickListener(v -> {
            if(!TextUtils.isEmpty(couponcodeEditText.getText())){
                ProgressDialog progressDialog=new ProgressDialog(this);
                progressDialog.setMessage("Hold on...");
                progressDialog.show();
                Call<CouponItem> couponItemCall=jsonPlaceHolderApi2.applyCoupon(new ServiceIdList(sidlist, null, null, curAmount,couponcodeEditText.getText().toString()),"Bearer "+token);
                couponItemCall.enqueue(new Callback<CouponItem>() {
                    @Override
                    public void onResponse(Call<CouponItem> call, retrofit2.Response<CouponItem> response) {
                        if(response.code()==200){
                            CouponItem item=response.body();
                            couponServiceId=item.getServiceId();
                            upper=item.getUpperLimit();
                            lower=item.getLowerLimit();
                            couponName=couponcodeEditText.getText().toString();
                            Toast.makeText(getApplicationContext(),"Coupon applied!",Toast.LENGTH_LONG).show();
//                            if(couponServiceId.equals("all")){
//                                for(CartItemModel model:sidlist){
//                                    if(upper!=-1){
//                                        Log.d("price",model.getServicePrice()+"");
//                                        if(model.getServicePrice()<=upper && model.getServicePrice()>=lower) {
//                                            curAmount = BookingTotalAmount - item.getDiscount();
//                                            Log.d("price1",curAmount+"");
//                                            break;
//                                        }
//                                    }
//                                    else{
//                                        if(model.getServicePrice()>=lower) {
//                                            curAmount = BookingTotalAmount - item.getDiscount();
//                                            Log.d("price2",curAmount+"");
//                                            break;
//                                        }
//                                    }
//                                }
//                            }
//                            else{
//                                for(CartItemModel model:sidlist){
//                                    if(upper!=-1){
//                                        Log.d("price",model.getServicePrice()+"");
//                                        if(sidlist.get(sidlist.indexOf(model)).getId().equals(couponServiceId)){
//                                            Log.d("id same",model.getServicePrice()+"");
//                                            if(model.getServicePrice()<=upper && model.getServicePrice()>=lower){
//                                                Log.d("price range",model.getServicePrice()+"");
//                                                curAmount=BookingTotalAmount-item.getDiscount();
//                                                Log.d("price3",curAmount+"");
//                                            }
//                                        }
//                                    }
//                                    else{
//                                        if(sidlist.get(sidlist.indexOf(model)).getId().equals(couponServiceId)){
//                                            if(model.getServicePrice()>=lower){
//                                                curAmount=BookingTotalAmount-item.getDiscount();
//                                            }
//                                            Log.d("price4",curAmount+"");
//                                        }
//                                    }
//                                }
//                            }
                            curAmount=BookingTotalAmount-item.getDiscount();
                            //Log.d("price",curAmount+"");
                            progressDialog.dismiss();
                            couponInfo.setVisibility(View.VISIBLE);
                            couponInfo.setText("You have received a discount of Rs:"+item.getDiscount());
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Could not apply coupon",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CouponItem> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            } else{
                couponcodeEditText.setError("Please enter a coupon code first");
                couponcodeEditText.requestFocus();
            }
        });
    }

    private void checkDialog () {
        CheckTermDialog checkTermDialog = new CheckTermDialog();
        checkTermDialog.show(getSupportFragmentManager(), "test");
    }

    private void sendemailconfirmation () {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        JsonPlaceHolderApi jsonPlaceholderApi = retrofit.create(JsonPlaceHolderApi.class);
        FirebaseFirestore.getInstance().collection("Users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(task -> {
                    email = task.getResult().get("Email Address").toString();
                    //Toast.makeText(getApplicationContext(), email +"cds",Toast.LENGTH_SHORT).show();
                    Emailer emailer = new Emailer(email, OrderSummary, finalTime + "  " + finalDate, BookingTotalAmount + "");
                    Call<Emailer> call = jsonPlaceholderApi.sendEmail(emailer);
                    call.enqueue(new Callback<Emailer>() {
                        @Override
                        public void onResponse(Call<Emailer> call, retrofit2.Response<Emailer> response) {

                        }

                        @Override
                        public void onFailure(Call<Emailer> call, Throwable t) {

                        }
                    });
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Toast.makeText(getApplicationContext(),"Destroyed",Toast.LENGTH_SHORT).show();
//        Retrofit retrofit1= RetrofitClientInstanceBooking.getRetrofitInstance();
//        JsonPlaceHolderApi2 jsonPlaceHolderApi21=retrofit1.create(JsonPlaceHolderApi2.class);
//        Call<Void> call1=jsonPlaceHolderApi21.revertBooking(new ServiceIdList(sidlist,barberIdRet,slotRet,0,couponName),"Bearer "+token);
//        call1.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
//                if(response.code()==200){
//                    //Toast.makeText(getApplicationContext(),"Cancelled",Toast.LENGTH_SHORT).show();
//                }
//                else{
////                    Toast.makeText(getApplicationContext(),"Could not cancel booking",Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
//            }
//        });
    }

 //   @Override
//    protected void onPause() {
//        super.onPause();
//        //Toast.makeText(getApplicationContext(),"On pause",Toast.LENGTH_SHORT).show();
//        if(!inMap){
//            //Toast.makeText(getApplicationContext(),"Destroyed",Toast.LENGTH_SHORT).show();
//        Retrofit retrofit1= RetrofitClientInstanceBooking.getRetrofitInstance();
//        JsonPlaceHolderApi2 jsonPlaceHolderApi21=retrofit1.create(JsonPlaceHolderApi2.class);
//        Call<Void> call1=jsonPlaceHolderApi21.revertBooking(new ServiceIdList(sidlist,barberIdRet,slotRet,0,couponName),"Bearer "+token);
//        call1.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
//                if(response.code()==200){
//                    //Toast.makeText(getApplicationContext(),"Cancelled",Toast.LENGTH_SHORT).show();
//                }
//                else{
////                    Toast.makeText(getApplicationContext(),"Could not cancel booking",Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
//            }
//        });
//        inMap=false;
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(getApplicationContext(),"On resume",Toast.LENGTH_SHORT).show();
        inMap=false;
    }
    @Override
    protected void onRestart () {
        super.onRestart();
        Intent intent=getIntent();
        finish();
        startActivity(intent);
    }

//    private void addTosheet () {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbynnJCsAja8_NPhqBVhc9wB2vsrw2lHRpIQIgoqCiw1_d5geLuUDzm-ibTVN1pSzrQ-oA/exec"
//                , new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                //Toast.makeText(getApplicationContext(),"Entered on REsponse",Toast.LENGTH_SHORT).show();
//                //Toast.makeText(BookingPage.this,response,Toast.LENGTH_LONG).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//
//                Map<String, String> parmas = new HashMap<>();
//                //here we pass params
//                parmas.put("action", "addItem");
//                parmas.put("randomId", randomId);
//                //parmas.put("userName", Username);
//                parmas.put("services", OrderSummary);
//                parmas.put("servicedate", finalDate);
//                parmas.put("servicetime", finalTime);
//                parmas.put("total", String.valueOf(BookingTotalAmount));
//                parmas.put("address", userAddress);
//                //parmas.put("phone", UserPhone);
//                parmas.put("region", String.valueOf(region));
//                parmas.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
//                if (isCouponApplied)
//                    parmas.put("covid_warrior", "Yes");
//                else
//                    parmas.put("covid_warrior", "No");
//
//                return parmas;
//            }
//        };
//        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds
//        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        stringRequest.setRetryPolicy(retryPolicy);
//        RequestQueue queue = Volley.newRequestQueue(this);
//        queue.add(stringRequest);
//    }

    private boolean checkUserData () {
        if (houseAddress.getText().toString().isEmpty()) {
            houseAddress.setError("Please Enter an Address");
            houseAddress.requestFocus();
            Toast.makeText(getApplicationContext(), "Please Choose An Address", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (array[0] == 100 || array[1] == 100) {
            Toast.makeText(getApplicationContext(), "Date/Time Not selected!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onStart () {
        super.onStart();
        //extractDataFromUser();
    }

    private void useCurrentAddress () {
        SharedPreferences preferences=getSharedPreferences("Profile",MODE_PRIVATE);
        String address=preferences.getString("address",null);
        //Toast.makeText(getApplicationContext(),address,Toast.LENGTH_SHORT).show();
        houseAddress.setText(address);
    }

    private void slots (Task < DocumentSnapshot > task) {
        if (men && !women) {
            male_slots.setVisibility(View.VISIBLE);
            female_slots.setVisibility(View.INVISIBLE);
            mf_slots.setVisibility(View.INVISIBLE);
        } else if (women && !men) {
            male_slots.setVisibility(View.INVISIBLE);
            female_slots.setVisibility(View.VISIBLE);
            mf_slots.setVisibility(View.INVISIBLE);
        } else {
            male_slots.setVisibility(View.INVISIBLE);
            female_slots.setVisibility(View.INVISIBLE);
            mf_slots.setVisibility(View.VISIBLE);
        }
        if (men && !women) {
            if(task.getResult().get("7_m").toString().equals("B")) {
                slot1.setEnabled(false);
                slot1.setCardBackgroundColor(Color.GRAY);
            } else {
                slot1.setEnabled(true);
                slot1.setCardBackgroundColor(Color.BLACK);
            }
            if (task.getResult().get("8_m").toString().equals("B")) {
                slot2.setEnabled(false);
                slot2.setCardBackgroundColor(Color.GRAY);
            } else {
                slot2.setEnabled(true);
                slot2.setCardBackgroundColor(Color.BLACK);
            }
            if (task.getResult().get("9_m").toString().equals("B")) {
                slot3.setEnabled(false);
                slot3.setCardBackgroundColor(Color.GRAY);
            } else {
                slot3.setEnabled(true);
                slot3.setCardBackgroundColor(Color.BLACK);
            }
            if (task.getResult().get("10_m").toString().equals("B")) {
                slot4.setEnabled(false);
                slot4.setCardBackgroundColor(Color.GRAY);
            } else {
                slot4.setEnabled(true);
                slot4.setCardBackgroundColor(Color.BLACK);
            }
            if (task.getResult().get("11_m").toString().equals("B")) {
                slot5.setEnabled(false);
                slot5.setCardBackgroundColor(Color.GRAY);
            } else {
                slot5.setEnabled(true);
                slot5.setCardBackgroundColor(Color.BLACK);
            }
            if (task.getResult().get("12_m").toString().equals("B")) {
                slot6.setEnabled(false);
                slot6.setCardBackgroundColor(Color.GRAY);
            } else {
                slot6.setEnabled(true);
                slot6.setCardBackgroundColor(Color.BLACK);
            }
            if (task.getResult().get("16_m").toString().equals("B")) {
                slot7.setEnabled(false);
                slot7.setCardBackgroundColor(Color.GRAY);
            } else {
                slot7.setEnabled(true);
                slot7.setCardBackgroundColor(Color.BLACK);
            }
            if (task.getResult().get("17_m").toString().equals("B")) {
                slot8.setEnabled(false);
                slot8.setCardBackgroundColor(Color.GRAY);
            } else {
                slot8.setEnabled(true);
                slot8.setCardBackgroundColor(Color.BLACK);
            }
            if (task.getResult().get("18_m").toString().equals("B")) {
                slot9.setEnabled(false);
                slot9.setCardBackgroundColor(Color.GRAY);
            } else {
                slot9.setEnabled(true);
                slot9.setCardBackgroundColor(Color.BLACK);
            }
            if (task.getResult().get("19_m").toString().equals("B")) {
                slot10.setEnabled(false);
                slot10.setCardBackgroundColor(Color.GRAY);
            } else {
                slot10.setEnabled(true);
                slot10.setCardBackgroundColor(Color.BLACK);
            }
        } else if (women && !men) {
            if (task.getResult().get("9_f").toString().equals("B")) {
                slot1.setEnabled(false);
                slot1.setCardBackgroundColor(Color.GRAY);
            } else {
                slot1.setEnabled(true);
                slot1.setCardBackgroundColor(Color.BLACK);
            }
            if (task.getResult().get("10_f").toString().equals("B")) {
                slot2.setEnabled(false);
                slot2.setCardBackgroundColor(Color.GRAY);
            } else {
                slot2.setEnabled(true);
                slot2.setCardBackgroundColor(Color.BLACK);
            }
            if (task.getResult().get("11_f").toString().equals("B")) {
                slot3.setEnabled(false);
                slot3.setCardBackgroundColor(Color.GRAY);
            } else {
                slot3.setEnabled(true);
                slot3.setCardBackgroundColor(Color.BLACK);
            }
            if (task.getResult().get("12_f").toString().equals("B")) {
                slot4.setEnabled(false);
                slot4.setCardBackgroundColor(Color.GRAY);
            } else {
                slot4.setEnabled(true);
                slot4.setCardBackgroundColor(Color.BLACK);
            }
            if (task.getResult().get("13_f").toString().equals("B")) {
                slot5.setEnabled(false);
                slot5.setCardBackgroundColor(Color.GRAY);
            } else {
                slot5.setEnabled(true);
                slot5.setCardBackgroundColor(Color.BLACK);
            }
            if (task.getResult().get("14_f").toString().equals("B")) {
                slot6.setEnabled(false);
                slot6.setCardBackgroundColor(Color.GRAY);
            } else {
                slot6.setEnabled(true);
                slot6.setCardBackgroundColor(Color.BLACK);
            }
            if (task.getResult().get("15_f").toString().equals("B")) {
                slot7.setEnabled(false);
                slot7.setCardBackgroundColor(Color.GRAY);
            } else {
                slot7.setEnabled(true);
                slot7.setCardBackgroundColor(Color.BLACK);
            }
            if (task.getResult().get("16_f").toString().equals("B")) {
                slot8.setEnabled(false);
                slot8.setCardBackgroundColor(Color.GRAY);
            } else {
                slot8.setEnabled(true);
                slot8.setCardBackgroundColor(Color.BLACK);
            }
            if (task.getResult().get("17_f").toString().equals("B")) {
                slot9.setEnabled(false);
                slot9.setCardBackgroundColor(Color.GRAY);
            } else {
                slot9.setEnabled(true);
                slot9.setCardBackgroundColor(Color.BLACK);
            }
        } else {
            if (task.getResult().get("9_m").toString().equals("B") || task.getResult().get("9_f").toString().equals("B")) {
                slot1.setEnabled(false);
                slot1.setCardBackgroundColor(Color.GRAY);
            } else {
                slot1.setEnabled(true);
                slot1.setCardBackgroundColor(Color.BLACK);
            }
            if (task.getResult().get("10_m").toString().equals("B") || task.getResult().get("10_f").toString().equals("B")) {
                slot2.setEnabled(false);
                slot2.setCardBackgroundColor(Color.GRAY);
            } else {
                slot2.setEnabled(true);
                slot2.setCardBackgroundColor(Color.BLACK);
            }
            if (task.getResult().get("11_m").toString().equals("B") || task.getResult().get("11_f").toString().equals("B")) {
                slot3.setEnabled(false);
                slot3.setCardBackgroundColor(Color.GRAY);
            } else {
                slot3.setEnabled(true);
                slot3.setCardBackgroundColor(Color.BLACK);
            }
            if (task.getResult().get("12_m").toString().equals("B") || task.getResult().get("12_f").toString().equals("B")) {
                slot4.setEnabled(false);
                slot4.setCardBackgroundColor(Color.GRAY);
            } else {
                slot4.setEnabled(true);
                slot4.setCardBackgroundColor(Color.BLACK);
            }
            if (task.getResult().get("16_m").toString().equals("B") || task.getResult().get("16_f").toString().equals("B")) {
                slot5.setEnabled(false);
                slot5.setCardBackgroundColor(Color.GRAY);
            } else {
                slot5.setEnabled(true);
                slot5.setCardBackgroundColor(Color.BLACK);
            }
            if (task.getResult().get("17_m").toString().equals("B") || task.getResult().get("17_f").toString().equals("B")) {
                slot6.setEnabled(false);
                slot6.setCardBackgroundColor(Color.GRAY);
            } else {
                slot6.setEnabled(true);
                slot6.setCardBackgroundColor(Color.BLACK);
            }
        }
        progressDialog.dismiss();
    }

    @SuppressLint("ResourceAsColor")
    private void disableUnavialableSlots(int x){
        if(x==1){
            if(blist1.getSl1()==0){
                slot1.setClickable(false);
                slot1.setCardBackgroundColor(Color.GRAY);
            }
            if(blist1.getSl2()==0){
                slot2.setClickable(false);
                slot2.setCardBackgroundColor(Color.GRAY);
            }
            if(blist1.getSl3()==0){
                slot3.setClickable(false);
                slot3.setCardBackgroundColor(Color.GRAY);
            }
            if(blist1.getSl4()==0){
                slot4.setClickable(false);
                slot4.setCardBackgroundColor(Color.GRAY);
            }
            if(blist1.getSl5()==0){
                slot5.setClickable(false);
                slot5.setCardBackgroundColor(Color.GRAY);
            }
            if(blist1.getSl6()==0){
                slot6.setClickable(false);
                slot6.setCardBackgroundColor(Color.GRAY);
            }
            if(blist1.getSl7()==0){
                slot7.setClickable(false);
                slot7.setCardBackgroundColor(Color.GRAY);
            }
            if(blist1.getSl8()==0){
                slot8.setClickable(false);
                slot8.setCardBackgroundColor(Color.GRAY);
            }
            if(blist1.getSl9()==0){
                slot9.setClickable(false);
                slot9.setCardBackgroundColor(Color.GRAY);
            }
            if(blist1.getSl10()==0){
                slot10.setClickable(false);
                slot10.setCardBackgroundColor(Color.GRAY);
            }
            if(blist1.getSl11()==0){
                slot11.setClickable(false);
                slot11.setCardBackgroundColor(Color.GRAY);
            }
            if(blist1.getSl12()==0){
                slot12.setClickable(false);
                slot12.setCardBackgroundColor(Color.GRAY);
            }
            if(blist1.getSl13()==0){
                slot13.setClickable(false);
                slot13.setCardBackgroundColor(Color.GRAY);
            }
            long currMilliSec = System.currentTimeMillis();
            DateFormat dateFormat = new SimpleDateFormat("HH");
            Date date = new Date(currMilliSec);
            String hourString = dateFormat.format(date);
            int hour = Integer.parseInt(hourString);

            if(hour>=6){
                slot1.setClickable(false);
                slot1.setCardBackgroundColor(Color.GRAY);
            }
            if(hour>=7){
                slot2.setClickable(false);
                slot2.setCardBackgroundColor(Color.GRAY);
            }
            if(hour>=8){
                slot3.setClickable(false);
                slot3.setCardBackgroundColor(Color.GRAY);
            }
            if(hour>=9){
                slot4.setClickable(false);
                slot4.setCardBackgroundColor(Color.GRAY);
            }
            if(hour>=10){
                slot5.setClickable(false);
                slot5.setCardBackgroundColor(Color.GRAY);
            }
            if(hour>=11){
                slot6.setClickable(false);
                slot6.setCardBackgroundColor(Color.GRAY);
            }
            if(hour>=12){
                slot7.setClickable(false);
                slot7.setCardBackgroundColor(Color.GRAY);
            }
            if(hour>=13){
                slot8.setClickable(false);
                slot8.setCardBackgroundColor(Color.GRAY);
            }
            if(hour>=14){
                slot9.setClickable(false);
                slot9.setCardBackgroundColor(Color.GRAY);
            }
            if(hour>=15){
                slot10.setClickable(false);
                slot10.setCardBackgroundColor(Color.GRAY);
            }
            if(hour>=16){
                slot11.setClickable(false);
                slot11.setCardBackgroundColor(Color.GRAY);
            }
            if(hour>=17){
                slot12.setClickable(false);
                slot12.setCardBackgroundColor(Color.GRAY);
            }
            if(hour>=18){
                slot13.setClickable(false);
                slot13.setCardBackgroundColor(Color.GRAY);
            }
        }
        else if(x==2){
            if(blist2.getSl1()==0){
                slot1.setClickable(false);
                slot1.setCardBackgroundColor(Color.GRAY);
            }
            if(blist2.getSl2()==0){
                slot2.setClickable(false);
                slot2.setCardBackgroundColor(Color.GRAY);
            }
            if(blist2.getSl3()==0){
                slot3.setClickable(false);
                slot3.setCardBackgroundColor(Color.GRAY);
            }
            if(blist2.getSl4()==0){
                slot4.setClickable(false);
                slot4.setCardBackgroundColor(Color.GRAY);
            }
            if(blist2.getSl5()==0){
                slot5.setClickable(false);
                slot5.setCardBackgroundColor(Color.GRAY);
            }
            if(blist2.getSl6()==0){
                slot6.setClickable(false);
                slot6.setCardBackgroundColor(Color.GRAY);
            }
            if(blist2.getSl7()==0){
                slot7.setClickable(false);
                slot7.setCardBackgroundColor(Color.GRAY);
            }
            if(blist2.getSl8()==0){
                slot8.setClickable(false);
                slot8.setCardBackgroundColor(Color.GRAY);
            }
            if(blist2.getSl9()==0){
                slot9.setClickable(false);
                slot9.setCardBackgroundColor(Color.GRAY);
            }
            if(blist2.getSl10()==0){
                slot10.setClickable(false);
                slot10.setCardBackgroundColor(Color.GRAY);
            }
            if(blist2.getSl11()==0){
                slot11.setClickable(false);
                slot11.setCardBackgroundColor(Color.GRAY);
            }
            if(blist2.getSl12()==0){
                slot12.setClickable(false);
                slot12.setCardBackgroundColor(Color.GRAY);
            }
            if(blist2.getSl13()==0){
                slot13.setClickable(false);
                slot13.setCardBackgroundColor(Color.GRAY);
            }
        }
        else if(x==3){
            if(blist3.getSl1()==0){
                slot1.setClickable(false);
                slot1.setCardBackgroundColor(Color.GRAY);
            }
            if(blist3.getSl2()==0){
                slot2.setClickable(false);
                slot2.setCardBackgroundColor(Color.GRAY);
            }
            if(blist3.getSl3()==0){
                slot3.setClickable(false);
                slot3.setCardBackgroundColor(Color.GRAY);
            }
            if(blist3.getSl4()==0){
                slot4.setClickable(false);
                slot4.setCardBackgroundColor(Color.GRAY);
            }
            if(blist3.getSl5()==0){
                slot5.setClickable(false);
                slot5.setCardBackgroundColor(Color.GRAY);
            }
            if(blist3.getSl6()==0){
                slot6.setClickable(false);
                slot6.setCardBackgroundColor(Color.GRAY);
            }
            if(blist3.getSl7()==0){
                slot7.setClickable(false);
                slot7.setCardBackgroundColor(Color.GRAY);
            }
            if(blist3.getSl8()==0){
                slot8.setClickable(false);
                slot8.setCardBackgroundColor(Color.GRAY);
            }
            if(blist3.getSl9()==0){
                slot9.setClickable(false);
                slot9.setCardBackgroundColor(Color.GRAY);
            }
            if(blist3.getSl10()==0){
                slot10.setClickable(false);
                slot10.setCardBackgroundColor(Color.GRAY);
            }
            if(blist3.getSl11()==0){
                slot11.setClickable(false);
                slot11.setCardBackgroundColor(Color.GRAY);
            }
            if(blist3.getSl12()==0){
                slot12.setClickable(false);
                slot12.setCardBackgroundColor(Color.GRAY);
            }
            if(blist3.getSl13()==0){
                slot13.setClickable(false);
                slot13.setCardBackgroundColor(Color.GRAY);
            }
        }
        else if(x==4){
            if(blist4.getSl1()==0){
                slot1.setClickable(false);
                slot1.setCardBackgroundColor(Color.GRAY);
            }
            if(blist4.getSl2()==0){
                slot2.setClickable(false);
                slot2.setCardBackgroundColor(Color.GRAY);
            }
            if(blist4.getSl3()==0){
                slot3.setClickable(false);
                slot3.setCardBackgroundColor(Color.GRAY);
            }
            if(blist4.getSl4()==0){
                slot4.setClickable(false);
                slot4.setCardBackgroundColor(Color.GRAY);
            }
            if(blist4.getSl5()==0){
                slot5.setClickable(false);
                slot5.setCardBackgroundColor(Color.GRAY);
            }
            if(blist4.getSl6()==0){
                slot6.setClickable(false);
                slot6.setCardBackgroundColor(Color.GRAY);
            }
            if(blist4.getSl7()==0){
                slot7.setClickable(false);
                slot7.setCardBackgroundColor(Color.GRAY);
            }
            if(blist4.getSl8()==0){
                slot8.setClickable(false);
                slot8.setCardBackgroundColor(Color.GRAY);
            }
            if(blist4.getSl9()==0){
                slot9.setClickable(false);
                slot9.setCardBackgroundColor(Color.GRAY);
            }
            if(blist4.getSl10()==0){
                slot10.setClickable(false);
                slot10.setCardBackgroundColor(Color.GRAY);
            }
            if(blist4.getSl11()==0){
                slot11.setClickable(false);
                slot11.setCardBackgroundColor(Color.GRAY);
            }
            if(blist4.getSl12()==0){
                slot12.setClickable(false);
                slot12.setCardBackgroundColor(Color.GRAY);
            }
            if(blist4.getSl13()==0){
                slot13.setClickable(false);
                slot13.setCardBackgroundColor(Color.GRAY);
            }
        }
        else if(x==5){
            if(blist5.getSl1()==0){
                slot1.setClickable(false);
                slot1.setCardBackgroundColor(Color.GRAY);
            }
            if(blist5.getSl2()==0){
                slot2.setClickable(false);
                slot2.setCardBackgroundColor(Color.GRAY);
            }
            if(blist5.getSl3()==0){
                slot3.setClickable(false);
                slot3.setCardBackgroundColor(Color.GRAY);
            }
            if(blist5.getSl4()==0){
                slot4.setClickable(false);
                slot4.setCardBackgroundColor(Color.GRAY);
            }
            if(blist5.getSl5()==0){
                slot5.setClickable(false);
                slot5.setCardBackgroundColor(Color.GRAY);
            }
            if(blist5.getSl6()==0){
                slot6.setClickable(false);
                slot6.setCardBackgroundColor(Color.GRAY);
            }
            if(blist5.getSl7()==0){
                slot7.setClickable(false);
                slot7.setCardBackgroundColor(Color.GRAY);
            }
            if(blist5.getSl8()==0){
                slot8.setClickable(false);
                slot8.setCardBackgroundColor(Color.GRAY);
            }
            if(blist5.getSl9()==0){
                slot9.setClickable(false);
                slot9.setCardBackgroundColor(Color.GRAY);
            }
            if(blist5.getSl10()==0){
                slot10.setClickable(false);
                slot10.setCardBackgroundColor(Color.GRAY);
            }
            if(blist5.getSl11()==0){
                slot11.setClickable(false);
                slot11.setCardBackgroundColor(Color.GRAY);
            }
            if(blist5.getSl12()==0){
                slot12.setClickable(false);
                slot12.setCardBackgroundColor(Color.GRAY);
            }
            if(blist5.getSl13()==0){
                slot13.setClickable(false);
                slot13.setCardBackgroundColor(Color.GRAY);
            }
        }
        else if(x==6){
            if(blist6.getSl1()==0){
                slot1.setClickable(false);
                slot1.setCardBackgroundColor(Color.GRAY);
            }
            if(blist6.getSl2()==0){
                slot2.setClickable(false);
                slot2.setCardBackgroundColor(Color.GRAY);
            }
            if(blist6.getSl3()==0){
                slot3.setClickable(false);
                slot3.setCardBackgroundColor(Color.GRAY);
            }
            if(blist6.getSl4()==0){
                slot4.setClickable(false);
                slot4.setCardBackgroundColor(Color.GRAY);
            }
            if(blist6.getSl5()==0){
                slot5.setClickable(false);
                slot5.setCardBackgroundColor(Color.GRAY);
            }
            if(blist6.getSl6()==0){
                slot6.setClickable(false);
                slot6.setCardBackgroundColor(Color.GRAY);
            }
            if(blist6.getSl7()==0){
                slot7.setClickable(false);
                slot7.setCardBackgroundColor(Color.GRAY);
            }
            if(blist6.getSl8()==0){
                slot8.setClickable(false);
                slot8.setCardBackgroundColor(Color.GRAY);
            }
            if(blist6.getSl9()==0){
                slot9.setClickable(false);
                slot9.setCardBackgroundColor(Color.GRAY);
            }
            if(blist6.getSl10()==0){
                slot10.setClickable(false);
                slot10.setCardBackgroundColor(Color.GRAY);
            }
            if(blist6.getSl11()==0){
                slot11.setClickable(false);
                slot11.setCardBackgroundColor(Color.GRAY);
            }
            if(blist6.getSl12()==0){
                slot12.setClickable(false);
                slot12.setCardBackgroundColor(Color.GRAY);
            }
            if(blist6.getSl13()==0){
                slot13.setClickable(false);
                slot13.setCardBackgroundColor(Color.GRAY);
            }
        }
        else{
            if(blist7.getSl1()==0){
                slot1.setClickable(false);
                slot1.setCardBackgroundColor(Color.GRAY);
            }
            if(blist7.getSl2()==0){
                slot2.setClickable(false);
                slot2.setCardBackgroundColor(Color.GRAY);
            }
            if(blist7.getSl3()==0){
                slot3.setClickable(false);
                slot3.setCardBackgroundColor(Color.GRAY);
            }
            if(blist7.getSl4()==0){
                slot4.setClickable(false);
                slot4.setCardBackgroundColor(Color.GRAY);
            }
            if(blist7.getSl5()==0){
                slot5.setClickable(false);
                slot5.setCardBackgroundColor(Color.GRAY);
            }
            if(blist7.getSl6()==0){
                slot6.setClickable(false);
                slot6.setCardBackgroundColor(Color.GRAY);
            }
            if(blist7.getSl7()==0){
                slot7.setClickable(false);
                slot7.setCardBackgroundColor(Color.GRAY);
            }
            if(blist7.getSl8()==0){
                slot8.setClickable(false);
                slot8.setCardBackgroundColor(Color.GRAY);
            }
            if(blist7.getSl9()==0){
                slot9.setClickable(false);
                slot9.setCardBackgroundColor(Color.GRAY);
            }
            if(blist7.getSl10()==0){
                slot10.setClickable(false);
                slot10.setCardBackgroundColor(Color.GRAY);
            }
            if(blist7.getSl11()==0){
                slot11.setClickable(false);
                slot11.setCardBackgroundColor(Color.GRAY);
            }
            if(blist7.getSl12()==0){
                slot12.setClickable(false);
                slot12.setCardBackgroundColor(Color.GRAY);
            }
            if(blist7.getSl13()==0){
                slot13.setClickable(false);
                slot13.setCardBackgroundColor(Color.GRAY);
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private void enableAvialableSlots(){
        long currMilliSec = System.currentTimeMillis();
        DateFormat dateFormat = new SimpleDateFormat("HH");
        Date date = new Date(currMilliSec);
        String hourString = dateFormat.format(date);

        int hour = Integer.parseInt(hourString);

            slot1.setClickable(true);
            slot1.setCardBackgroundColor(Color.BLACK);


            slot2.setClickable(true);
            slot2.setCardBackgroundColor(Color.BLACK);


            slot3.setClickable(true);
            slot3.setCardBackgroundColor(Color.BLACK);


            slot4.setClickable(true);
            slot4.setCardBackgroundColor(Color.BLACK);


            slot5.setClickable(true);
            slot5.setCardBackgroundColor(Color.BLACK);


            slot6.setClickable(true);
            slot6.setCardBackgroundColor(Color.BLACK);


            slot7.setClickable(true);
            slot7.setCardBackgroundColor(Color.BLACK);


            slot8.setClickable(true);
            slot8.setCardBackgroundColor(Color.BLACK);


            slot9.setClickable(true);
            slot9.setCardBackgroundColor(Color.BLACK);


            slot10.setClickable(true);
            slot10.setCardBackgroundColor(Color.BLACK);


            slot11.setClickable(true);
            slot11.setCardBackgroundColor(Color.BLACK);


            slot12.setClickable(true);
            slot12.setCardBackgroundColor(Color.BLACK);


            slot13.setClickable(true);
            slot13.setCardBackgroundColor(Color.BLACK);

    }
}