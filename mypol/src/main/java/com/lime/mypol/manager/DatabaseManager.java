package com.lime.mypol.manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lime.mypol.listitem.AssemblymanItem;
import com.lime.mypol.listitem.BillItem;
import com.lime.mypol.models.MemberInfo;
import com.lime.mypol.result.CheckTagResult;
import com.lime.mypol.utils.FileUtils;
import com.lime.mypol.models.Assemblyman;
import com.lime.mypol.models.Bill;
import com.lime.mypol.models.CommitteeMeeting;
import com.lime.mypol.models.GeneralMeeting;
import com.lime.mypol.models.PartyHistory;
import com.lime.mypol.models.Vote;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2015-06-11.
 */
public class DatabaseManager {
    /**
     * TAG for debugging
     */
    private final String TAG = "DatabaseManager";

    /**
     * Singleton instance
     */
    private static DatabaseManager instance;

    /**
     * database name
     */
    public static String DATABASE_NAME = "wadb";

    /**
     * version
     */
    public static int DATABASE_VERSION = 1;

    /**
     * Helper class defined
     */
    private DatabaseHelper mDBHelper;

    /**
     * Database object
     */
    private SQLiteDatabase mDB;

    private Context mContext;

    /**
     * Singleton Constructor
     *
     * @param context
     */
    private DatabaseManager(Context context) {
        this.mContext = context;
    }

    public static DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context);
        }

        return instance;
    }

    /**
     * open database
     *
     * @return
     */
    public boolean open() {
        Log.d(TAG, "opening database [" + DATABASE_NAME + "].");

        mDBHelper = new DatabaseHelper(mContext);
        mDB = mDBHelper.getWritableDatabase();

        return true;
    }

    /**
     * close database
     */
    public void close() {
        Log.d(TAG, "closing database [" + DATABASE_NAME + "].");

        mDB.close();
        instance = null;
    }

    /**
     * execute raw query using the input SQL
     * close the cursor after fetching any result;
     *
     * @param SQL
     * @return
     */
    public Cursor rawQuery(String SQL) {
        Log.d(TAG, "nexecuteQuery called.");

        Cursor c1 = null;
        try {
            c1 = mDB.rawQuery(SQL, null);
            Log.d(TAG, "cursor count : " + c1.getCount());
        } catch (Exception ex) {
            Log.d(TAG, " : Exception in rawQuery" + ex);
        }

        return c1;
    }

    /**
     * @param SQL
     * @return
     */
    public boolean execSQL(String SQL) {
        Log.d(TAG, "nexecuteQuery called.");

        try {
            Log.d(TAG, " : SQL : " + SQL);
            mDB.execSQL(SQL);
        } catch (Exception ex) {
            Log.d(TAG, " : Exception in execSQL " + ex);
            return false;
        }

        return true;
    }

    public CheckTagResult checkTag() {
        CheckTagResult result = new CheckTagResult();
        List<Integer> list = new ArrayList<Integer>();

        Cursor cursor = rawQuery("select max(update_tag) from assemblyman");
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            list.add(cursor.getInt(0));
        } else {
            list.add(0);
        }
        cursor = rawQuery("select max(update_tag) from bill");
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            list.add(cursor.getInt(0));
        } else {
            list.add(0);
        }
        cursor = rawQuery("select max(update_tag) from committee_meeting");
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            list.add(cursor.getInt(0));
        } else {
            list.add(0);
        }
        cursor = rawQuery("select max(update_tag) from general_meeting");
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            list.add(cursor.getInt(0));
        } else {
            list.add(0);
        }
        cursor = rawQuery("select max(update_tag) from party_history");
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            list.add(cursor.getInt(0));
        } else {
            list.add(0);
        }
        cursor = rawQuery("select max(update_tag) from vote");
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            list.add(cursor.getInt(0));
        } else {
            list.add(0);
        }

        result.setTagList(list);
        return result;
    }

    public boolean insertAssemblymanList(List<Assemblyman> assList) {


        for (Assemblyman ass : assList) {
            try {
                String sql = "insert into assemblyman values('" + ass.getAssemblyman_id() +
                        "','" + ass.getAssemblyman_name() + "'," + ass.getUpdate_tag() + ",'" + ass.getImage_url() +
                        "','" + ass.getOrg_image_url() + "','" + ass.getMod_dttm() + "'," + ass.getParty_id() +
                        ",'" + ass.getParty_name() + "','" + ass.getLocal_constituency() + "');";
                mDB.execSQL(sql);

            } catch (Exception ex) {
                Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                try {
                    String sql = "update assemblyman set assemblyman_name='" + ass.getAssemblyman_name() +
                            "', image_url='" + ass.getImage_url() + "', org_image_url= '" + ass.getOrg_image_url() +
                            "' , mod_dttm= '" + ass.getMod_dttm() + "', party_id='" + ass.getParty_id() + "', party_name='" + ass.getParty_name() +
                            "', local_constituency='" + ass.getLocal_constituency() + "', update_tag='" + ass.getUpdate_tag() + "' " +
                            "where assemblyman_id='" + ass.getAssemblyman_id() + "';";
                    mDB.execSQL(sql);
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<AssemblymanItem> selectAssemblymenList(int type, int start, int count) {
        List<AssemblymanItem> list = null;
        String sql = "";

        switch (type) {
            case 0:     // mod_dttm
                sql = "select * from assemblyman order by mod_dttm desc limit " + start + ", " + count;
                break;
            case 1:     // favorite
                break;
            case 2:     // naming
                sql = "select * from assemblyman order by assemblyman_name asc limit " + start + ", " + count;
                break;
        }

        Cursor cursor = rawQuery(sql);

        if (cursor != null && cursor.moveToFirst()) {
            list = new ArrayList<>();
            do {
                AssemblymanItem item = new AssemblymanItem();
                item.setAssemblymanName(cursor.getString(1));
                item.setCountDislike(0);
                item.setCountLike(0);
                item.setUrlPhoto(cursor.getString(3));
                item.setLocalConstituency(cursor.getString(8));
                item.setPartyName(cursor.getString(7));

                list.add(item);
            } while (cursor.moveToNext());
        }

        return list;
    }

    public boolean insertBillList(List<Bill> billList) {

        for (Bill bill : billList) {
            try {
                String sql = "insert into bill values('" + bill.getAssemblyman_id() + "', '" + bill.getUpdate_tag() + "', '" + bill.getBill_seq() + "', '" + bill.getBill_no() + "', '" + bill.getBill_status() + "'," +
                        "'" + bill.getBill_title() + "', '" + bill.getProposer_info() + "', '" + bill.getBill_class() + "', '" + bill.getReceive_date() + "', '" + bill.getRefer_date() + "', '" + bill.getBill_date3() + "'," +
                        "'" + bill.getCommittee_name() + "', '" + bill.getCommittee_id() + "', '" + bill.getCommittee_class() + "', '" + bill.getBill_result() + "', '" + bill.getBill_target_url() + "');";
//                Log.d(TAG, sql);
                mDB.execSQL(sql);

            } catch (Exception ex) {
                Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                try {
                    String sql = "update bill set assemblyman_id= '" + bill.getAssemblyman_id() + "', update_tag= '" + bill.getUpdate_tag() + "', bill_seq= '" + bill.getBill_seq() + "', bill_status= '" + bill.getBill_status() + "'," +
                            "bill_title= '" + bill.getBill_title() + "', proposer_info='" + bill.getProposer_info() + "', bill_class='" + bill.getBill_class() + "', receive_date='" + bill.getReceive_date() + "'," +
                            "refer_date='" + bill.getRefer_date() + "', bill_date3='" + bill.getBill_date3() + "', committee_name='" + bill.getCommittee_name() + "', committee_id='" + bill.getCommittee_id() + "', " +
                            "committee_class='" + bill.getCommittee_class() + "', bill_result='" + bill.getBill_result() + "', bill_target_url='" + bill.getBill_target_url() + "' " +
                            "where bill_no='" + bill.getBill_no() + "';";
                    mDB.execSQL(sql);
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<BillItem> selectBillList(int type, int start, int count) {
        List<BillItem> list = null;
        String sql = "";

        switch (type) {
            case 0:     // mod_dttm
                sql = "select * from bill order by update_tag desc limit " + start + ", " + count;
                break;
            case 1:     // favorite
                break;
            case 2:     // naming
                sql = "select * from bill order by bill_title asc limit " + start + ", " + count;
                break;
        }

        Cursor cursor = rawQuery(sql);
//        Log.d(TAG, cursor.getColumnName(1));
        if (cursor != null && cursor.moveToFirst()) {
            list = new ArrayList<>();
            while (cursor.moveToNext()) {
                BillItem item = new BillItem();
                item.setBillTitle(cursor.getString(5));
                item.setCountDislike(0);
                item.setCountLike(0);
                item.setBillStatus(cursor.getString(4));
                item.setProposerInfo(cursor.getString(6));
                item.setCommitteeName(cursor.getString(11));
                item.setReferDate(cursor.getString(9));

                list.add(item);
            }
        }

//        Log.d(TAG, "billList count : " + list.size());
        return list;
    }

    public boolean insertCommitteeMeetingList(List<CommitteeMeeting> list) {

        for (CommitteeMeeting com : list) {
            try {
                String sql = "insert into committee_meeting values('" + com.getAssemblyman_id() + "', '" + com.getUpdate_tag() + "', '" + com.getMeeting_id() + "', '" + com.getMeeting_name() + "'," +
                        "'" + com.getMeeting_order() + "', '" + com.getMeeting_date() + "', '" + com.getAttend_status() + "');";
//                Log.d(TAG, sql);
                mDB.execSQL(sql);

            } catch (Exception ex) {
                Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                try {
                    String sql = "update committee_meeting set assemblyman_id='" + com.getAssemblyman_id() + "', update_tag='" + com.getUpdate_tag() + "', meeting_id='" + com.getMeeting_id() + "'," +
                            "meeting_name='" + com.getMeeting_name() + "', meeting_order='" + com.getMeeting_order() + "', meeting_date='" + com.getMeeting_date() + "', attend_status='" + com.getAttend_status() + "' " +
                            "where assemblyman_id='" + com.getAssemblyman_id() + "' AND meeting_name='" + com.getMeeting_name() + "' AND meeting_order='" + com.getMeeting_order() + "';";
                    mDB.execSQL(sql);
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean insertGneralMeetingList(List<GeneralMeeting> list) {
        for (GeneralMeeting gen : list) {
            try {
                String sql = "insert into general_meeting values('" + gen.getAssemblyman_id() + "', '" + gen.getUpdate_tag() + "', '" + gen.getMeeting_id() + "', '" + gen.getMeeting_order() + "'," +
                        "'" + gen.getMeeting_dttm() + "', '" + gen.getAttend_status() + "');";
//                Log.d(TAG, sql);
                mDB.execSQL(sql);

            } catch (Exception ex) {
                Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                try {
                    String sql = "update general_meeting set assemblyman_id='" + gen.getAssemblyman_id() + "', update_tag='" + gen.getUpdate_tag() + "', meeting_id='" + gen.getMeeting_id() + "'," +
                            "meeting_order='" + gen.getMeeting_order() + "', meeting_dttm='" + gen.getMeeting_dttm() + "', attend_status='" + gen.getAttend_status() + "' " +
                            "where assemblyman_id='" + gen.getAssemblyman_id() + "' AND meeting_id='" + gen.getMeeting_id() + "';";
                    mDB.execSQL(sql);
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean insertPartyHistoryList(List<PartyHistory> list) {
        for (PartyHistory par : list) {
            try {
                String sql = "insert into party_history values('" + par.getUpdate_tag() + "', '" + par.getMember_seq() + "', '" + par.getParty_name() + "', '" + par.getIn_date() + "', '" + par.getOut_date() + "', '" + par.getNote() + "');";
//                Log.d(TAG, sql);
                mDB.execSQL(sql);

            } catch (Exception ex) {
                Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                try {
                    String sql = "update party_history set update_tag='" + par.getUpdate_tag() + "', member_seq='" + par.getMember_seq() + "', party_name='" + par.getParty_name() + "', in_date='" + par.getIn_date() + "'," +
                            "out_date='" + par.getOut_date() + "', note='" + par.getNote() + "'" +
                            "where party_name='" + par.getParty_name() + "' AND member_seq='" + par.getMember_seq() + "' AND in_date='" + par.getIn_date() + "';";
                    mDB.execSQL(sql);
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean insertVoteList(List<Vote> list) {
        for (Vote vote : list) {
            try {
                String sql = "insert into vote values('" + vote.getAssemblyman_id() + "', '" + vote.getUpdate_tag() + "', '" + vote.getBill_name() + "', '" + vote.getBill_no() + "', '" + vote.getVote_dttm() + "'," +
                        "'" + vote.getBill_target_url() + "', '" + vote.getResult() + "', '" + vote.getAssemblyman_vote() + "');";
//                Log.d(TAG, sql);
                mDB.execSQL(sql);

            } catch (Exception ex) {
                Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                try {
                    String sql = "update vote set assemblyman_id='" + vote.getAssemblyman_id() + "', update_tag='" + vote.getUpdate_tag() + "', bill_name='" + vote.getBill_name() + "'," +
                            "bill_no='" + vote.getBill_no() + "', vote_dttm='" + vote.getVote_dttm() + "', bill_target_url='" + vote.getBill_target_url() + "', result='" + vote.getResult() + "', assemblyman_vote='" + vote.getAssemblyman_vote() + "'" +
                            "where assemblyman_id='" + vote.getAssemblyman_id() + "' AND bill_no='" + vote.getBill_no() + "';";
                    mDB.execSQL(sql);
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getTotalAssemblyman() {
        int count = 0;
        String sql = "select count(*) from assemblyman";
        Cursor cursor = rawQuery(sql);

        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        return count;
    }

    public int getTotalBill() {
        int count = 0;
        String sql = "select count(*) from bill";
        Cursor cursor = rawQuery(sql);

        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

//        Log.d(TAG, "bill count : " + count);
        return count;
    }

    public boolean initDatabase(File result, MemberInfo memberInfo) {
        if (mDBHelper.initDatabase(mDB, result)) {
            open();
            makeTables();
            insertMemberInfo(memberInfo);

            return true;
        }
        return false;
    }

    private void makeTables() {
        // member_info table 생성
        String query;
        query = "DROP TABLE IF EXISTS member_info;";
        mDB.execSQL(query);
        query = "CREATE TABLE member_info(" +
                "member_id VARCHAR(45)," +
                "logon_type_id int," +
                "member_nickname VARCHAR(45)," +
                "address VARCHAR(100)," +
                "birth_date DATE, gender CHAR(1)," +
                "url_thumbnail VARCHAR(100)," +
                "CONSTRAINT tab_pk PRIMARY KEY (member_id, logon_type_id));";
        mDB.execSQL(query);
    }

    public boolean insertMemberInfo(MemberInfo memberInfo) {
        if (memberInfo == null) return true;

        try {
            String sql = "insert into member_info values('" + memberInfo.getMemberId() + "', " + memberInfo.getLogonTypeId() + ", '" +
                    memberInfo.getMemberNickname() + "', '" + memberInfo.getAddress() + "', '" + memberInfo.getBirthDate() + "', '" +
                    memberInfo.getGender() + "', '" + memberInfo.getUrlThumbnail() + "');";
//                Log.d(TAG, sql);
            mDB.execSQL(sql);

        } catch (Exception ex) {
            Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            try {
                String sql = "update member_info set member_nickname='" + memberInfo.getMemberNickname() + "', address='" + memberInfo.getAddress() +
                        "', birth_date='" + memberInfo.getBirthDate() + "', gender='" + memberInfo.getGender() + "'," + "url_thumbnail='" + memberInfo.getUrlThumbnail() +
                        "' where member_id='" + memberInfo.getMemberId() + "' AND logon_type_id=" + memberInfo.getLogonTypeId() + ";";
                mDB.execSQL(sql);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public MemberInfo selectMemberInfo(String member_id, int logonType) {
        MemberInfo memberInfo = null;
        String sql = "";

        sql = "select * from member_info where member_id='" + member_id + "' and logon_type_id=" + logonType;

        Cursor cursor = rawQuery(sql);
//        Log.d(TAG, cursor.getColumnName(1));
        if (cursor != null && cursor.moveToFirst()) {
            memberInfo.setMemberId(member_id);
            memberInfo.setLogonTypeId(logonType);
            memberInfo.setMemberNickname(cursor.getString(2));
            memberInfo.setAddress(cursor.getString(3));
            memberInfo.setGender(cursor.getString(4));
            memberInfo.setUrlThumbnail(cursor.getString(5));
        }

        return memberInfo;
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
//            String query;
//            query = "CREATE TABLE member_info(" +
//                    "member_id VARCHAR(45)," +
//                    "logon_type_id int," +
//                    "member_nickname VARCHAR(45)," +
//                    "address VARCHAR(100)," +
//                    "birth_date DATE, gender CHAR(1)," +
//                    "CONSTRAINT tab_pk PRIMARY KEY (member_id, logon_type_id));";
//            db.execSQL(query);
//            query = "create table assemblyman(" +
//                    "assemblyman_id VARCHAR(30) NOT NULL primary key," +
//                    "assemblyman_name VARCHAR(30) NOT NULL," +
//                    "update_tag INT(10) NOT NULL," +
//                    "image_url VARCHAR(60)," +
//                    "org_image_url VARCHAR(60)," +
//                    "mod_dttm VARCHAR(60)," +
//                    "party_id INT(10)," +
//                    "party_name VARCHAR(60)," +
//                    "local_constituency VARCHAR(60));";
//            db.execSQL(query);
//            query = "create table bill(" +
//                    "assemblyman_id INT(10) NOT NULL," +
//                    "update_tag INT(10) NOT NULL," +
//                    "bill_seq INT(10)," +
//                    "bill_no VARCHAR(60) NOT NULL primary key," +
//                    "bill_status VARCHAR(60)," +
//                    "bill_title VARCHAR(300)," +
//                    "proposer_info VARCHAR(60)," +
//                    "bill_class VARCHAR(60)," +
//                    "receive_date VARCHAR(60)," +
//                    "refer_date VARCHAR(60)," +
//                    "bill_date3 VARCHAR(60)," +
//                    "committee_name VARCHAR(60)," +
//                    "committee_id INT(10)," +
//                    "committee_class INT(10)," +
//                    "bill_result VARCHAR(60)," +
//                    "bill_target_url VARCHAR(300));";
//            db.execSQL(query);
//            query = "create table committee_meeting(" +
//                    "assemblyman_id INT(10) NOT NULL, " +
//                    "update_tag INT(10) NOT NULL," +
//                    "meeting_id INT(10)," +
//                    "meeting_name VARCHAR(100) NOT NULL," +
//                    "meeting_order VARCHAR(60) NOT NULL," +
//                    "meeting_date VARCHAR(60)," +
//                    "attend_status VARCHAR(60)," +
//                    "CONSTRAINT tab_pk PRIMARY KEY (assemblyman_id, meeting_name, meeting_order));";
//            db.execSQL(query);
//            query = "create table general_meeting(" +
//                    "assemblyman_id INT(10) NOT NULL," +
//                    "update_tag INT(10) NOT NULL, " +
//                    "meeting_id INT(10) NOT NULL," +
//                    "meeting_order VARCHAR(60)," +
//                    "meeting_dttm VARCHAR(60)," +
//                    "attend_status VARCHAR(60)," +
//                    "CONSTRAINT tab_pk PRIMARY KEY (assemblyman_id, meeting_id));";
//            db.execSQL(query);
//            query = "create table party_history(" +
//                    "update_tag INT(10) NOT NULL," +
//                    "member_seq INT(10) NOT NULL," +
//                    "party_name VARCHAR(60) NOT NULL," +
//                    "in_date VARCHAR(60) NOT NULL," +
//                    "out_date VARCHAR(60)," +
//                    "note VARCHAR(60)," +
//                    "CONSTRAINT tab_pk PRIMARY KEY (party_name, member_seq, in_date));";
//            db.execSQL(query);
//            query = "create table vote(" +
//                    "assemblyman_id INT(10) NOT NULL, " +
//                    "update_tag INT(10) NOT NULL," +
//                    "bill_name VARCHAR(300)," +
//                    "bill_no INT(10) NOT NULL," +
//                    "vote_dttm VARCHAR(60)," +
//                    "bill_target_url VARCHAR(300)," +
//                    "result VARCHAR(60)," +
//                    "assemblyman_vote VARCHAR(60)," +
//                    "CONSTRAINT tab_pk PRIMARY KEY (assemblyman_id, bill_no));";
//            db.execSQL(query);
//            Log.d(TAG, "onCreated database [" + DATABASE_NAME + "].");
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            Log.d(TAG, "opened database [" + DATABASE_NAME + "].");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            dropTables(db);
            onCreate(db);
            Log.d(TAG, "onUpgraded database [" + DATABASE_NAME + "].");
        }

        private void dropTables(SQLiteDatabase db) {
            String query;
            query = "DROP TABLE IF EXISTS member_info;";
            db.execSQL(query);
            query = "DROP TABLE IF EXISTS assemblyman;";
            db.execSQL(query);
            query = "DROP TABLE IF EXISTS bill;";
            db.execSQL(query);
            query = "DROP TABLE IF EXISTS committee_meeting;";
            db.execSQL(query);
            query = "DROP TABLE IF EXISTS general_meeting;";
            db.execSQL(query);
            query = "DROP TABLE IF EXISTS party_history;";
            db.execSQL(query);
            query = "DROP TABLE IF EXISTS vote;";
            db.execSQL(query);
        }

        public String DB_FILEPATH = "/data/data/com.lime.mypol/databases/wadb";

        public boolean initDatabase(SQLiteDatabase db, File newDb) {
            dropTables(db);
            close();
            File oldDb = new File(DB_FILEPATH);
            if (newDb.exists()) {
                try {
                    FileUtils.copyFile(new FileInputStream(newDb), new FileOutputStream(oldDb));
                    getWritableDatabase().close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                // Access the copied database so SQLiteHelper will cache it and mark
                // it as created.

                return true;
            }
            return false;
        }
    }
}
