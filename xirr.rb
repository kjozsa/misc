#!/usr/bin/ruby
# XIRR calculation algorithm implemented
# based on Excel 2010 help description and formula

require 'date'

values = [-10000.0, 2750.0, 4250.0, 3250.0, 2750.0]
dates = [Date.new(2008,1,1), Date.new(2008,3,1), Date.new(2008,10,30), Date.new(2009,2,15), Date.new(2009,4,1)]

n = values.size

# starter values
sum = 1
rate = 0
delta = 1

until (sum.abs < 0.000001) do
  delta = delta * 0.9
  rate += delta if sum > 0
  rate -= delta if sum < 0

  sum = 0
  for i in 0..(n-1) do
    d = (dates[i] - dates[0]) / 365.0
    denom = (1.0 + rate) ** d
    sum += (values[i] / denom)
  end
  
  puts "sum: #{sum}, rate: #{rate}, delta: #{delta}"
end

puts "finished, rate: #{rate}"

