import{a as i,o as d,c as u,b as t,w as f,v as c,_ as p}from"./index-793f7473.js";import{g as m,r as g,u as h,a as b}from"./index.esm2017-76f3ad60.js";const w={data(){return{user:{},file:{},id:0}},methods:{getImageUrl(s){const e=s!==null?s:"/src/assets/default-user-profile.png";return new URL(e,import.meta.url).href},isEmptyObject(s){return Object.keys(s).length===0&&s.constructor===Object},onChangeProfileImage(s){const e=s.target.files||s.dataTransfer.files;e!=={}&&(this.file=e[0],this.user.profileUrl=URL.createObjectURL(this.file))},transferPhoneNumberFormat(s){let e="";e=s.target.value.replace(/[^0-9]/g,""),e.length<4?this.user.tel=e:e.length===4?this.user.tel=`${e.slice(0,3)}-${e.slice(3)}`:e.length===7?this.user.tel=`${e.slice(0,3)}-${e.slice(3,7)}-${e.slice(7)}`:this.user.tel=`${e.slice(0,3)}-${e.slice(3,7)}-${e.slice(7,11)}`},async onUserEditing(){if(this.id=this.$route.params.id,this.isEmptyObject(this.file)){const s={name:this.user.name,tel:this.user.tel,profileUrl:this.user.profileUrl};console.log(`id: ${this.id}`),await i.patch(`${i.defaults.baseURL}api/profile/${this.id}`,s,{headers:{"Content-Type":"application/json"}}).then(e=>{e.data.success&&(alert("해당 사용자 정보가 수정되었습니다."),window.location.reload())}).catch(e=>console.error(`e : ${e}`)).finally(()=>console.log("finally"))}else{const s=m();this.user.profileUrl.slice(this.user.profileUrl.lastIndexOf("/"));const e=g(s,`profiles/${this.id}/${this.file.name}`);h(e,this.file).then(async o=>{const l=await b(o.ref);this.user.profile_image=o.ref;var a={name:this.user.name,tel:this.user.tel,profileUrl:l},n=await i.patch(`${i.defaults.baseURL}api/profile/${this.id}`,a,{headers:{"Content-Type":"application/json"}});n.data.success&&(alert("해당 사용자 정보가 수정되었습니다."),window.location.reload())})}},async onUserDeleting(){this.id=this.$route.params.id,(await i.delete(`${i.defaults.baseURL}api/user/${this.id}`)).data.isSuccess&&(alert("회원 탈퇴되었습니다."),this.$router.push("/login"))}},async beforeCreate(){this.id=this.$route.params.id;const s=await i.get(`${i.defaults.baseURL}api/profile/${this.id}`);this.user=s.data}},y={class:"w-full m-5"},x={class:"sm:max-w-lg md:max-w-3xl lg:max-w-2xl xl:max-w-6xl",enctype:"multipart/form-data"},U=t("div",{class:"text-lg font-bold"}," 회원 상세보기 ",-1),_={class:"w-full my-5"},$={class:"flex justify-center mr-2"},v={for:"profileUrl",class:"relative cursor-default rounded-md"},k=["src"],j={class:"flex flex-wrap -mx-3 mb-6"},L={class:"w-full md:w-1/2 px-3 mb-6 md:mb-0"},R=t("label",{class:"block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2",for:"name"}," 이름 ",-1),C={class:"w-full md:w-1/2 px-3"},I=t("label",{class:"block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2",for:"tel"}," 전화번호 ",-1),E=["value"],O={class:"mt-6 space-y-3"},D={class:"flex justify-end"};function B(s,e,o,l,a,n){return d(),u("div",y,[t("div",x,[U,t("div",_,[t("div",$,[t("label",v,[t("img",{class:"w-32 h-32 rounded-full",src:s.getImageUrl(s.user.profileUrl)},null,8,k),t("input",{id:"profileUrl",name:"profileUrl",type:"file",class:"sr-only",onChange:e[0]||(e[0]=r=>s.onChangeProfileImage(r))},null,32)])])]),t("div",j,[t("div",L,[R,f(t("input",{class:"appearance-none block w-full bg-gray-200 text-gray-700 border border-grey-200 hover:border-red-500 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white",id:"name",name:"name","onUpdate:modelValue":e[1]||(e[1]=r=>s.user.name=r),autocomplete:"on"},null,512),[[c,s.user.name]])]),t("div",C,[I,t("input",{class:"appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 hover:border-red-500 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white",id:"tel",name:"tel",value:s.user.tel,onInput:e[2]||(e[2]=r=>s.transferPhoneNumberFormat(r)),autocomplete:"on"},null,40,E)])]),t("div",O,[t("div",D,[t("button",{onClick:e[3]||(e[3]=(...r)=>s.onUserEditing&&s.onUserEditing(...r)),class:"inline-flex justify-center rounded-md border border-transparent bg-blue-500 py-2 px-4 text-sm font-medium text-white shadow-sm hover:bg-blue-300 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 mr-2"}," 수정 "),t("button",{onClick:e[4]||(e[4]=(...r)=>s.onUserDeleting&&s.onUserDeleting(...r)),class:"inline-flex justify-center rounded-md border border-transparent bg-red-500 py-2 px-4 text-sm font-medium text-white shadow-sm hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-red-900 focus:ring-offset-2"}," 회원 탈퇴 ")])])])])}const T=p(w,[["render",B]]);export{T as default};
