package com.example.signuploginrealtime;

public interface SelectListener {

    /*
        private void fileList(String text) {

            ArrayList<Doctor> filtredlist=new ArrayList<>();

            for (Doctor item:list){
            if(item.getSpec().toLowerCase().contains(text.toLowerCase())){

                filtredlist.add(item);

            }

            }

         if(filtredlist.isEmpty()){

             Toast.makeText(this,"no data found",Toast.LENGTH_SHORT).show();
         }else {
           setfiltredlist(filtredlist);
         }




        }*/


    void onItemClicked(Doctor mymodel);

    void onItemClicked(DossierClass mymodel);

    void onItemClicked(Presc mymodel);

    void onItemClicked(Appointement mymodel);

    void onItemClicked(Contact mymodel);


}
