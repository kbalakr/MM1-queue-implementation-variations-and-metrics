package project1;

import java.util.LinkedList;

import jxl.Workbook;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class simulation1 {
	public static void part1() throws IOException, RowsExceededException, WriteException {		
		int total_customers=10000;			                             
		float lamda[]= {0.7f,0.8f,0.9f,0.95f};
		float arrival_rate_all[][][]=new float[lamda.length][10][total_customers];
		float waiting_time_all[][][]=new float[lamda.length][10][total_customers];
		float number_of_customers_d[][][]=new float[lamda.length][10][total_customers];
		float number_of_customers_a[][][]=new float[lamda.length][10][total_customers];
		
		//float arrival_rate_all_avg[][]=new float[lamda.length][total_customers];
		//float waiting_time_all_avg[][]=new float[lamda.length][total_customers];
		//float number_of_customers_d_avg[][]=new float[lamda.length][total_customers];
		//float number_of_customers_a_avg[][]=new float[lamda.length][total_customers];
		
		int lamda_iter=0;
		for(float x:lamda) {
			for (int k=0;k<10;k++) {
				int N=0; 
				int na=0;
				float arrival_rate =0;     
				float service_rate = Float.MAX_VALUE;
				ArrayList<Integer> noca=new ArrayList<>();
				LinkedList<Float> queue=new LinkedList<>();
				while(N<total_customers) {
					if(service_rate<arrival_rate || na>=total_customers){
						arrival_rate_all[lamda_iter][k][N]=queue.peek();
						if(service_rate-queue.peek()>=0)
							waiting_time_all[lamda_iter][k][N]=service_rate-queue.peek();
						queue.remove();
						number_of_customers_d[lamda_iter][k][N]=queue.size();
						N++;
						if (queue.size()!=0)
							service_rate+=(-(Math.log(1-Math.random())));
						else
							service_rate=Float.MAX_VALUE;
					}
					if(na<total_customers && service_rate>=arrival_rate) {
						arrival_rate+=-((Math.log(1-Math.random()))/x);
						na++;
						if(queue.size()==0)
							service_rate=arrival_rate+(float)(-(Math.log(1-Math.random())));
						noca.add(queue.size());
						queue.add(arrival_rate);
					}
				}
				int i=0;
				Iterator<Integer> iter=noca.listIterator();
				while(iter.hasNext()) {
					number_of_customers_a[lamda_iter][k][i++]=iter.next();
				}
				noca.clear();
			}
			lamda_iter++;		
		}
		/*for (int li=0;li<lamda.length;li++) {
			for(int i=0;i<total_customers;i++) {
				float temp1sum=0;
				float temp2sum=0;
				float temp3sum=0;
				float temp4sum=0;
				for(int k=0;k<10;k++) {
					temp1sum+=arrival_rate_all[li][k][i];
					temp2sum+=waiting_time_all[li][k][i];
					temp3sum+=number_of_customers_a[li][k][i];
					temp4sum+=number_of_customers_d[li][k][i];
				}
				arrival_rate_all_avg[li][i]=temp1sum/10;
				waiting_time_all_avg[li][i]=temp2sum/10;
				number_of_customers_a_avg[li][i]=temp3sum/10;
				number_of_customers_d_avg[li][i]=temp4sum/10;				
			}
		}*/
		
		File f=new File("C:\\Users\\kbala\\Desktop\\excel.xls");
		WritableWorkbook excel=Workbook.createWorkbook(f);
		NumberFormat floatingNo = new NumberFormat("0.00"); 
		NumberFormat wholeNo = new NumberFormat("0"); 
		WritableCellFormat floatingNumberFormat = new WritableCellFormat(floatingNo); 
		WritableCellFormat wholeNumberFormat = new WritableCellFormat(wholeNo);
		//WritableSheet sheet=excel.createSheet("sheet1", 0);
		WritableSheet sheet[]=new WritableSheet[10];
		for (int k=0;k<10;k++) {
			sheet[k]=excel.createSheet(String.valueOf(k),0);
			for (int i=0;i<lamda.length;i++) {
				for (int j=0;j<total_customers;j++) {
					Number ar = new Number(0+i*lamda.length, j, arrival_rate_all[i][k][j], floatingNumberFormat);
					Number wt = new Number(1+i*lamda.length, j, waiting_time_all[i][k][j], floatingNumberFormat);
					Number numbera = new Number(2+i*lamda.length, j, number_of_customers_a[i][k][j], wholeNumberFormat);
					Number numberd = new Number(3+i*lamda.length, j, number_of_customers_d[i][k][j], wholeNumberFormat);
					sheet[k].addCell(ar);
					sheet[k].addCell(wt);
					sheet[k].addCell(numbera);
					sheet[k].addCell(numberd);
				}
			}
		}
			
		excel.write();
		excel.close();
	}
	
	public static void part2() throws IOException, RowsExceededException, WriteException {
		int m=10;
		int total_customers=50000;
		float lamda[]= {7.0f,8.0f,9.0f,9.5f};
		int lamda_iter=0;
		float waiting_time_final[][][]=new float[lamda.length][10][total_customers];
		for(float x:lamda) {
			for (int k=0;k<10;k++) {				
				float arrival_rate=0;
				float service_rate=Float.MAX_VALUE;
				LinkedList<Customers> queue[]=new LinkedList[m];
				queues q[]=new queues[m];
				for (int i=0;i<m;i++) {
					queue[i]=new LinkedList<>();
				}
				for (int i=0;i<m;i++) {
					q[i]=new queues(queue[i],0,0);
				}
				int N=0, na=0;
				Customers cust[]=new Customers[total_customers];
				
				while(N<total_customers) {
					Arrays.sort(q,new sortQueue());
					
					for (int i=0;i<m;i++) {
						q[i].service_rnd=(float)(-(Math.log(1-Math.random())));
					}
					float arrival_rnd=(float)(-((Math.log(1-Math.random()))/x));
					
					if(na<total_customers) {
						arrival_rate+=arrival_rnd;
						if(q[0].queue.size()==0) {
							service_rate=arrival_rate+q[0].service_rnd;
						}
						cust[na]=new Customers(na+1,arrival_rate,0);
						q[0].queue.add(cust[na]);
						q[0].service_rate=service_rate;
						na++;
					}
					
					for(int i=0;i<m;i++) {
						if(arrival_rnd>q[i].service_rnd) {
							if(q[i].queue.size()!=0) {
								Customers ctemp=q[i].queue.peek();					
								cust[(ctemp.N)-1].waiting_time=(q[i].service_rate-ctemp.arrival_rate)<0?0:q[i].service_rate-ctemp.arrival_rate;
								q[i].queue.remove();
								N++;
								if(q[i].queue.size()!=0) {
									q[i].service_rate+=q[i].service_rnd;
								}
								else {
									q[i].service_rate=Float.MAX_VALUE;
								}
							}
						}					
					}
						
				}
				
				for (int i=0;i<total_customers;i++) {
					waiting_time_final[lamda_iter][k][i]=cust[i].waiting_time;
				}				
			}
			lamda_iter++;
		}
		
		File f=new File("C:\\Users\\kbala\\Desktop\\excel2.xls");
		WritableWorkbook excel=Workbook.createWorkbook(f);
		NumberFormat floatingNo = new NumberFormat("0.00");
		WritableCellFormat floatingNumberFormat = new WritableCellFormat(floatingNo);
		WritableSheet sheet[]=new WritableSheet[10];
		for (int k=0;k<10;k++) {
			sheet[k]=excel.createSheet(String.valueOf(k),0);
			for (int i=0;i<lamda.length;i++) {
				for (int j=0;j<total_customers;j++) {
					Number wt = new Number(i, j, waiting_time_final[i][k][j], floatingNumberFormat);
					sheet[k].addCell(wt);
				}
			}
		}			
		excel.write();
		excel.close();		
	}
	
	public static void part3() throws IOException, RowsExceededException, WriteException {
		int m=10;
		int total_customers=50000;
		float lamda[]= {7.0f,8.0f,9.0f,9.5f};
		int lamda_iter=0;
		float waiting_time_final[][][]=new float[lamda.length][10][total_customers];
		
		for(float x:lamda) {
			for (int k=0;k<10;k++) {				
				float arrival_rate=0;
				float service_rate=Float.MAX_VALUE;
				LinkedList<Customers> queue[]=new LinkedList[m];
				queues q[]=new queues[m];
				for (int i=0;i<m;i++) {
					queue[i]=new LinkedList<>();
				}
				for (int i=0;i<m;i++) {
					q[i]=new queues(queue[i],0,0);
				}
				int N=0, na=0;
				Customers cust[]=new Customers[total_customers];
				
				while(N<total_customers) {
					int queue1=getRandom(m-1);
					int queue2=0;
					while(true) {
						queue2=getRandom(m-1);
						if(queue2!=queue1)
							break;
					}
					
					int queue_to_join=q[queue1].queue.size()<q[queue2].queue.size()?queue1:queue2;
					
					for (int i=0;i<m;i++) {
						q[i].service_rnd=(float)(-(Math.log(1-Math.random())));
					}
					float arrival_rnd=(float)(-((Math.log(1-Math.random()))/x));
					
					if(na<total_customers) {
						arrival_rate+=arrival_rnd;
						if(q[queue_to_join].queue.size()==0) {
							service_rate=arrival_rate+q[queue_to_join].service_rnd;
						}
						cust[na]=new Customers(na+1,arrival_rate,0);
						q[queue_to_join].queue.add(cust[na]);
						q[queue_to_join].service_rate=service_rate;
						na++;
					}
					
					for(int i=0;i<m;i++) {
						if(arrival_rnd>q[i].service_rnd) {
							if(q[i].queue.size()!=0) {
								Customers ctemp=q[i].queue.peek();					
								cust[(ctemp.N)-1].waiting_time=(q[i].service_rate-ctemp.arrival_rate)<0?0:q[i].service_rate-ctemp.arrival_rate;
								q[i].queue.remove();
								N++;
								if(q[i].queue.size()!=0) {
									q[i].service_rate+=q[i].service_rnd;
								}
								else {
									q[i].service_rate=Float.MAX_VALUE;
								}
							}
						}					
					}
						
				}
				
				for (int i=0;i<total_customers;i++) {
					waiting_time_final[lamda_iter][k][i]=cust[i].waiting_time;
				}				
			}
			lamda_iter++;
		}
		
		File f=new File("C:\\Users\\kbala\\Desktop\\excel3.xls");
		WritableWorkbook excel=Workbook.createWorkbook(f);
		NumberFormat floatingNo = new NumberFormat("0.00");
		WritableCellFormat floatingNumberFormat = new WritableCellFormat(floatingNo);
		WritableSheet sheet[]=new WritableSheet[10];
		for (int k=0;k<10;k++) {
			sheet[k]=excel.createSheet(String.valueOf(k),0);
			for (int i=0;i<lamda.length;i++) {
				for (int j=0;j<total_customers;j++) {
					Number wt = new Number(i, j, waiting_time_final[i][k][j], floatingNumberFormat);
					sheet[k].addCell(wt);
				}
			}
		}			
		excel.write();
		excel.close();		
	}
	public static class queues{
		float service_rnd=0, service_rate=0;
		LinkedList<Customers> queue=new LinkedList<>();
		public queues(LinkedList<Customers> queue, float service_rnd, float service_rate) {
			this.queue=queue;
			this.service_rate=service_rate;
			this.service_rnd=service_rnd;
		}
	}
	
	public static int getRandom(int max){ 
		return (int) (Math.random()*max);
	}
	
	public static class Customers{
		int N=0; 
		float arrival_rate=0;
		float waiting_time=0;
		public Customers(int N, float arrival_rate, float waiting_time) {
			this.N=N;
			this.arrival_rate=arrival_rate;
			this.waiting_time=0;
		}
	}
	
	private static class sortQueue implements Comparator<queues>{
		@Override
		public int compare(queues q1,queues q2) {
			return q1.queue.size()-q2.queue.size();
		}
	}
	
	public static void main(String args[]) throws RowsExceededException, WriteException, IOException {
		part1();
		part2();
		part3();
	}
}
