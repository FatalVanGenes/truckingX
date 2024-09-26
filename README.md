<h2>TO-DO's</h2>
<ul>
<li>Keep track of every link: Map&lt;City, Map&lt;City, LinkType&gt;&gt; allLinks;</li>
<li>change clique linking to the nearest two (drop any duplicate)</li>
<li>final check build accessibility groupings to ensure entire network is linked.</li>
</ul>

<h2>Rules/actions</h2>
<ul>
  <li>Person gets up, goes to work, goes home.</li>
  <li>Person has a Profession.</li>
  <li>Person works for a Company.</li>
  <li>Company sells products.</li>
  <li>Company pays Employees.</li>
  <li>Company gets paid when product is delivered.</li>
  <li>Company pays to ship product.</li>
  <li>Shipping gets paid when product is delivered.</li>
</ul>

<h2>source -> sink</h2>
<ul>
  <li>Woods -> LumberCamp</li>
  <li>LumberCamp -> SawMill</li>
  <li>SawMill -> FurnitureFactory, HouseBuilders</li>
  <li>Mining</li>
  <ul>
    <li>Metals</li><li>Concrete</li><li>ToolMaking</li><li>Machinery</li>
  </ul>
  <li>Banking</li>
    <ul>
      <li>deposits</li>
      <li>business loans</li>
      <li>mortgages</li>
    </ul>
  <li>Farming -> FoodProduction</li>
  <li>FoodProduction -> FoodSales</li>
</ul>

<h2>First simulation is lumber</h2>
<ul>
  <li>Pre-creation</li>
  <ul>
    <li>form corporation</li>
  </ul>
  <li>Creation</li>
  <ul>
    <li>form LumberCamp</li>
  </ul>
  <li>Operations</li>
  <ul>
    <li>Semi delivers WheelLoader to work sites [WheelLoaderUnloadedEvent]</li>
    <li>crew arrives at LumberCamp by 0800 [TripEndEvent]</li>
    <li>crew mounts pickup and departs [TripBeginEvent]</li>
    <li>semi w/flatbed picks up [WheelLoaderLoadedEvent]</li>
    <li>semi w/flatbed carries WheelLoader [TripBeginEvent]</li>
    <li>travel to work site [TripBeginEvent]</li>
    <li>arrive at work site [TripEndEvent]</li>
    <li>chop down trees [ChoppingBeginEvent, ChoppingEndEvent]</li>
    <li>WheelLoader makes stacks of 20 trees</li>
    <li>(WheelLoader needs to be stacking trees while cutting occurs)</li>
    <li>semi w/flatbed picks up 20+ trees [FlatbedFullEvent]</li>
    <li>semi travels to next worksite [TripBeginEvent] to pick up more [TripEndEvent]</li>
    <li>1700 work crews depart work site [TripBeginEvent] and return to LumberCamp [TripEndEvent]</li>
  </ul>
  <li>Shutdown</li>
  <ul>
    <li>Dismantle buildings at work site</li>
    <li>Plant seedlings</li>
  </ul>
</ul>


