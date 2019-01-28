package com.example.imeshranawaka.styleomega.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imeshranawaka.styleomega.Adapters.SlidingImageAdapter;
import com.example.imeshranawaka.styleomega.Models.Address;
import com.example.imeshranawaka.styleomega.Models.Order_Product;
import com.example.imeshranawaka.styleomega.Models.Orders;
import com.example.imeshranawaka.styleomega.Models.Product;
import com.example.imeshranawaka.styleomega.Models.Questions;
import com.example.imeshranawaka.styleomega.Models.Reviews;
import com.example.imeshranawaka.styleomega.Models.User;
import com.example.imeshranawaka.styleomega.R;
import com.example.imeshranawaka.styleomega.SharedPreferenceUtility;
import com.orm.SugarRecord;
import com.orm.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetails extends Fragment {

    @BindView(R.id.imgDisplayPager) ViewPager imgDisplayPager;
    @BindView(R.id.detailsContainer) ConstraintLayout detailsContainer;
    @BindView(R.id.txtTitle) TextView txtTitle;
    @BindView(R.id.txtPrice) TextView txtPrice;
    @BindView(R.id.txtDesc) TextView txtDesc;
    @BindView(R.id.txtRevName) TextView txtRevName;
    @BindView(R.id.txtRevDesc) TextView txtRevDesc;
    @BindView(R.id.txtQuestion) TextView txtQuestion;
    @BindView(R.id.txtAnswer) TextView txtAnswer;
    @BindView(R.id.txtQueText) TextView txtQueText;
    @BindView(R.id.txtRevText) TextView txtRevText;
    @BindView(R.id.quantity) Spinner quantity;
    @BindView(R.id.prodRating) RatingBar prodRating;

    @BindView(R.id.btnOrderNow) Button btnOrderNow;
    @BindView(R.id.btnAddToCart) Button btnAddToCart;
    @BindView(R.id.btnCartInView) ImageView btnCartInView;
    @BindView(R.id.txtStockMessage) TextView txtStockMessage;

    @BindView(R.id.sizeContainer) LinearLayout sizeContainer;
    @BindView(R.id.size) Spinner sizeSelector;

    private Unbinder unbinder;
    private Product product;
    private long prodId;
    public ProductDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_details, container, false);
        unbinder = ButterKnife.bind(this,v);
        //product = (Product) getArguments().getSerializable("product");
        prodId = getArguments().getLong("prodNo");

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        product = Product.findById(Product.class,prodId);
        setupProductPage();
    }

    private void setupProductPage() {
        String email = SharedPreferenceUtility.getInstance(getContext()).getUserEmail();
        Display display = getActivity().getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display. getSize(size);
        int width = size. x;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.height = width;

        imgDisplayPager.setLayoutParams(layoutParams);


        imgDisplayPager.setAdapter(new SlidingImageAdapter(this.getContext(),product.getImages()));
        imgDisplayPager.setMinimumHeight(width);
        //ConstraintLayout detailsContainer = v.findViewById(R.id.detailsContainer);
        txtPrice.setText("US$ ".concat(String.valueOf(product.getPrice())));
        txtTitle.setText(product.getTitle());

        int prodQty = product.getQuantity();
        ArrayList<Integer> qtyList = new ArrayList<>();
        for(int count=1;count<=prodQty;count++){
            qtyList.add(count);
        }

        if(product.getQuantity()!=0) {
            ArrayAdapter<Integer> qtyAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, qtyList);
            qtyAdapter.setDropDownViewResource(R.layout.spinner_item_view);
            quantity.setAdapter(qtyAdapter);

            if(product.getExtraFeatures().isEmpty()){
                sizeContainer.setVisibility(View.GONE);
            }else{
                try {
                    JSONArray array = new JSONArray(product.getExtraFeatures());
                    List<String> sizes = new ArrayList<>();
                    for(int count=0;count<array.length();count++){
                        sizes.add(array.get(count).toString());
                    }
                    ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_view, sizes);
                    sizeAdapter.setDropDownViewResource(R.layout.spinner_item_view);
                    sizeSelector.setAdapter(sizeAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else{
            quantity.setVisibility(View.GONE);
            btnAddToCart.setVisibility(View.GONE);
            btnOrderNow.setVisibility(View.GONE);
            btnCartInView.setVisibility(View.GONE);
            txtStockMessage.setVisibility(View.VISIBLE);
            sizeContainer.setVisibility(View.GONE);
        }



        txtDesc.setText(Html.fromHtml(product.getDesc()));

        List<Reviews> reviewsList = Reviews.find(Reviews.class,"prod_Id=?"
                ,String.valueOf(product.getId()));
        List<Questions> questions = Questions.find(Questions.class, "prod_ID=?"
                , product.getId().toString());

        if(reviewsList.size()==0){
            txtRevName.setText("No Reviews Found!");
            txtRevDesc.setVisibility(View.GONE);
            txtRevText.setVisibility(View.GONE);
        }else{
            Reviews rev = reviewsList.get(reviewsList.size()-1);
            List<User> user = User.find(User.class, "email=?", rev.getUserEmail());
            txtRevName.setText(user.get(0).getfName().concat(" ").concat(user.get(0).getlName()));
            txtRevDesc.setText(rev.getRevDesc());
            float rating=0;
            for(Reviews tempRev : reviewsList){
                rating+=tempRev.getRating();
            }
            prodRating.setRating(rating/reviewsList.size());
        }

        if(questions.size()==0){
            txtQuestion.setText("No Questions Found!");
            txtAnswer.setVisibility(View.GONE);
            txtQueText.setVisibility(View.GONE);
        }else{
            Questions que = questions.get(questions.size()-1);
            txtQuestion.setText(que.getQuestion());
            if(que.getAnswer()!=null) {
                txtAnswer.setText(que.getAnswer());
            }else{
                txtAnswer.setText("Not answered yet!");
            }
        }
    }

    @OnClick(R.id.btnShare)
    public void btnShare_onClick(){
        String shareBody = product.getImages().get(0)+"\n\nProduct Title : "
                +product.getTitle()+"\n\nProduct Price : $"+product.getPrice();

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");

        sharingIntent.putExtra(Intent.EXTRA_TEXT,shareBody );
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    @OnClick(R.id.btnAddToCart)
    public void btnAddToCart_onClick(){
        SharedPreferenceUtility sharedPref = SharedPreferenceUtility.getInstance(getContext());
        String email = sharedPref.getUserEmail();

        List<Orders> orders = Orders.find(Orders.class, "user_Email=? and order_Status=?"
                , email, "pending");

        if(orders.size()==0){
            List<Address> addressList = Address.find(Address.class, "user_Email=?", email);
            Address address=null;
            for(Address tempAdd : addressList){
                if(tempAdd.isDef()){
                    address = tempAdd;
                    break;
                }
            }
            Orders newOrder;
            if(address==null) {
                newOrder = new Orders("pending", email, 0);
            }else{
                newOrder = new Orders("pending", email, address.getId());
            }
            Calendar calendar = Calendar.getInstance();
            newOrder.setPurchasedDate(calendar.getTime());
            newOrder.save();
            long id = newOrder.getId();

            String size = sizeSelector.getSelectedItem().toString();
            if(size.isEmpty()){
                Order_Product orderProduct = new Order_Product(id, product.getId(),
                        Integer.parseInt(quantity.getSelectedItem().toString()));
                orderProduct.save();
            }else{
                Order_Product orderProduct = new Order_Product(id, product.getId(),
                        Integer.parseInt(quantity.getSelectedItem().toString()),size);
                orderProduct.save();
            }
            Toast.makeText(getContext(), "Product Successfully Added!", Toast.LENGTH_SHORT).show();
        }else{
            Orders currentOrder = orders.get(0);
            long id = currentOrder.getId();

            List<Order_Product> orderList = Order_Product.find(Order_Product.class,
                    "prod_Id=? and order_No=?", product.getId().toString()
                    ,String.valueOf(id));
            Order_Product orderProduct;
            int selectedQty = Integer.parseInt(quantity.getSelectedItem().toString());
            String size = sizeSelector.getSelectedItem().toString();
            if(orderList.size()==0){
                if(size.isEmpty()) {
                    orderProduct = new Order_Product(id, product.getId(), selectedQty);
                }else{
                    orderProduct = new Order_Product(id, product.getId(), selectedQty,size);
                }
                orderProduct.save();
            }else{
                orderProduct = orderList.get(0);
                orderProduct.setQuantity(selectedQty);
                if(!size.isEmpty()) {
                    orderProduct.setSize(size);
                }
                orderProduct.save();
            }


            Toast.makeText(getContext(),"Product Successfully Added!",Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btnRevViewAll)
    public void btnRevViewAll_onClick(){
        FragmentManager fm = getFragmentManager();
        AllReviews revies = new AllReviews();
        Bundle bundle = new Bundle();
        bundle.putLong("productNo",product.getId());
        revies.setArguments(bundle);
        FragmentTransaction transaction = fm.beginTransaction().replace(R.id.mainFragment, revies, "AllReviews");
        transaction.addToBackStack("ProductDetails");
        transaction.commit();

    }

    @OnClick(R.id.btnQueViewAll)
    public void btnQueViewAll_onClick(){
        FragmentManager fm = getFragmentManager();
        AllQuestions questions = new AllQuestions();
        Bundle bundle = new Bundle();
        bundle.putLong("productNo",product.getId());
        questions.setArguments(bundle);
        FragmentTransaction transaction = fm.beginTransaction().replace(R.id.mainFragment, questions, "AllQuestions");
        transaction.addToBackStack("ProductDetails");
        transaction.commit();

    }

    @OnClick(R.id.btnOrderNow)
    public void btnOrderNow_onClick(){
        int selectedQty = Integer.parseInt(quantity.getSelectedItem().toString());
        String size = sizeSelector.getSelectedItem().toString();
        Order_Product orderItem;
        if(size.isEmpty()){
            orderItem = new Order_Product(0,product.getId(),selectedQty);
        }else{
            orderItem = new Order_Product(0,product.getId(),selectedQty,size);
        }
        orderItem.save();
        List<Order_Product> orderProducts = new ArrayList<>();
        orderProducts.add(orderItem);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putSerializable("order_products", (Serializable) orderProducts);
        PlaceOrder placeOrder = new PlaceOrder();
        placeOrder.setArguments(bundle);

        transaction.replace(R.id.mainFragment, placeOrder,"PlaceOrder");
        transaction.addToBackStack("ProductDetails");
        transaction.commit();
    }

    @OnClick(R.id.btnCart)
    public void btnCart_onClick(){
        fragment_actions.getIntance(this).btnCart_onClick();
    }

    @OnClick(R.id.btnCartInView)
    public void btnCartInView_onClick(){
        fragment_actions.getIntance(this).btnCart_onClick();
    }

    @OnClick(R.id.btnBack)
    public void btnBack_onClick(){
        fragment_actions.getIntance(this).btnBack_onClick();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
