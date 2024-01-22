The application caters to the below two types of users:
  
1. Admin - The user can set and view the list of shows and seat allocations
2. Buyer - The user can retrieve a list of available seats for a show, book one or more seats, and cancel tickets
  
When the admin user is selected, the following commands are available:
1. Setup <Show Number> <Number of Rows> <Number of seats per row> <Cancellation window in minutes>
   - This command sets the number of seats for the show (example: A1, A2... Z8, Z9)
   - Max row is 26
   - Max seats per row is 10
2. View <Show Number>
   - This command displays the ticket #, phone # and seat numbers allocated to each buyer
   
When the buyer user is selected, the following commands are available:
1. Availability <Show Number>
   - This command lists all available seat numbers for a show.
2. Book <Show Number> <Phone#> <Comma separated list of seats>
   - This command books a ticket that generates a single unique ticket #.
   - Only one booking # per phone # is allowed per show
   - Only found seats will be booked if some seats are not found.
   - User can only book existing shows and seats
3. Cancel <Ticket#> <Phone#>
   - This command cancels a ticket as long as it is within the cancellation window
  
Another command "quit" is available to all users which allows the user to return to the main menu.
Any commands not mentioned here and those not following the right format will display the appropriate error to the user.
