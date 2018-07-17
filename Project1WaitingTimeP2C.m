filename='excel3.xls';
for i=1:10    
        lamda1w2(i,:)=xlsread(filename,i,'A1:A50000');
        lamda2w2(i,:)=xlsread(filename,i,'B1:B50000');
        lamda3w2(i,:)=xlsread(filename,i,'C1:C50000');
        lamda4w2(i,:)=xlsread(filename,i,'D1:D50000');   
end

for i=1:10
    [counts1w2(i,:),edge1w2]=hist(lamda1w2(i,:));
    [counts2w2(i,:),edge2w2]=hist(lamda2w2(i,:));
    [counts3w2(i,:),edge3w2]=hist(lamda3w2(i,:));
    [counts4w2(i,:),edge4w2]=hist(lamda4w2(i,:));
end

for i=1:10
    cdf1w2(i,:)=cumsum(counts1w2(i,:))/sum(counts1w2(i,:));
    cdf2w2(i,:)=cumsum(counts2w2(i,:))/sum(counts2w2(i,:));
    cdf3w2(i,:)=cumsum(counts3w2(i,:))/sum(counts3w2(i,:));
    cdf4w2(i,:)=cumsum(counts4w2(i,:))/sum(counts4w2(i,:));
end

ccdf1w2=1-cdf1w2;
ccdf2w2=1-cdf2w2;
ccdf3w2=1-cdf3w2;
ccdf4w2=1-cdf4w2;

ccdf1w2_avg=mean(ccdf1w2);
ccdf2w2_avg=mean(ccdf2w2);
ccdf3w2_avg=mean(ccdf3w2);
ccdf4w2_avg=mean(ccdf4w2);

figure;
semilogy(edge1w2,ccdf1w2_avg,'r'); 
hold on;
semilogy(edge2w2,ccdf2w2_avg,'g');
semilogy(edge3w2,ccdf3w2_avg,'b'); 
semilogy(edge4w2,ccdf4w2_avg,'m');
title('CCDF of Waiting Time');
xlabel('Waiting time (power of two choices)');
ylabel('P(N>n) (semilog)');
legend('lamda = 7 ','lamda = 8','lamda = 9','lamda = 9.5','Location','southeast');
hold off;