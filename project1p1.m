filename='excel.xls';
for i=1:10    
        lamda1a(i,:)=xlsread(filename,i,'C1:C10000');
        lamda2a(i,:)=xlsread(filename,i,'G1:G10000');
        lamda3a(i,:)=xlsread(filename,i,'K1:K10000');
        lamda4a(i,:)=xlsread(filename,i,'O1:O10000');   
end

for i=1:10
    [counts1a(i,:),edge1a]=hist(lamda1a(i,:));
    [counts2a(i,:),edge2a]=hist(lamda2a(i,:));
    [counts3a(i,:),edge3a]=hist(lamda3a(i,:));
    [counts4a(i,:),edge4a]=hist(lamda4a(i,:));
end

for i=1:10
    cdf1a(i,:)=cumsum(counts1a(i,:))/sum(counts1a(i,:));
    cdf2a(i,:)=cumsum(counts2a(i,:))/sum(counts2a(i,:));
    cdf3a(i,:)=cumsum(counts3a(i,:))/sum(counts3a(i,:));
    cdf4a(i,:)=cumsum(counts4a(i,:))/sum(counts4a(i,:));
end

ccdf1a=1-cdf1a;
ccdf2a=1-cdf2a;
ccdf3a=1-cdf3a;
ccdf4a=1-cdf4a;

ccdf1a_avg=mean(ccdf1a);
ccdf2a_avg=mean(ccdf2a);
ccdf3a_avg=mean(ccdf3a);
ccdf4a_avg=mean(ccdf4a);

figure;
semilogy(edge1a,ccdf1a_avg,'r'); 
hold on;
semilogy(edge2a,ccdf2a_avg,'g');
semilogy(edge3a,ccdf3a_avg,'b'); 
semilogy(edge4a,ccdf4a_avg,'m');
title('CCDF of X(ti)');
xlabel('Customers N in Queue during arrival');
ylabel('P(N>n)');
legend('lamda = 0.7 ','lamda = 0.8','lamda = 0.9','lamda = 0.95','Location','southeast');
hold off;