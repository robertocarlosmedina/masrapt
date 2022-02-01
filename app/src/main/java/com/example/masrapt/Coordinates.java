package com.example.masrapt;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Coordinates {
    private ArrayList<LatLng> lat_long_route_1 = new ArrayList<>();
    private ArrayList<LatLng> lat_long_route_2 = new ArrayList<>();
    private ArrayList<LatLng> lat_long_route_3 = new ArrayList<>();
    private ArrayList<ArrayList<LatLng>> all_routes_coordinates = new ArrayList<>();
    private ArrayList<Integer> color_array = new ArrayList<>();
    private ArrayList<String> routes_names = new ArrayList<>();

    private int color_route_1 = Color.BLUE;
    private int color_route_2 = Color.RED;
    private int color_route_3 = Color.GREEN;

    public int getColor_route_1() {
        return color_route_1;
    }

    public int getColor_route_2() {
        return color_route_2;
    }

    public int getColor_route_3() {
        return color_route_3;
    }


    public Coordinates() {
        addCoordinateValueToRoute_1();
        addCoordinateValueToRoute_2();
        addCoordinateValueToRoute_3();
        makeAllRoutesCoordinatesList();
    }

    public ArrayList<ArrayList<LatLng>> getAll_routes_coordinates() {
        return all_routes_coordinates;
    }

    public ArrayList<Integer> getColor_array() {
        return color_array;
    }

    public ArrayList<String> getRoutes_names() {
        return routes_names;
    }

    private void makeAllRoutesCoordinatesList() {
        all_routes_coordinates.add(lat_long_route_1);
        all_routes_coordinates.add(lat_long_route_2);
        all_routes_coordinates.add(lat_long_route_3);
        color_array.add(color_route_1);
        color_array.add(color_route_2);
        color_array.add(color_route_3);
        routes_names.add("Route L1");
        routes_names.add("Route L2");
        routes_names.add("Route L3");
    }

    public ArrayList<LatLng> getLat_long_route_1() {
        return lat_long_route_1;
    }

    public ArrayList<LatLng> getLat_long_route_2() {
        return lat_long_route_2;
    }

    public ArrayList<LatLng> getLat_long_route_3() {
        return lat_long_route_3;
    }

    private void addCoordinateValueToRoute_3() {
        LatLng m0 = new LatLng(16.88562900206418, -24.98646194341872);
        LatLng m1 = new LatLng(16.88604830336415, -24.98606172793679);
        LatLng m2 = new LatLng(16.88613746317504, -24.98661064992312);
        LatLng m3 = new LatLng(16.88631587338471, -24.98734042481583);
        LatLng m4 = new LatLng(16.88655923197705, -24.98760046889436);
        LatLng m5 = new LatLng(16.8867385244906, -24.98842998122516);
        LatLng m6 = new LatLng(16.88682070521176, -24.98906328335739);
        LatLng m7 = new LatLng(16.88645070240512, -24.98924937945331);
        LatLng m8 = new LatLng(16.88594252809708, -24.98927529507553);
        LatLng m9 = new LatLng(16.88538059311944, -24.98941009412679);
        LatLng m10 = new LatLng(16.88474261416841, -24.98963423084001);
        LatLng m11 = new LatLng(16.88421417705662, -24.98981964175229);
        LatLng m12 = new LatLng(16.88361399463777, -24.99003922186337);
        LatLng m13 = new LatLng(16.8832330746826, -24.98969271226329);
        LatLng m14 = new LatLng(16.88302219053354, -24.98909277115184);
        LatLng m15 = new LatLng(16.88284031947454, -24.98856044847816);
        LatLng m16 = new LatLng(16.88265228098728, -24.98807259430045);
        LatLng m17 = new LatLng(16.88219139822911, -24.98771460334859);
        LatLng m18 = new LatLng(16.88163909827321, -24.98748693717013);
        LatLng m19 = new LatLng(16.88104021493959, -24.98735942081671);
        LatLng m20 = new LatLng(16.88048888052272, -24.98760626633458);
        LatLng m21 = new LatLng(16.87978380835639, -24.98789816951712);
        LatLng m22 = new LatLng(16.87918422305658, -24.9881961763486);
        LatLng m23 = new LatLng(16.87857076365088, -24.98851096280102);
        LatLng m24 = new LatLng(16.87771999879102, -24.98887755676631);
        LatLng m25 = new LatLng(16.87703812987048, -24.98929382123301);
        LatLng m26 = new LatLng(16.87620647290203, -24.98976851829278);
        LatLng m27 = new LatLng(16.87503466912086, -24.99033461502972);
        LatLng m28 = new LatLng(16.87366829296922, -24.99062325292433);
        LatLng m29 = new LatLng(16.87236115745785, -24.9908501789183);
        LatLng m30 = new LatLng(16.87208986433749, -24.98979428383684);
        LatLng m31 = new LatLng(16.87192681956205, -24.9892402318581);
        LatLng m32 = new LatLng(16.87280913162169, -24.9889695604035);
        LatLng m33 = new LatLng(16.87339568148898, -24.98886612740751);
        LatLng m34 = new LatLng(16.87439900066825, -24.98863543808125);
        LatLng m35 = new LatLng(16.87470063563034, -24.98897541928934);
        LatLng m36 = new LatLng(16.87572785589519, -24.98884450919243);
        LatLng m37 = new LatLng(16.87588948960564, -24.98802555271449);
        LatLng m38 = new LatLng(16.87592899222503, -24.98743605930835);
        LatLng m39 = new LatLng(16.87552289777644, -24.98720134424565);
        LatLng m40 = new LatLng(16.87563428334343, -24.98683191454369);
        LatLng m41 = new LatLng(16.87660744878169, -24.98611136034203);
        LatLng m42 = new LatLng(16.87731788042954, -24.98555375755317);
        LatLng m43 = new LatLng(16.87795325370819, -24.98508016225288);
        LatLng m44 = new LatLng(16.87862525202783, -24.98456596865687);
        LatLng m45 = new LatLng(16.87939441067304, -24.98393258625296);
        LatLng m46 = new LatLng(16.88018534606585, -24.98345340811576);
        LatLng m47 = new LatLng(16.88090532503888, -24.9830957031855);
        LatLng m48 = new LatLng(16.88205398885236, -24.98228282605319);
        LatLng m49 = new LatLng(16.88197670380399, -24.98199094246121);
        LatLng m50 = new LatLng(16.88221134425819, -24.98186351274819);
        LatLng m51 = new LatLng(16.88232593352581, -24.982188868924);
        LatLng m52 = new LatLng(16.88247852705121, -24.98292572693677);
        LatLng m53 = new LatLng(16.88273495621657, -24.98365596698388);
        LatLng m54 = new LatLng(16.88300939824487, -24.98450971621313);
        LatLng m55 = new LatLng(16.88316472279283, -24.98513295355844);
        LatLng m56 = new LatLng(16.88335753348409, -24.98578935650636);
        LatLng m57 = new LatLng(16.88354230637747, -24.98639620658496);
        LatLng m58 = new LatLng(16.88364380445579, -24.98663680793663);
        LatLng m59 = new LatLng(16.88380975962628, -24.98717981588157);
        LatLng m60 = new LatLng(16.88407766516813, -24.98758362055947);
        LatLng m61 = new LatLng(16.88472196592175, -24.98750374723166);
        LatLng m62 = new LatLng(16.88511260881046, -24.9871426996398);
        LatLng m63 = new LatLng(16.88562900206418, -24.98646194341872);
        lat_long_route_3.add(m0);
        lat_long_route_3.add(m1);
        lat_long_route_3.add(m2);
        lat_long_route_3.add(m3);
        lat_long_route_3.add(m4);
        lat_long_route_3.add(m5);
        lat_long_route_3.add(m6);
        lat_long_route_3.add(m7);
        lat_long_route_3.add(m8);
        lat_long_route_3.add(m9);
        lat_long_route_3.add(m10);
        lat_long_route_3.add(m11);
        lat_long_route_3.add(m12);
        lat_long_route_3.add(m13);
        lat_long_route_3.add(m14);
        lat_long_route_3.add(m15);
        lat_long_route_3.add(m16);
        lat_long_route_3.add(m17);
        lat_long_route_3.add(m18);
        lat_long_route_3.add(m19);
        lat_long_route_3.add(m20);
        lat_long_route_3.add(m21);
        lat_long_route_3.add(m22);
        lat_long_route_3.add(m23);
        lat_long_route_3.add(m24);
        lat_long_route_3.add(m25);
        lat_long_route_3.add(m26);
        lat_long_route_3.add(m27);
        lat_long_route_3.add(m28);
        lat_long_route_3.add(m29);
        lat_long_route_3.add(m30);
        lat_long_route_3.add(m31);
        lat_long_route_3.add(m32);
        lat_long_route_3.add(m33);
        lat_long_route_3.add(m34);
        lat_long_route_3.add(m35);
        lat_long_route_3.add(m36);
        lat_long_route_3.add(m37);
        lat_long_route_3.add(m38);
        lat_long_route_3.add(m39);
        lat_long_route_3.add(m40);
        lat_long_route_3.add(m41);
        lat_long_route_3.add(m42);
        lat_long_route_3.add(m43);
        lat_long_route_3.add(m44);
        lat_long_route_3.add(m45);
        lat_long_route_3.add(m46);
        lat_long_route_3.add(m47);
        lat_long_route_3.add(m48);
        lat_long_route_3.add(m49);
        lat_long_route_3.add(m50);
        lat_long_route_3.add(m51);
        lat_long_route_3.add(m52);
        lat_long_route_3.add(m53);
        lat_long_route_3.add(m54);
        lat_long_route_3.add(m55);
        lat_long_route_3.add(m56);
        lat_long_route_3.add(m57);
        lat_long_route_3.add(m58);
        lat_long_route_3.add(m59);
        lat_long_route_3.add(m60);
        lat_long_route_3.add(m61);
        lat_long_route_3.add(m62);
        lat_long_route_3.add(m63);
    }

    private void addCoordinateValueToRoute_2() {
        LatLng m0 = new LatLng(16.88339577650514, -24.99010731214204);
        LatLng m1 = new LatLng(16.88309142817492, -24.98935388394678);
        LatLng m2 = new LatLng(16.88286303026812, -24.98865814534707);
        LatLng m3 = new LatLng(16.8834558709042, -24.98814731248727);
        LatLng m4 = new LatLng(16.88387499290745, -24.98781040206401);
        LatLng m5 = new LatLng(16.88442050686252, -24.98744753172662);
        LatLng m6 = new LatLng(16.88485072225428, -24.98727734465026);
        LatLng m7 = new LatLng(16.88516073907682, -24.98698451034955);
        LatLng m8 = new LatLng(16.8854132666715, -24.98668487412288);
        LatLng m9 = new LatLng(16.8853149740663, -24.98632126666091);
        LatLng m10 = new LatLng(16.88561125257653, -24.98606290657146);
        LatLng m11 = new LatLng(16.88592372472549, -24.98598813668758);
        LatLng m12 = new LatLng(16.88583718188046, -24.98537116556903);
        LatLng m13 = new LatLng(16.88598601060839, -24.98536977660699);
        LatLng m14 = new LatLng(16.88605168033196, -24.98606939933761);
        LatLng m15 = new LatLng(16.8861039348472, -24.98676570654844);
        LatLng m16 = new LatLng(16.88631270327691, -24.98726255599238);
        LatLng m17 = new LatLng(16.88674059694585, -24.98719181955182);
        LatLng m18 = new LatLng(16.88725411610161, -24.98707277354506);
        LatLng m19 = new LatLng(16.88771985102677, -24.98695464092336);
        LatLng m20 = new LatLng(16.88836160755481, -24.98677945461188);
        LatLng m21 = new LatLng(16.88894809508258, -24.98665595964918);
        LatLng m22 = new LatLng(16.88957275905836, -24.9864546571739);
        LatLng m23 = new LatLng(16.89009948417031, -24.98633113179454);
        LatLng m24 = new LatLng(16.89065782505036, -24.98620107356013);
        LatLng m25 = new LatLng(16.89126745962405, -24.98600163994818);
        LatLng m26 = new LatLng(16.89174370986302, -24.98588974657231);
        LatLng m27 = new LatLng(16.89184373246959, -24.98566216745914);
        LatLng m28 = new LatLng(16.89206359986671, -24.98511342555617);
        LatLng m29 = new LatLng(16.89245851613505, -24.98480197794389);
        LatLng m30 = new LatLng(16.89277242789669, -24.98466433215106);
        LatLng m31 = new LatLng(16.89317852963801, -24.98454214451486);
        LatLng m32 = new LatLng(16.893625994304, -24.98446492985767);
        LatLng m33 = new LatLng(16.89377620396163, -24.98447188254413);
        LatLng m34 = new LatLng(16.89391550132764, -24.98501734764052);
        LatLng m35 = new LatLng(16.89436394817355, -24.98500887052441);
        LatLng m36 = new LatLng(16.89470874202308, -24.98471992013152);
        LatLng m37 = new LatLng(16.89502131476559, -24.98434765752098);
        LatLng m38 = new LatLng(16.89530012394263, -24.9841871831339);
        LatLng m39 = new LatLng(16.89565207646869, -24.98453335686518);
        LatLng m40 = new LatLng(16.89608830707524, -24.98489844148363);
        LatLng m41 = new LatLng(16.89646011565157, -24.98528215936853);
        LatLng m42 = new LatLng(16.89687931750748, -24.98565983741402);
        LatLng m43 = new LatLng(16.89724834393586, -24.98588303246966);
        LatLng m44 = new LatLng(16.89774294090637, -24.98574262401132);
        LatLng m45 = new LatLng(16.89823941326708, -24.98550182728033);
        LatLng m46 = new LatLng(16.89846484932111, -24.98533735681728);
        LatLng m47 = new LatLng(16.89877337106743, -24.98526965272827);
        LatLng m48 = new LatLng(16.89897259821982, -24.98545911209493);
        LatLng m49 = new LatLng(16.89892417347086, -24.98609811596852);
        LatLng m50 = new LatLng(16.89880937782372, -24.98675223518338);
        LatLng m51 = new LatLng(16.89872500863527, -24.98731012026584);
        LatLng m52 = new LatLng(16.89865888480534, -24.98799104654323);
        LatLng m53 = new LatLng(16.89847230900579, -24.98855857140502);
        LatLng m54 = new LatLng(16.89820910270704, -24.98911950872496);
        LatLng m55 = new LatLng(16.89797608617067, -24.98965587393855);
        LatLng m56 = new LatLng(16.89753791462941, -24.99057705269678);
        LatLng m57 = new LatLng(16.89693959407899, -24.990827084801);
        LatLng m58 = new LatLng(16.89632012584708, -24.99123709056666);
        LatLng m59 = new LatLng(16.8958610888097, -24.99164184984541);
        LatLng m60 = new LatLng(16.89517521857636, -24.99193025815596);
        LatLng m61 = new LatLng(16.89462740694351, -24.99212362330487);
        LatLng m62 = new LatLng(16.89375841710797, -24.99229579524396);
        LatLng m63 = new LatLng(16.8931474364997, -24.99255757687489);
        LatLng m64 = new LatLng(16.89229774717582, -24.99317767278684);
        LatLng m65 = new LatLng(16.89158362470325, -24.99365593552766);
        LatLng m66 = new LatLng(16.89081732244271, -24.99405090678174);
        LatLng m67 = new LatLng(16.89007895603264, -24.9938774675038);
        LatLng m68 = new LatLng(16.88946548402488, -24.99334974055349);
        LatLng m69 = new LatLng(16.88905644362704, -24.99234931970395);
        LatLng m70 = new LatLng(16.88894965439444, -24.99137828539993);
        LatLng m71 = new LatLng(16.88894015265435, -24.99047110509387);
        LatLng m72 = new LatLng(16.88855904351282, -24.98972919131076);
        LatLng m73 = new LatLng(16.88795978302884, -24.98939024178096);
        LatLng m74 = new LatLng(16.88729960786499, -24.98926170556767);
        LatLng m75 = new LatLng(16.886870317268, -24.98927830290754);
        LatLng m76 = new LatLng(16.88632070559103, -24.98928480824429);
        LatLng m77 = new LatLng(16.88581081101212, -24.98933887690859);
        LatLng m78 = new LatLng(16.88528834985533, -24.9894648176289);
        LatLng m79 = new LatLng(16.88474460883993, -24.9896439982527);
        LatLng m80 = new LatLng(16.8841174858844, -24.98984911048568);
        LatLng m81 = new LatLng(16.88339577650514, -24.99010731214204);
        lat_long_route_2.add(m0);
        lat_long_route_2.add(m1);
        lat_long_route_2.add(m2);
        lat_long_route_2.add(m3);
        lat_long_route_2.add(m4);
        lat_long_route_2.add(m5);
        lat_long_route_2.add(m6);
        lat_long_route_2.add(m7);
        lat_long_route_2.add(m8);
        lat_long_route_2.add(m9);
        lat_long_route_2.add(m10);
        lat_long_route_2.add(m11);
        lat_long_route_2.add(m12);
        lat_long_route_2.add(m13);
        lat_long_route_2.add(m14);
        lat_long_route_2.add(m15);
        lat_long_route_2.add(m16);
        lat_long_route_2.add(m17);
        lat_long_route_2.add(m18);
        lat_long_route_2.add(m19);
        lat_long_route_2.add(m20);
        lat_long_route_2.add(m21);
        lat_long_route_2.add(m22);
        lat_long_route_2.add(m23);
        lat_long_route_2.add(m24);
        lat_long_route_2.add(m25);
        lat_long_route_2.add(m26);
        lat_long_route_2.add(m27);
        lat_long_route_2.add(m28);
        lat_long_route_2.add(m29);
        lat_long_route_2.add(m30);
        lat_long_route_2.add(m31);
        lat_long_route_2.add(m32);
        lat_long_route_2.add(m33);
        lat_long_route_2.add(m34);
        lat_long_route_2.add(m35);
        lat_long_route_2.add(m36);
        lat_long_route_2.add(m37);
        lat_long_route_2.add(m38);
        lat_long_route_2.add(m39);
        lat_long_route_2.add(m40);
        lat_long_route_2.add(m41);
        lat_long_route_2.add(m42);
        lat_long_route_2.add(m43);
        lat_long_route_2.add(m44);
        lat_long_route_2.add(m45);
        lat_long_route_2.add(m46);
        lat_long_route_2.add(m47);
        lat_long_route_2.add(m48);
        lat_long_route_2.add(m49);
        lat_long_route_2.add(m50);
        lat_long_route_2.add(m51);
        lat_long_route_2.add(m52);
        lat_long_route_2.add(m53);
        lat_long_route_2.add(m54);
        lat_long_route_2.add(m55);
        lat_long_route_2.add(m56);
        lat_long_route_2.add(m57);
        lat_long_route_2.add(m58);
        lat_long_route_2.add(m59);
        lat_long_route_2.add(m60);
        lat_long_route_2.add(m61);
        lat_long_route_2.add(m62);
        lat_long_route_2.add(m63);
        lat_long_route_2.add(m64);
        lat_long_route_2.add(m65);
        lat_long_route_2.add(m66);
        lat_long_route_2.add(m67);
        lat_long_route_2.add(m68);
        lat_long_route_2.add(m69);
        lat_long_route_2.add(m70);
        lat_long_route_2.add(m71);
        lat_long_route_2.add(m72);
        lat_long_route_2.add(m73);
        lat_long_route_2.add(m74);
        lat_long_route_2.add(m75);
        lat_long_route_2.add(m76);
        lat_long_route_2.add(m77);
        lat_long_route_2.add(m78);
        lat_long_route_2.add(m79);
        lat_long_route_2.add(m80);
        lat_long_route_2.add(m81);
    }

    private void addCoordinateValueToRoute_1() {
        LatLng m0 = new LatLng(16.88606433352687, -24.98629148762652);
        LatLng m1 = new LatLng(16.88581641052961, -24.98632020492875);
        LatLng m2 = new LatLng(16.88553631093369, -24.98637987539332);
        LatLng m3 = new LatLng(16.88533401122892, -24.98643186094823);
        LatLng m4 = new LatLng(16.8852642892178, -24.98615465095456);
        LatLng m5 = new LatLng(16.88559617763656, -24.98607147102824);
        LatLng m6 = new LatLng(16.88597070890412, -24.98599768512697);
        LatLng m7 = new LatLng(16.88590797133129, -24.98553279651804);
        LatLng m8 = new LatLng(16.88585291771152, -24.98517512473801);
        LatLng m9 = new LatLng(16.88579069520526, -24.98464214963741);
        LatLng m10 = new LatLng(16.88574995479092, -24.98414048675458);
        LatLng m11 = new LatLng(16.88578203322842, -24.98373642886931);
        LatLng m12 = new LatLng(16.88568609601361, -24.98337623528259);
        LatLng m13 = new LatLng(16.88564044608639, -24.98294926679576);
        LatLng m14 = new LatLng(16.88570488281033, -24.98249928125145);
        LatLng m15 = new LatLng(16.88563831017057, -24.98202169716476);
        LatLng m16 = new LatLng(16.88558714627003, -24.98157753572666);
        LatLng m17 = new LatLng(16.88548401758224, -24.98119665572729);
        LatLng m18 = new LatLng(16.88533983441716, -24.98075353452506);
        LatLng m19 = new LatLng(16.88526209428177, -24.98033635761292);
        LatLng m20 = new LatLng(16.88529972772972, -24.9798995583594);
        LatLng m21 = new LatLng(16.88540237050413, -24.97945021004677);
        LatLng m22 = new LatLng(16.88551128443155, -24.9790685705698);
        LatLng m23 = new LatLng(16.88554121925686, -24.97875340666548);
        LatLng m24 = new LatLng(16.88595822755225, -24.97892936082148);
        LatLng m25 = new LatLng(16.88643653974191, -24.97916557852084);
        LatLng m26 = new LatLng(16.88692096373541, -24.97936736850087);
        LatLng m27 = new LatLng(16.88736685409554, -24.97960758171477);
        LatLng m28 = new LatLng(16.8878070486269, -24.97978323208909);
        LatLng m29 = new LatLng(16.88826150395278, -24.97997844643842);
        LatLng m30 = new LatLng(16.8888035247211, -24.98016315581063);
        LatLng m31 = new LatLng(16.88938646881791, -24.9804106649505);
        LatLng m32 = new LatLng(16.88999417836212, -24.98062202335055);
        LatLng m33 = new LatLng(16.8907291648407, -24.980902346896);
        LatLng m34 = new LatLng(16.89123301822837, -24.98116298873003);
        LatLng m35 = new LatLng(16.89163837120334, -24.98157596686816);
        LatLng m36 = new LatLng(16.89190934107216, -24.98198806546086);
        LatLng m37 = new LatLng(16.89212972304748, -24.98245862079255);
        LatLng m38 = new LatLng(16.8922167568488, -24.9828007310516);
        LatLng m39 = new LatLng(16.89225691027207, -24.98313422688832);
        LatLng m40 = new LatLng(16.89173061369002, -24.9833852461767);
        LatLng m41 = new LatLng(16.89129634978105, -24.98366467503281);
        LatLng m42 = new LatLng(16.89093946483473, -24.98393605795271);
        LatLng m43 = new LatLng(16.89066091671846, -24.98429388624523);
        LatLng m44 = new LatLng(16.89074888536842, -24.98472330656572);
        LatLng m45 = new LatLng(16.89115671313244, -24.98500182113498);
        LatLng m46 = new LatLng(16.89152370365731, -24.985309624424);
        LatLng m47 = new LatLng(16.8918756874504, -24.98573375765937);
        LatLng m48 = new LatLng(16.89210993385963, -24.98620898344038);
        LatLng m49 = new LatLng(16.89229603435444, -24.98652665903384);
        LatLng m50 = new LatLng(16.89242468983358, -24.98705194482634);
        LatLng m51 = new LatLng(16.89250231489389, -24.98766162191514);
        LatLng m52 = new LatLng(16.89260630086517, -24.98830968928252);
        LatLng m53 = new LatLng(16.89267817779694, -24.98901208721236);
        LatLng m54 = new LatLng(16.89278643749336, -24.98985265049949);
        LatLng m55 = new LatLng(16.89293094874364, -24.99074454443494);
        LatLng m56 = new LatLng(16.89304835435065, -24.99150497502455);
        LatLng m57 = new LatLng(16.89326860013824, -24.9922594758812);
        LatLng m58 = new LatLng(16.89292803652718, -24.99273933038166);
        LatLng m59 = new LatLng(16.89243140949482, -24.99324198585118);
        LatLng m60 = new LatLng(16.89190562334231, -24.99347722886432);
        LatLng m61 = new LatLng(16.8914325409305, -24.99380321046601);
        LatLng m62 = new LatLng(16.89080703054731, -24.99407489978546);
        LatLng m63 = new LatLng(16.89003571575983, -24.99385845500998);
        LatLng m64 = new LatLng(16.88937948990977, -24.99343087793348);
        LatLng m65 = new LatLng(16.88914869239382, -24.99274955752854);
        LatLng m66 = new LatLng(16.8889530596743, -24.99197501436225);
        LatLng m67 = new LatLng(16.8889569889635, -24.99133637302979);
        LatLng m68 = new LatLng(16.88898404469682, -24.99065012487826);
        LatLng m69 = new LatLng(16.88875038926453, -24.9899856955643);
        LatLng m70 = new LatLng(16.88826652071406, -24.98952557865027);
        LatLng m71 = new LatLng(16.88751054695956, -24.98926109566973);
        LatLng m72 = new LatLng(16.88660775508751, -24.98919713190032);
        LatLng m73 = new LatLng(16.88681332070872, -24.98886484821954);
        LatLng m74 = new LatLng(16.88669071256902, -24.9882586943791);
        LatLng m75 = new LatLng(16.88660867320069, -24.98786369709445);
        LatLng m76 = new LatLng(16.88651818608574, -24.98745598056123);
        LatLng m77 = new LatLng(16.88626012761841, -24.98723352237617);
        LatLng m78 = new LatLng(16.88615710836407, -24.98683509111975);
        LatLng m79 = new LatLng(16.88606433352687, -24.98629148762652);
        lat_long_route_1.add(m0);
        lat_long_route_1.add(m1);
        lat_long_route_1.add(m2);
        lat_long_route_1.add(m3);
        lat_long_route_1.add(m4);
        lat_long_route_1.add(m5);
        lat_long_route_1.add(m6);
        lat_long_route_1.add(m7);
        lat_long_route_1.add(m8);
        lat_long_route_1.add(m9);
        lat_long_route_1.add(m10);
        lat_long_route_1.add(m11);
        lat_long_route_1.add(m12);
        lat_long_route_1.add(m13);
        lat_long_route_1.add(m14);
        lat_long_route_1.add(m15);
        lat_long_route_1.add(m16);
        lat_long_route_1.add(m17);
        lat_long_route_1.add(m18);
        lat_long_route_1.add(m19);
        lat_long_route_1.add(m20);
        lat_long_route_1.add(m21);
        lat_long_route_1.add(m22);
        lat_long_route_1.add(m23);
        lat_long_route_1.add(m24);
        lat_long_route_1.add(m25);
        lat_long_route_1.add(m26);
        lat_long_route_1.add(m27);
        lat_long_route_1.add(m28);
        lat_long_route_1.add(m29);
        lat_long_route_1.add(m30);
        lat_long_route_1.add(m31);
        lat_long_route_1.add(m32);
        lat_long_route_1.add(m33);
        lat_long_route_1.add(m34);
        lat_long_route_1.add(m35);
        lat_long_route_1.add(m36);
        lat_long_route_1.add(m37);
        lat_long_route_1.add(m38);
        lat_long_route_1.add(m39);
        lat_long_route_1.add(m40);
        lat_long_route_1.add(m41);
        lat_long_route_1.add(m42);
        lat_long_route_1.add(m43);
        lat_long_route_1.add(m44);
        lat_long_route_1.add(m45);
        lat_long_route_1.add(m46);
        lat_long_route_1.add(m47);
        lat_long_route_1.add(m48);
        lat_long_route_1.add(m49);
        lat_long_route_1.add(m50);
        lat_long_route_1.add(m51);
        lat_long_route_1.add(m52);
        lat_long_route_1.add(m53);
        lat_long_route_1.add(m54);
        lat_long_route_1.add(m55);
        lat_long_route_1.add(m56);
        lat_long_route_1.add(m57);
        lat_long_route_1.add(m58);
        lat_long_route_1.add(m59);
        lat_long_route_1.add(m60);
        lat_long_route_1.add(m61);
        lat_long_route_1.add(m62);
        lat_long_route_1.add(m63);
        lat_long_route_1.add(m64);
        lat_long_route_1.add(m65);
        lat_long_route_1.add(m66);
        lat_long_route_1.add(m67);
        lat_long_route_1.add(m68);
        lat_long_route_1.add(m69);
        lat_long_route_1.add(m70);
        lat_long_route_1.add(m71);
        lat_long_route_1.add(m72);
        lat_long_route_1.add(m73);
        lat_long_route_1.add(m74);
        lat_long_route_1.add(m75);
        lat_long_route_1.add(m76);
        lat_long_route_1.add(m77);
        lat_long_route_1.add(m78);
        lat_long_route_1.add(m79);
    }

}
