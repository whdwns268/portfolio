package portfolio1;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class webpage {
	/*
	@Inject
	private SqlSessionFactory sqlSessionf;
	
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;
	 */
	@Inject
	private SqlSessionTemplate sqlSessoin;
	
	/*
	
	//@Resource = @Autowired
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;
	 */
	PrintWriter pw = null;
	RequestDispatcher rd = null;
	JSONObject jb = null;
	@RequestMapping("/admin/admin_main.do")
	public String admin_main(Model model, HttpServletRequest req, HttpServletResponse res) throws Exception{
		req.setCharacterEncoding("utf-8");
	    res.setCharacterEncoding("utf-8");
	    res.setContentType("text/html");
	    pw = res.getWriter();
	    HttpSession session = req.getSession();
	    if (session.getAttribute("uname") == null) {
	    	System.out.println("세션없음");
	    	pw.print("<script>alert('잘못된 접근입니다.'); location.href='./index.jsp';</script>");
	    	res.sendRedirect("./index.jsp");
	    	}
	    	else {
	    	System.out.println("세션있음");
	    	try {
				String uname = (String) session.getAttribute("uname");
				
				System.out.println(uname);
				if (uname == null || uname=="null" || uname=="") {
			        pw.print("<script>alert('잘못된 접근입니다.'); window.location.href='./index.jsp';</script>");
			    }
				List<portfoliovo> nc= sqlSessoin.selectList("portfolioDB.selectall");
				model.addAttribute("portfoliolist", nc);
				System.out.println(model);
				}
				catch (Exception e) {
					System.out.println("뭔가가 안된다잉");
				}
	    	}
		
		return "/admin/admin_main";
	}
	
	  @RequestMapping("/admin/logout.do")
	   public void logout(HttpServletRequest req, HttpServletResponse res) throws Exception {
	      req.setCharacterEncoding("utf-8");
	      res.setCharacterEncoding("utf-8");
	      res.setContentType("text/html");
	      HttpSession session = req.getSession();   
	      session.invalidate();
	      PrintWriter pw = res.getWriter();
	      
	      pw.print("<script>alert('로그아웃 하셨습니다'); location.href='./index.jsp';</script>");
	   }
	
	  
	  @SuppressWarnings("unchecked")
	  @RequestMapping("/admin/config_form.do")
	   public void config_form(@ModelAttribute("setuplist") shopsetupvo shopsetupvo,Model model,
			   HttpServletRequest req) throws Exception {
		  req.setCharacterEncoding("utf-8");
		  List<shopsetupvo> nc = sqlSessoin.selectList("portfolioDB.setupselect",shopsetupvo);
		  model.addAttribute("setuplist", nc);
		  JSONArray arr = new JSONArray();
		  for (shopsetupvo vo : nc) {
		        JSONObject jb = new JSONObject();
		        Field[] fields = vo.getClass().getDeclaredFields();
		        for (Field field : fields) {
		            try {
		                field.setAccessible(true);
		                jb.put(field.getName(), field.get(vo).toString().replaceAll("^\'|\'$", ""));
		            } catch (IllegalAccessException e) {
		                e.printStackTrace();
		            }
		        }
		        arr.add(jb);
		    }
		  jb = new JSONObject();
		  jb.put("setup",arr);
		  
		  String url = req.getServletContext().getRealPath("/");
		  try (FileWriter fw = new FileWriter(url+"setup.json")) {
			fw.write(jb.toJSONString());
		}catch (Exception e) {
			System.out.println("json실패");
		}
		  System.out.println(nc);	
		  System.out.println(jb);
	   }	
	  
		@RequestMapping("/admin/config_form_react.do")
		public void configformreact(@ModelAttribute("board") shopsetupvo shopsetupvo,HttpServletRequest req, HttpServletResponse res){
			res.setCharacterEncoding("UTF-8");
			res.addHeader("Access-Control-Allow-Origin", "*");
			res.addHeader("Access-Control-Allow-Credentials", "true");
			
			//pagevo.setSidx(6);
			List<shopsetupvo> nc = sqlSessoin.selectList("portfolioDB.setupselect",shopsetupvo);
			
			JSONArray array = new JSONArray();
			for(shopsetupvo datas : nc) {
				array.add(datas.getSwork());	//회사
				array.add(datas.getSceo());		//대표자명
				array.add(datas.getSno());		//사업자등록번호
				array.add(datas.getStel());		//대표전화
				array.add(datas.getSnu());		//통신판매업 신고번호
				array.add(datas.getSare());		//사업장 주소
				array.add(datas.getSname());	//정보관리책임자명 
				array.add(datas.getSema());		//정보책임자이메일
				array.add(datas.getSbank());	//무통장은행
				array.add(datas.getSbno());		//은행계좌번호
			}

			try {
				PrintWriter pw = res.getWriter();
				pw.print(array);
				pw.flush();
				pw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}


	  @PostMapping("admin/admin_confirm.do")
	   public void admin_confirm(@ModelAttribute("board") portfoliovo portfoliovo,HttpServletRequest req, HttpServletResponse res) throws Exception {
		  req.setCharacterEncoding("utf-8");
	      res.setCharacterEncoding("utf-8");
	      res.setContentType("text/html");
		  int r = sqlSessoin.update("portfolioDB.dataupdate",portfoliovo);
		  System.out.println(r);
		  PrintWriter pw = res.getWriter();
		  if(r > 0) {
				pw.print("<script>alert('정상적으로 승인되었습니다.'); location.href='./admin_main.do';</script>");
			}
			else {
				pw.print("<script>alert('오류가 발생되었습니다.'); location.href='./admin_main.do';</script>");
			}
		  
		  
	   }
	  
	  @PostMapping("/admin/config_form_update.do")
	   public void config_form_update(@ModelAttribute("setuplist") shopsetupvo shopsetupvo,Model model,HttpServletRequest req, HttpServletResponse res) throws Exception {
		  req.setCharacterEncoding("utf-8");
	      res.setCharacterEncoding("utf-8");
	      res.setContentType("text/html");
		  int r = sqlSessoin.update("portfolioDB.setup_update",shopsetupvo);
		  PrintWriter pw = res.getWriter();
		  if(r > 0) {
				pw.print("<script>alert('정상적으로 저장되었습니다.'); location.href='./config_form.do';</script>");
			}
			else {
				pw.print("<script>alert('오류가 발생되었습니다.'); location.href='./config_form.do';</script>");
			}
		  
	   }
	  
	  @RequestMapping("/admin/member_list.do")
	  public ModelAndView memberList(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
	          @RequestParam(value = "search_member", required = false) String searchMember,
	          @RequestParam(value = "member_input", required = false) String memberInput,
	          Model model, HttpServletRequest req, HttpServletResponse res) throws Exception {
	      res.setCharacterEncoding("utf-8");
	      res.setContentType("text/html");
	      PrintWriter pw = res.getWriter();
	      HttpSession session = req.getSession();
	      System.out.println(currentPage);
	      ModelAndView modelAndView = new ModelAndView();
	     
	      if (session.getAttribute("uname") == null) {
	          System.out.println("세션없음");
	          pw.print("<script>alert('잘못된 접근입니다.'); location.href='./index.jsp';</script>");
	          res.sendRedirect("./index.jsp");
	      } else {
	          System.out.println("세션있음");
	          try {
	              String uname = (String) session.getAttribute("uname");

	              System.out.println(uname);
	              if (uname == null || uname.equals("null") || uname.isEmpty()) {
	                  pw.print("<script>alert('잘못된 접근입니다.'); window.location.href='./index.jsp';</script>");
	              }
	              Map<String, Object> params = new HashMap<>();
	              
	              String search_member = req.getParameter("search_member");
	              System.out.println(search_member);
	              String member_input = req.getParameter("member_input");
	              System.out.println(member_input);
	              
	              params.put("memberInput", memberInput);
	              params.put("searchMember", searchMember);
	              int pageSize = 8; // 한 페이지에 보여줄 항목 수
	              int totalCount = sqlSessoin.selectOne("portfolioDB.getTotalCount",params);
	              int pageGroupSize = 5; // 페이지 그룹 크기
	             System.out.println(totalCount);
	              // 파라미터로 넘어온 현재 페이지 번호를 가져옴
	              if (currentPage == 0) {
	                  currentPage = 1;
	              }

	              int startRow = (currentPage - 1) * pageSize + 0; // 시작 항목 인덱스 계산
	              int endRow = Math.min(startRow + pageSize - 1, totalCount); // 끝 항목 인덱스 계산
	              
	              modelAndView.setViewName("/admin/member_list");
	              modelAndView.addObject("model", model);
	              
	              params.put("startRow", startRow);
	              params.put("pageSize", pageSize);
	             
	              
	              List<smembervo> nc2 = sqlSessoin.selectList("portfolioDB.mselectPaging", params);
	            
	              
	              
	              model.addAttribute("member_input", member_input);
	              model.addAttribute("search_member", search_member);
	              model.addAttribute("memberlist", nc2);
	              System.out.println(nc2);
	              
	              model.addAttribute("currentPage", currentPage);
	              model.addAttribute("pageSize", pageSize);
	              model.addAttribute("totalCount", totalCount);
	              model.addAttribute("pageGroupSize", pageGroupSize);
	              model.addAttribute("startRow", startRow);
	              model.addAttribute("endRows", endRow);

	              //List<smembervo> nc2 = sqlSessoin.selectList("portfolioDB.mselectall");         
	          } catch (Exception e) {
	              System.out.println("뭔가가 안된다잉" + e);
	          }
	      }
	      return modelAndView;
	  }

	  @RequestMapping("/admin/member_modify.do")
	   public void  member_modify(@ModelAttribute("board") smembervo smembervo, HttpServletRequest req, HttpServletResponse res)throws Exception {
		  req.setCharacterEncoding("utf-8");
	      res.setCharacterEncoding("utf-8");
	      res.setContentType("text/html");
	      
	      String sid = req.getParameter("sid");
          System.out.println(sid);

          Map<String, Object> params = new HashMap<>();
			try {
				params.put("memberInput", sid);
				params.put("searchMember", "sid");
				params.put("startRow", 0);
	            params.put("pageSize", 10);
				List<smembervo> nc2 = sqlSessoin.selectList("portfolioDB.mselectPaging", params);
	            System.out.println(nc2);
	            req.setAttribute("memberlist", nc2);
	              System.out.println(nc2);
	              
			   }catch (Exception e) {
				   System.out.println(e);
			   }
			this.rd = req.getRequestDispatcher("./member_modify.jsp");
			this.rd.forward(req, res);  
	  	}
	  
	  @PostMapping("/admin/membermodifyok.do")
	  public void  membermodifyoks(@ModelAttribute("mupdate") smembervo smembervo, 
	   HttpServletRequest req, HttpServletResponse res)throws Exception {
		  req.setCharacterEncoding("utf-8");
	      res.setCharacterEncoding("utf-8");
	      res.setContentType("text/html");
		  int r = sqlSessoin.update("portfolioDB.memberupdate",smembervo);
		  System.out.println(r);
		  PrintWriter pw = res.getWriter();
		  if(r > 0) {
				pw.print("<script>alert('정상적으로 변경되었습니다.'); window.close();</script>");
			}
			else {
				pw.print("<script>alert('오류가 발생되었습니다.'); history.back();</script>");
			}
	  }
	  
	  
	  @PostMapping("/admin/mstate.do")
	  public void  mstate(@ModelAttribute("mstate") smembervo smembervo, 
	   HttpServletRequest req, HttpServletResponse res)throws Exception {
		  req.setCharacterEncoding("utf-8");
	      res.setCharacterEncoding("utf-8");
	      res.setContentType("text/html");
		  int r = sqlSessoin.update("portfolioDB.memberstate",smembervo);
		  System.out.println(r);
		  PrintWriter pw = res.getWriter();
		  if(r > 0) {
				pw.print("<script>alert('정지되었습니다.'); history.back();</script>");
			}
			else {
				pw.print("<script>alert('오류가 발생되었습니다.'); history.back();</script>");
			}
	  }
	  
	  @PostMapping("/admin/ckdelete.do")
	  public void  ckdelete(@ModelAttribute("mstate") smembervo smembervo,
			  HttpServletRequest req, HttpServletResponse res)throws Exception {
		  req.setCharacterEncoding("utf-8");
	      res.setCharacterEncoding("utf-8");
	      res.setContentType("text/html");
	      
//	      String[] cksidArray = req.getParameterValues("cksid");
//	      System.out.println(cksidArray);
		  int r = sqlSessoin.delete("portfolioDB.memberdelete",smembervo);
		  PrintWriter pw = res.getWriter();
		  if(r > 0) {
				pw.print("<script>alert('정상적으로 삭제되었습니다.'); location.href='./member_list.do';</script>");
			}
			else {
				pw.print("<script>alert('오류가 발생되었습니다.'); history.back();</script>");
			}
	  }
	  
	  @RequestMapping("/admin/notice_list.do")
	  public ModelAndView noticeList(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
	          Model model, HttpServletRequest req, HttpServletResponse res) throws Exception {
	      res.setCharacterEncoding("utf-8");
	      res.setContentType("text/html");
	      PrintWriter pw = res.getWriter();
	      HttpSession session = req.getSession();
	      System.out.println(currentPage);
	      ModelAndView modelAndView = new ModelAndView();
	     
	      if (session.getAttribute("uname") == null) {
	          System.out.println("세션없음");
	          pw.print("<script>alert('잘못된 접근입니다.'); location.href='./index.jsp';</script>");
	          res.sendRedirect("./index.jsp");
	      } else {
	          System.out.println("세션있음");
	          try {
	              String uname = (String) session.getAttribute("uname");

	              System.out.println(uname);
	              if (uname == null || uname.equals("null") || uname.isEmpty()) {
	                  pw.print("<script>alert('잘못된 접근입니다.'); window.location.href='./index.jsp';</script>");
	              }
	              
	              Map<String, Object> params = new HashMap<>();
	              int pageSize = 8; // 한 페이지에 보여줄 항목 수
	              int totalCount = sqlSessoin.selectOne("portfolioDB.noticeTotalCount",params);
	              int pageGroupSize = 5; // 페이지 그룹 크기
	              // 파라미터로 넘어온 현재 페이지 번호를 가져옴
	              if (currentPage == 0) {
	                  currentPage = 1;
	              }

	              int startRow = (currentPage - 1) * pageSize + 0; // 시작 항목 인덱스 계산
	              int endRow = Math.min(startRow + pageSize - 1, totalCount); // 끝 항목 인덱스 계산
	              
	              modelAndView.setViewName("/admin/notice_list");
	              modelAndView.addObject("model", model);
	              
	              params.put("startRow", startRow);
	              params.put("pageSize", pageSize);
	             
	              
	              List<noticevo> nc3 = sqlSessoin.selectList("portfolioDB.notice_all", params);
	              System.out.println(nc3);

	              model.addAttribute("noticelist", nc3);
	              model.addAttribute("currentPage", currentPage);
	              model.addAttribute("pageSize", pageSize);
	              model.addAttribute("totalCount", totalCount);
	              model.addAttribute("pageGroupSize", pageGroupSize);
	              model.addAttribute("startRow", startRow);
	              model.addAttribute("endRows", endRow);
 
	          } catch (Exception e) {
	              System.out.println("뭔가가 안된다잉" + e);
	          }
	      }
	      return modelAndView;
	  }
	  
	  
	  
	  @RequestMapping("/admin/notice_writer.do")
	   public void  notice_writer() {
	  }
	  
	  @RequestMapping("/admin/notice_view.do")
	   public void notice_view(@ModelAttribute("noticeview") noticevo noticevo,Model model,HttpServletRequest req) throws Exception {
		  String nidx = req.getParameter("nidx");
		  List<noticevo> nc = sqlSessoin.selectList("portfolioDB.noticeOneSelect",nidx);
		  model.addAttribute("notilist", nc);
	  }
	  
	  @RequestMapping("/admin/notice_modify.do")
	   public void notice_modify(@ModelAttribute("noticeview") noticevo noticevo,Model model,HttpServletRequest req) throws Exception {
		  String nidx = req.getParameter("nidx");
		  List<noticevo> nc = sqlSessoin.selectList("portfolioDB.noticeOneSelect",nidx);
		  model.addAttribute("notilist", nc);
	  }
	  
	  @RequestMapping("/admin/notice_modify_update.do")
	   public void notice_modify_update(@ModelAttribute("noticeupdate") noticevo noticevo,Model model,
			   HttpServletRequest req, HttpServletResponse res) throws Exception {
		 
		  req.setCharacterEncoding("utf-8");
	      res.setCharacterEncoding("utf-8");
	      res.setContentType("text/html");
		  PrintWriter pw = res.getWriter();
		  int r = sqlSessoin.update("portfolioDB.noticeupdate",noticevo);
		  if(r > 0) {
			  pw.print("<script>alert('정상적으로 승인되었습니다.'); location.href='notice_view.do?nidx="+noticevo.getNidx()+"';</script>");
			}
			else {
				pw.print("<script>alert('오류가 발생되었습니다.'); history.back();</script>");
			}
	  }
	  
	  @RequestMapping("/admin/notice_writer_upload.do")
	   public void  notice_writer_upload(@ModelAttribute("noticeupload") noticevo noticevo,
		  MultipartFile file,
		  HttpServletRequest req, HttpServletResponse res) throws Exception{
		  req.setCharacterEncoding("utf-8");
	      res.setCharacterEncoding("utf-8");
	      res.setContentType("text/html");
	      
	      String filett = (String)req.getParameter("nfilename");
	      System.out.println(filett);
		 if(file.isEmpty()) {
			 System.out.println("파일없음");		 }
		 else {
				 String savePath = req.getSession().getServletContext().getRealPath("/fileup/");
				 String filename = file.getOriginalFilename();
				 
				 try {
					 FileOutputStream fos = new FileOutputStream(new File(savePath+filename));
					 BufferedOutputStream bos = new BufferedOutputStream(fos);
					 
					 byte[] bytes = file.getBytes();
						bos.write(bytes);
						bos.close();
						
						
						System.out.println("파일업로드 작업 완료 -> DB작업시작");
						
						try {
							
							int r = sqlSessoin.insert("portfolioDB.noticeinsert",noticevo);
							PrintWriter pw = res.getWriter();
							if(r > 0) {
								pw.print("<script>alert('정상적으로 승인되었습니다.'); location.href='./notice_list.do';</script>");
							}
							else {
								pw.print("<script>alert('오류가 발생되었습니다.'); history.back();</script>");
							}
							
						}catch (Exception e) {
							System.out.println("sql실패 : " + e);
						}
						
						
				 }catch (Exception e) {
					 System.out.println("파일 업로드 실패 : " + e);
				}
				
			 }
			 
		 }
	  
	  @GetMapping("/admin/download.do")
	  public void downloadFile(HttpServletRequest req,HttpServletResponse response) throws IOException {
	      // 파일 경로 및 파일명 설정
		  String filePath = req.getSession().getServletContext().getRealPath("/fileup/");
	      String fileName = req.getParameter("filename");
	      File downloadFile = new File(filePath + fileName);
	      byte fileByte[] = FileUtils.readFileToByteArray(downloadFile);
	      
	      response.setContentType("application/octet-stream");
          response.setContentLength(fileByte.length);
          
          response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName,"UTF-8") +"\";");
          response.setHeader("Content-Transfer-Encoding", "binary");
          
          response.getOutputStream().write(fileByte);
          response.getOutputStream().flush();
          response.getOutputStream().close();
	  }

	  @PostMapping("/admin/notice_ckdelete.do")
	  public void  notice_ckdelete(@ModelAttribute("noti_delete") noticevo noticevo,
		  HttpServletRequest req, HttpServletResponse res)throws Exception {
		  req.setCharacterEncoding("utf-8");
	      res.setCharacterEncoding("utf-8");
	      res.setContentType("text/html");
	      
		  int r = sqlSessoin.delete("portfolioDB.noticedelete",noticevo);
		  PrintWriter pw = res.getWriter();
		  if(r > 0) {
				pw.print("<script>alert('정상적으로 삭제되었습니다.'); location.href='./notice_list.do';</script>");
			}
			else {
				pw.print("<script>alert('오류가 발생되었습니다.'); history.back();</script>");
			}
	  }
	  
	  @RequestMapping("/admin/product_upload.do")
	   public void  product_upload(@ModelAttribute("productvo") productvo productvo,
		  HttpServletRequest req, HttpServletResponse res) throws Exception{
		  req.setCharacterEncoding("utf-8");
	      res.setCharacterEncoding("utf-8");
	      res.setContentType("application/x-www-form-urlencoded");
	      res.setContentType("text/html");

	      
	      if(productvo.files[0].isEmpty()) {
			 System.out.println("파일없음");		 }
		  else {
			 	for(int i=0; i < productvo.files.length; i++) {
				 String savePath = req.getSession().getServletContext().getRealPath("/fileup/");
				 String filename = productvo.files[i].getOriginalFilename();
				 
				 if (i == 0) {
				        productvo.setPfile1("./fileup/" + filename);
				    } else if (i == 1) {
				        productvo.setPfile2(filename);
				    } else if (i == 2) {
				        productvo.setPfile3(filename);
				    }
				 
				 try {
					 FileOutputStream fos = new FileOutputStream(new File(savePath+filename));
					 BufferedOutputStream bos = new BufferedOutputStream(fos);
					 
					 byte[] bytes = productvo.files[i].getBytes();
						bos.write(bytes);
						bos.close();
				 }catch (Exception e) {
						System.out.println("sql실패 : " + e);
					}
			 	}
						
				System.out.println("파일업로드 작업 완료 -> DB작업시작");
						
				try {
							
					int r = sqlSessoin.insert("portfolioDB.productinsert",productvo);
					PrintWriter pw = res.getWriter();
					if(r > 0) {
						pw.print("<script>alert('정상적으로 승인되었습니다.'); location.href='./shop_product.do';</script>");
					}
					else {
						pw.print("<script>alert('오류가 발생되었습니다.'); history.back();</script>");
					}
				}catch (Exception e) {
					 System.out.println("파일 업로드 실패 : " + e);
				}
			 }
		}
	  @RequestMapping("/admin/shop_setup_code.do")
	   public void shop_setup_code(HttpServletRequest req, HttpServletResponse res) throws Exception{
		  req.setCharacterEncoding("utf-8");
	      res.setCharacterEncoding("utf-8");
	      res.setContentType("text/html");
	      
	      String pcodeck = req.getParameter("pcodeck");
	      System.out.println(pcodeck);
	      int pck = sqlSessoin.selectOne("portfolioDB.pcodeSelect",pcodeck);
	      
	      this.pw = res.getWriter();
	      if(pck == 1) {
	    	  res.getWriter().write("no");
	      }
	      else if(pck == 0) {
	    	  res.getWriter().write("yes");
	      }
	  }
	  
	  @RequestMapping("/admin/shop_product.do")
	  public ModelAndView product_List(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
	          @RequestParam(value = "search_member", required = false) String searchMember,
	          @RequestParam(value = "member_input", required = false) String memberInput,
	          Model model, HttpServletRequest req, HttpServletResponse res) throws Exception {
	      res.setCharacterEncoding("utf-8");
	      res.setContentType("text/html");
	      PrintWriter pw = res.getWriter();
	      HttpSession session = req.getSession();
	      System.out.println(currentPage);
	      ModelAndView modelAndView = new ModelAndView();
	     
	      if (session.getAttribute("uname") == null) {
	          System.out.println("세션없음");
	          pw.print("<script>alert('잘못된 접근입니다.'); location.href='./index.jsp';</script>");
	          res.sendRedirect("./index.jsp");
	      } else {
	          System.out.println("세션있음");
	          try {
	              String uname = (String) session.getAttribute("uname");

	              System.out.println(uname);
	              if (uname == null || uname.equals("null") || uname.isEmpty()) {
	                  pw.print("<script>alert('잘못된 접근입니다.'); window.location.href='./index.jsp';</script>");
	              }
	              Map<String, Object> params = new HashMap<>();
	              
	              String search_member = req.getParameter("search_member");
	              System.out.println(search_member);
	              String member_input = req.getParameter("member_input");
	              System.out.println(member_input);
	              
	              params.put("memberInput", memberInput);
	              params.put("searchMember", searchMember);
	              int pageSize = 8; // 한 페이지에 보여줄 항목 수
	              int totalCount = sqlSessoin.selectOne("portfolioDB.productCount",params);
	              int pageGroupSize = 5; // 페이지 그룹 크기
	             System.out.println(totalCount);
	              // 파라미터로 넘어온 현재 페이지 번호를 가져옴
	              if (currentPage == 0) {
	                  currentPage = 1;
	              }

	              int startRow = (currentPage - 1) * pageSize + 0; // 시작 항목 인덱스 계산
	              int endRow = Math.min(startRow + pageSize - 1, totalCount); // 끝 항목 인덱스 계산
	              
	              modelAndView.setViewName("/admin/shop_product");
	              modelAndView.addObject("model", model);
	              
	              params.put("startRow", startRow);
	              params.put("pageSize", pageSize);
	             
	              
	              List<smembervo> nc2 = sqlSessoin.selectList("portfolioDB.productlist", params);
	            
	              
	              
	              model.addAttribute("member_input", member_input);
	              model.addAttribute("search_member", search_member);
	              model.addAttribute("memberlist", nc2);
	              System.out.println(nc2);
	              
	              model.addAttribute("currentPage", currentPage);
	              model.addAttribute("pageSize", pageSize);
	              model.addAttribute("totalCount", totalCount);
	              model.addAttribute("pageGroupSize", pageGroupSize);
	              model.addAttribute("startRow", startRow);
	              model.addAttribute("endRows", endRow);

	              //List<smembervo> nc2 = sqlSessoin.selectList("portfolioDB.mselectall");         
	          } catch (Exception e) {
	              System.out.println("뭔가가 안된다잉" + e);
	          }
	      }
	      return modelAndView;
	  }
	  
	  
	  @PostMapping("/admin/product_ckdelete.do")
	  public void  product_ckdelete(@ModelAttribute("pro_delete") productvo productvo,
		  HttpServletRequest req, HttpServletResponse res)throws Exception {
		  req.setCharacterEncoding("utf-8");
	      res.setCharacterEncoding("utf-8");
	      res.setContentType("text/html");
	      
		  int r = sqlSessoin.delete("portfolioDB.productdelete",productvo);
		  PrintWriter pw = res.getWriter();
		  if(r > 0) {
				pw.print("<script>alert('정상적으로 삭제되었습니다.'); location.href='./shop_product.do';</script>");
			}
			else {
				pw.print("<script>alert('오류가 발생되었습니다.'); history.back();</script>");
			}
	  }
	  
	  @RequestMapping("/joinok.do")
	  public void  joinok(@ModelAttribute("joinok") smembervo smembervo,
			  @ModelAttribute("ssss") shopsetupvo shopsetupvo,
		  HttpServletRequest req, HttpServletResponse res)throws Exception {
		  req.setCharacterEncoding("utf-8");
	      res.setCharacterEncoding("utf-8");
	      res.setContentType("text/html");
	      
	      String pointck = sqlSessoin.selectOne("portfolioDB.pointck");
	      String levelck = sqlSessoin.selectOne("portfolioDB.levelck");
	      
	      //smembervo.setSpoint(shopsetupvo.getSpoint());
	      System.out.println(pointck);
	      smembervo.setSpoint(pointck);
	      smembervo.setSlevel(levelck);
		  if(smembervo.getSadmail() == null) {
			  smembervo.setSadmail("N");
		  }
		  
		  if(smembervo.getSadsms() == null) {
			  smembervo.setSadsms("N");
		  }
		  
		  int r = sqlSessoin.insert("portfolioDB.userinsert",smembervo);
			PrintWriter pw = res.getWriter();
			if(r > 0) {
				pw.print("<script>alert('정상적으로 회원가입 되었습니다.'); location.href='./index.jsp';</script>");
			}
			else {
				pw.print("<script>alert('오류가 발생되었습니다.'); history.back();</script>");
			}
	  }
	  
	  
	  @RequestMapping("/admin/shop_setup.do")
		public void index(@ModelAttribute("bnupdate") productvo productvo,
				HttpServletRequest req,Model model) throws Exception {
		
		List<productvo> nc = sqlSessoin.selectList("portfolioDB.bannerapi",productvo);
		model.addAttribute("bannerlist", nc);

		}
	  
	  
	  @PostMapping("/admin/shop_banne_update.do")
	  public void  shop_banne_update(@ModelAttribute("bnupdate") productvo productvo,
		  HttpServletRequest req, HttpServletResponse res)throws Exception {
		  req.setCharacterEncoding("utf-8");
	      res.setCharacterEncoding("utf-8");
	      res.setContentType("text/html");
	      
		  int r = sqlSessoin.update("portfolioDB.banner_update",productvo);
		  PrintWriter pw = res.getWriter();
		  if(r > 0) {
				pw.print("<script>alert('정상적으로 업데이트되었습니다.'); location.href='./shop_setup.do';</script>");
			}
			else {
				pw.print("<script>alert('오류가 발생되었습니다.'); history.back();</script>");
			}
	  }
	  
	  @RequestMapping("/admin/banner_api.do")
		public void configformreact(@ModelAttribute("bnapi") productvo productvo,HttpServletRequest req, HttpServletResponse res){
			res.setCharacterEncoding("UTF-8");
			res.addHeader("Access-Control-Allow-Origin", "*");
			res.addHeader("Access-Control-Allow-Credentials", "true");
			
			//pagevo.setSidx(6);
			List<productvo> nc = sqlSessoin.selectList("portfolioDB.bannerapi",productvo);
			System.out.println(productvo.banner1);
			JSONArray array = new JSONArray();
			for(productvo datas : nc) {
				array.add(datas.getBanner1());
				array.add(datas.getLink1());
				array.add(datas.getBanner2());
				array.add(datas.getLink2());
				array.add(datas.getBanner3());
				array.add(datas.getLink3());
				array.add(datas.getBanner4());
				array.add(datas.getLink4());
			}

			try {
				PrintWriter pw = res.getWriter();
				pw.print(array);
				pw.flush();
				pw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	  
	  @RequestMapping("/admin/notice_api.do")
		public void notice_api(HttpServletRequest req, HttpServletResponse res,noticevo noticevo){
			res.setCharacterEncoding("UTF-8");
			res.addHeader("Access-Control-Allow-Origin", "*");
			res.addHeader("Access-Control-Allow-Credentials", "true");

			List<noticevo> nc3 = sqlSessoin.selectList("portfolioDB.notice_api",noticevo);
			System.out.println(nc3);
			JSONArray array = new JSONArray();
			for(noticevo datas : nc3) {
				array.add(datas.getNidx());
				array.add(datas.getNtitle());
				array.add(datas.getNupdate().toString());
			}
			try {
				PrintWriter pw = res.getWriter();
				pw.print(array);
				pw.flush();
				pw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}










