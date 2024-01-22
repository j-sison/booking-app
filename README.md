The application caters to the below 2 types of users:
  
1. Admin - user is able to setup and view the list of shows and seat allocations
2. Buyer - user is able to retrieve list of available seats for a show, book 1 or more seats and cancel tickets
  
When admin user is selected, the following command are available:
1. Setup <Show Number> <Number of Rows> <Number of seats per row> <Cancellation window in minutes>
   - This command setup the number of seats for the show (example: A1, A2... Z8, Z9)
   - Max row is 26
   - Max seats per row is 10
2. View <Show Number>
   - This command displays the ticket #, phone # and seat numbers allocated to each buyer
   
When buyer user is selected, the following command are available:
1. Availability <Show Number>
   - This command lists all available seat numbers for a show.
2. Book <Show Number> <Phone#> <Comma separated list of seats>
   - This command books a ticket which generates a single unique ticket #.
   - Only one booking # per phone # is allowed per show
   - If some of the seats are not found, only found seats will be booked.
3. Cancel <Ticket#> <Phone#>
   - This command cancels a ticket as long as it is within the cancellation window
  
Another command "quit" is available to all users which allows the user to return to the main menu.
Any commands not mentioned here and those not following the right format will display the appropriate error to the user.
