package com.kurolink.app
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.*
import androidx.navigation.compose.*
private val bg=Color(0xFF090A0F); private val panel=Color(0xFF171923); private val red=Color(0xFFB51F3B); private val muted=Color(0xFF9295A3); private val white=Color(0xFFEDEDF2)
class MainActivity:ComponentActivity(){override fun onCreate(savedInstanceState:Bundle?){super.onCreate(savedInstanceState);setContent{App()}}}
@Composable fun App(){
 val nav= rememberNavController()
 MaterialTheme(colorScheme=darkColorScheme(background=bg,surface=panel,primary=red,onBackground=white,onSurface=white)){
 NavHost(nav,"welcome"){
 composable("welcome"){Welcome({nav.navigate("login")},{nav.navigate("register")})}
 composable("login"){Auth(false,{nav.navigate("home")},{nav.navigate("register")})}
 composable("register"){Auth(true,{nav.navigate("home")},{nav.navigate("login")})}
 composable("home"){Home{nav.navigate("welcome"){popUpTo("welcome"){inclusive=true}}}}
 }}
}
@Composable fun Welcome(login:()->Unit,create:()->Unit){
 Column(Modifier.fillMaxSize().background(bg).padding(28.dp),horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.Center){
 Box(Modifier.size(110.dp).clip(CircleShape).background(red.copy(.16f)),Alignment.Center){Text("眼",fontSize=56.sp,color=red,fontWeight=FontWeight.Black)}
 Spacer(Modifier.height(22.dp));Text("KUROLINK",fontSize=34.sp,fontWeight=FontWeight.Black,letterSpacing=4.sp)
 Text("CONNECT IN THE SHADOWS",fontSize=11.sp,color=muted,letterSpacing=2.sp);Spacer(Modifier.height(48.dp))
 Button(login,Modifier.fillMaxWidth().height(54.dp),colors=ButtonDefaults.buttonColors(containerColor=red)){Text("LOGIN")}
 Spacer(Modifier.height(10.dp));OutlinedButton(create,Modifier.fillMaxWidth().height(54.dp)){Text("CREATE ACCOUNT",color=white)}
 Spacer(Modifier.height(25.dp));Text("ITACHI-INSPIRED • SOCIAL COMMUNITY",color=muted,fontSize=10.sp)
 }}
@Composable fun Auth(register:Boolean,done:()->Unit,switch:()->Unit){
 var name by remember{mutableStateOf("")};var email by remember{mutableStateOf("")};var phone by remember{mutableStateOf("")};var pass by remember{mutableStateOf("")};var usePhone by remember{mutableStateOf(false)};var msg by remember{mutableStateOf(false)}
 Column(Modifier.fillMaxSize().background(bg).verticalScroll(rememberScrollState()).padding(24.dp)){
 Text("KUROLINK",color=red,fontWeight=FontWeight.Black,letterSpacing=3.sp);Spacer(Modifier.height(35.dp))
 Text(if(register)"Create your account" else "Welcome back",fontSize=27.sp,fontWeight=FontWeight.Bold);Text("Your shinobi community awaits.",color=muted)
 Spacer(Modifier.height(25.dp));if(register)Field("Display name",name,{name=it})
 Row(horizontalArrangement=Arrangement.spacedBy(8.dp)){FilterChip(!usePhone,{usePhone=false},{Text("Email")});FilterChip(usePhone,{usePhone=true},{Text("Phone")})}
 Field(if(usePhone)"Phone (+92...)" else "Email address",if(usePhone)phone else email,{if(usePhone)phone=it else email=it})
 Field("Password",pass,{pass=it},true);Spacer(Modifier.height(15.dp))
 Button({msg=true},Modifier.fillMaxWidth().height(52.dp),colors=ButtonDefaults.buttonColors(containerColor=red)){Text(if(register)"CREATE ACCOUNT" else "LOGIN")}
 if(msg){Text("Demo mode: authentication backend is not connected.",color=red,fontSize=12.sp);TextButton(done){Text("Open demo app")}}
 TextButton(switch){Text(if(register)"Already registered? Login" else "New here? Create account",color=white)}
 Text("Firebase authentication setup is required for real accounts and OTP.",color=muted,fontSize=11.sp)
 }}
@Composable fun Field(label:String,value:String,change:(String)->Unit,secret:Boolean=false){
 Column(Modifier.fillMaxWidth().padding(bottom=13.dp)){Text(label,color=muted,fontSize=12.sp);Spacer(Modifier.height(6.dp))
 BasicTextField(value,change,Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(panel).padding(16.dp),singleLine=true,textStyle=androidx.compose.ui.text.TextStyle(color=white,fontSize=15.sp),visualTransformation=if(secret)PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,decorationBox={inner->if(value.isEmpty())Text("Enter ${label.lowercase()}",color=muted);inner()})
 }}
@Composable fun Home(logout:()->Unit){
 var tab by remember{mutableStateOf("Home")};val tabs=listOf("Home","Chats","Reels","Calls","Profile");val icons=listOf(Icons.Default.Home,Icons.Default.ChatBubble,Icons.Default.PlayCircle,Icons.Default.Call,Icons.Default.Person)
 Scaffold(containerColor=bg,bottomBar={NavigationBar(containerColor=panel){tabs.forEachIndexed{i,t->NavigationBarItem(tab==t,{tab=t},{Icon(icons[i],t)},label={Text(t,fontSize=10.sp)},colors=NavigationBarItemDefaults.colors(selectedIconColor=red,indicatorColor=red.copy(.15f),selectedTextColor=white,unselectedTextColor=muted))}}}){pad->
 Column(Modifier.fillMaxSize().padding(pad)){Row(Modifier.fillMaxWidth().padding(16.dp),verticalAlignment=Alignment.CenterVertically){Text("KUROLINK",color=red,fontWeight=FontWeight.Black,letterSpacing=2.sp,fontSize=19.sp);Spacer(Modifier.weight(1f));IconButton(logout){Icon(Icons.Default.Logout,"Logout",tint=muted)}}
 when(tab){
 "Home"->Feed()
 "Chats"->Listing("Messages",listOf("Itachi Uchiha","Sasuke Uchiha","Kakashi Hatake","Naruto Uzumaki"),"Messaging backend pending")
 "Calls"->Listing("Calls",listOf("Voice calls","Video calls","Recent calls"),"Calling service pending")
 "Reels"->Box(Modifier.fillMaxSize().padding(18.dp).clip(RoundedCornerShape(18.dp)).background(Color(0xFF24111A)),Alignment.Center){Column(horizontalAlignment=Alignment.CenterHorizontally){Icon(Icons.Default.PlayCircle,null,tint=red,modifier=Modifier.size(60.dp));Text("KUROLINK REELS",fontWeight=FontWeight.Black,fontSize=22.sp);Text("Video upload/playback needs backend storage.",color=muted,fontSize=11.sp)}}
 else->Column(Modifier.fillMaxSize().padding(22.dp),horizontalAlignment=Alignment.CenterHorizontally){Box(Modifier.size(90.dp).clip(CircleShape).background(red.copy(.2f)),Alignment.Center){Text("K",color=red,fontSize=40.sp)};Text("Your Shinobi Profile",fontSize=22.sp,fontWeight=FontWeight.Bold);Text("@newshinobi",color=muted);Spacer(Modifier.height(25.dp));Text("0 Posts     0 Followers     0 Following",color=muted)}
 }
 }}
}
@Composable fun Feed(){
 Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal=16.dp)){
 Text("Stories",fontWeight=FontWeight.Bold,fontSize=20.sp)
 Row(Modifier.horizontalScroll(rememberScrollState()).padding(vertical=14.dp),horizontalArrangement=Arrangement.spacedBy(13.dp)){listOf("You","Itachi","Sasuke","Hinata","Naruto").forEach{Column(horizontalAlignment=Alignment.CenterHorizontally){Box(Modifier.size(58.dp).border(2.dp,red,CircleShape).padding(4.dp).clip(CircleShape).background(panel),Alignment.Center){Text(it.take(1),color=red,fontSize=22.sp)};Text(it,color=muted,fontSize=10.sp)}}}
 Text("For you",fontSize=20.sp,fontWeight=FontWeight.Bold);Spacer(Modifier.height(12.dp))
 Post("Itachi Uchiha","Some things are better understood in silence.","THE WILL OF FIRE","♡  2.4K     ◯  184")
 Post("Shinobi World","Protect what matters. That is true strength.","SHINOBI COMMUNITY","♡  986     ◯  72")
 }}
@Composable fun Post(author:String,caption:String,art:String,stats:String){
 Column(Modifier.fillMaxWidth().padding(bottom=18.dp).clip(RoundedCornerShape(17.dp)).background(panel)){
 Row(Modifier.padding(14.dp),verticalAlignment=Alignment.CenterVertically){Box(Modifier.size(35.dp).clip(CircleShape).background(red.copy(.2f)),Alignment.Center){Text(author.take(1),color=red)};Text(author,Modifier.padding(start=10.dp).weight(1f),fontWeight=FontWeight.Bold);Icon(Icons.Default.MoreHoriz,null,tint=muted)}
 Box(Modifier.fillMaxWidth().height(185.dp).background(Color(0xFF24111A)),Alignment.Center){Column(horizontalAlignment=Alignment.CenterHorizontally){Text("眼",fontSize=52.sp,color=red);Text(art,letterSpacing=3.sp,fontWeight=FontWeight.Bold)}}
 Text(caption,Modifier.padding(13.dp));Text(stats,Modifier.padding(start=13.dp,bottom=13.dp),color=muted,fontSize=12.sp)
 }}
@Composable fun Listing(title:String,names:List<String>,sub:String){
 Column(Modifier.fillMaxSize().padding(18.dp)){Text(title,fontSize=25.sp,fontWeight=FontWeight.Bold);Text(sub,color=muted,fontSize=12.sp);Spacer(Modifier.height(15.dp))
 names.forEach{Row(Modifier.fillMaxWidth().padding(vertical=6.dp).clip(RoundedCornerShape(13.dp)).background(panel).padding(14.dp),verticalAlignment=Alignment.CenterVertically){Box(Modifier.size(40.dp).clip(CircleShape).background(red.copy(.2f)),Alignment.Center){Text(it.take(1),color=red)};Text(it,Modifier.padding(start=12.dp).weight(1f));Icon(Icons.Default.ChevronRight,null,tint=muted)}}
 }}
