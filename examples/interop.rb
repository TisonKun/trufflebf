puts 'Input 2 nonnegative numbers'

x = gets.chomp.to_i
y = gets.chomp.to_i

add = Polyglot.eval('bf', '>[-<+>]<')
res = add.call([x, y], 0)
puts res['data'][res['dp']]

