filename='excel2.xls';
for i=1:10    
        lamda1w(i,:)=xlsread(filename,i,'A1:A50000');
        lamda2w(i,:)=xlsread(filename,i,'B1:B50000');
        lamda3w(i,:)=xlsread(filename,i,'C1:C50000');
        lamda4w(i,:)=xlsread(filename,i,'D1:D50000');   
end

for i=1:10
    [counts1w(i,:),edge1w]=hist(lamda1w(i,:));
    [counts2w(i,:),edge2w]=hist(lamda2w(i,:));
    [counts3w(i,:),edge3w]=hist(lamda3w(i,:));
    [counts4w(i,:),edge4w]=hist(lamda4w(i,:));
end

for i=1:10
    cdf1w(i,:)=cumsum(counts1w(i,:))/sum(counts1w(i,:));
    cdf2w(i,:)=cumsum(counts2w(i,:))/sum(counts2w(i,:));
    cdf3w(i,:)=cumsum(counts3w(i,:))/sum(counts3w(i,:));
    cdf4w(i,:)=cumsum(counts4w(i,:))/sum(counts4w(i,:));
end

ccdf1w=1-cdf1w;
ccdf2w=1-cdf2w;
ccdf3w=1-cdf3w;
ccdf4w=1-cdf4w;

ccdf1w_avg=mean(ccdf1w);
ccdf2w_avg=mean(ccdf2w);
ccdf3w_avg=mean(ccdf3w);
ccdf4w_avg=mean(ccdf4w);

figure;
semilogy(edge1w,ccdf1w_avg,'r'); 
hold on;
semilogy(edge2w,ccdf2w_avg,'g');
semilogy(edge3w,ccdf3w_avg,'b'); 
semilogy(edge4w,ccdf4w_avg,'m');
title('CCDF of Waiting Time');
xlabel('Waiting time (Join shortest queue)');
ylabel('P(N>n) (semilog)');
legend('lamda = 7 ','lamda = 8','lamda = 9','lamda = 9.5','Location','southeast');
hold off;