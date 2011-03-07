#!/usr/bin/ruby
# XIRR calculation algorithm implemented
# based on Excel 2010 help description and formula

require 'date'

# example values 1
values = [-10000.0, 2750.0, 4250.0, 3250.0, 2750.0]
dates = [Date.new(2008,1,1), Date.new(2008,3,1), Date.new(2008,10,30), Date.new(2009,2,15), Date.new(2009,4,1)]

# example values 2
#values = [1895, -500, -52, -24, -334, -234, -22, -44, -200, -110, -105, -100, -112, -100, -100, -100, -100 ,-100, -115]
#dates = [  Date.new(2010, 1, 1),  Date.new(2010, 2, 1),  Date.new(2010, 3, 1),  Date.new(2010, 4, 1),  Date.new(2010, 5, 1),  Date.new(2010, 6, 1),  Date.new(2011, 7, 1),  Date.new(2011, 8, 1),  Date.new(2011, 9, 1),  Date.new(2011, 10, 1),  Date.new(2011, 11, 1),  Date.new(2011, 12, 1),  Date.new(2012, 1, 1),  Date.new(2012, 2, 1),  Date.new(2012, 3, 1),  Date.new(2012, 4, 1),  Date.new(2012, 5, 1),  Date.new(2012, 6, 1),  Date.new(2012, 7, 1)]

# starter values
n = values.size
sum = 1
prevsum = 1
rate = 0
delta = 1
direction = 1  # rate increase or decrease

until (sum.abs < 0.000001) do
  delta = delta * 0.9

  direction *= -1 if sum.abs > prevsum.abs
  rate += delta * direction

  prevsum = sum
  sum = 0

  for i in 0..(n-1) do
    d = (dates[i] - dates[0]) / 365.0
    denom = (1.0 + rate) ** d
    sum += (values[i] / denom)
  end
  
  puts "sum: #{sum}, rate: #{rate}, delta: #{delta}"
end

puts "finished, rate: #{rate}"

