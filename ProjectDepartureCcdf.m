filename='excel.xls';
for i=1:10    
        lamda1d(i,:)=xlsread(filename,i,'D1:D10000');
        lamda2d(i,:)=xlsread(filename,i,'H1:H10000');
        lamda3d(i,:)=xlsread(filename,i,'L1:L10000');
        lamda4d(i,:)=xlsread(filename,i,'P1:P10000');   
end

for i=1:10
    [counts1d(i,:),edge1d]=hist(lamda1d(i,:));
    [counts2d(i,:),edge2d]=hist(lamda2d(i,:));
    [counts3d(i,:),edge3d]=hist(lamda3d(i,:));
    [counts4d(i,:),edge4d]=hist(lamda4d(i,:));
end

for i=1:10
    cdf1d(i,:)=cumsum(counts1d(i,:))/sum(counts1d(i,:));
    cdf2d(i,:)=cumsum(counts2d(i,:))/sum(counts2d(i,:));
    cdf3d(i,:)=cumsum(counts3d(i,:))/sum(counts3d(i,:));
    cdf4d(i,:)=cumsum(counts4d(i,:))/sum(counts4d(i,:));
end

ccdf1d=1-cdf1d;
ccdf2d=1-cdf2d;
ccdf3d=1-cdf3d;
ccdf4d=1-cdf4d;

ccdf1d_avg=mean(ccdf1d);
ccdf2d_avg=mean(ccdf2d);
ccdf3d_avg=mean(ccdf3d);
ccdf4d_avg=mean(ccdf4d);

figure;
semilogy(edge1d,ccdf1d_avg,'r'); 
hold on;
semilogy(edge2d,ccdf2d_avg,'g');
semilogy(edge3d,ccdf3d_avg,'b'); 
semilogy(edge4d,ccdf4d_avg,'m');
title('CCDF of Di)');
xlabel('Customers N in Queue at the time of Departure');
ylabel('P(N>n) (Semilog)');
legend('lamda = 0.7 ','lamda = 0.8','lamda = 0.9','lamda = 0.95','Location','southeast');
hold off;