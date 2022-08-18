# Algo-project

Backend : 
path1

Spring boot api

start application and hit following url to access api generates path : http://localhost:8090/path/swagger-ui/index.html#/path-rest-controller/generatePathsUsingPOST

groups : http://localhost:8090/path/swagger-ui/index.html#/path-rest-controller/groupRidersUsingPOST

Json format

input : { "numOfCabs": 4, "numPerCar": 5, "source": {"name":"snell","x":0,"y":0}, "vertices": [ {"name":"a","x":1,"y":1},{"name":"b","x":3,"y":5},{"name":"c","x":2,"y":4},{"name":"d","x":-1,"y":-5},{"name":"e","x":0,"y":7},{"name":"f","x":-3,"y":-5},{"name":"g","x":2,"y":8},{"name":"h","x":-2,"y":-6},{"name":"i","x":2,"y":9},{"name":"j","x":2,"y":0},{"name":"k","x":0,"y":2},{"name":"l","x":-4,"y":-2},{"name":"m","x":4,"y":7},{"name":"n","x":-6,"y":-2},{"name":"o","x":4,"y":8},{"name":"p","x":3,"y":0},{"name":"q","x":0,"y":3},{"name":"r","x":-5,"y":-4},{"name":"s","x":2,"y":2},{"name":"t","x":-4,"y":-4},{"name":"u","x":2,"y":7},{"name":"v","x":7,"y":0},{"name":"x","x":0,"y":4},{"name":"y","x":-7,"y":-1},{"name":"z","x":1,"y":3} ] }

output : [ [ { "name": "snell", "x": 0, "y": 0 }, { "name": "a", "x": 1, "y": 1 }, { "name": "s", "x": 2, "y": 2 }, { "name": "z", "x": 1, "y": 3 }, { "name": "q", "x": 0, "y": 3 }, { "name": "k", "x": 0, "y": 2 }, { "name": "snell", "x": 0, "y": 0 } ], [ { "name": "snell", "x": 0, "y": 0 }, { "name": "p", "x": 3, "y": 0 }, { "name": "c", "x": 2, "y": 4 }, { "name": "b", "x": 3, "y": 5 }, { "name": "o", "x": 4, "y": 8 }, { "name": "x", "x": 0, "y": 4 }, { "name": "snell", "x": 0, "y": 0 } ], [ { "name": "snell", "x": 0, "y": 0 }, { "name": "e", "x": 0, "y": 7 }, { "name": "u", "x": 2, "y": 7 }, { "name": "g", "x": 2, "y": 8 }, { "name": "i", "x": 2, "y": 9 }, { "name": "m", "x": 4, "y": 7 }, { "name": "snell", "x": 0, "y": 0 } ], [ { "name": "snell", "x": 0, "y": 0 }, { "name": "y", "x": -7, "y": -1 }, { "name": "n", "x": -6, "y": -2 }, { "name": "l", "x": -4, "y": -2 }, { "name": "v", "x": 7, "y": 0 }, { "name": "j", "x": 2, "y": 0 }, { "name": "snell", "x": 0, "y": 0 } ] ]

output for grouping:

{ "v1": [ { "name": "z", "x": 1, "y": 3 }, { "name": "k", "x": 0, "y": 2 }, { "name": "q", "x": 0, "y": 3 }, { "name": "s", "x": 2, "y": 2 }, { "name": "a", "x": 1, "y": 1 } ], "v2": [ { "name": "o", "x": 4, "y": 8 }, { "name": "b", "x": 3, "y": 5 }, { "name": "c", "x": 2, "y": 4 }, { "name": "x", "x": 0, "y": 4 }, { "name": "p", "x": 3, "y": 0 } ], "v3": [ { "name": "g", "x": 2, "y": 8 }, { "name": "i", "x": 2, "y": 9 }, { "name": "u", "x": 2, "y": 7 }, { "name": "m", "x": 4, "y": 7 }, { "name": "e", "x": 0, "y": 7 } ], "v4": [ { "name": "j", "x": 2, "y": 0 }, { "name": "v", "x": 7, "y": 0 }, { "name": "l", "x": -4, "y": -2 }, { "name": "y", "x": -7, "y": -1 }, { "name": "n", "x": -6, "y": -2 } ] }


Frontend :

This is a Next.js project bootstrapped with create-next-app.

Getting Started

Note: css classes are tailwindcss.

Grid is currently entirely composed of bidirectional edges, with weights of 150. We can likely randomize this to some extent to get more of a street-map looking view, or use a cytoscape layout for a more graph-looking view.

Please note that the current design makes it look like there is only a single edge between nodes, when in fact there are two, but the default curve made this graph pretty confusing, so I kept them straight for now until we modify conditions.

For teammates: Send me your email/github username and I will sned you an access invite to that email.

For people new to frontend. Install Node(if you haven't already), to get access to NPM, a package manager.

Personally I prever Yarn. You can get it after you download node/npm by running npm install --global yarn

Download the packages in this repo by running either yarn or npm install.

Then, run the development server:

npm run dev
# or
yarn dev
Open http://localhost:3000 with your browser to see the result.

You can start editing the page by modifying pages/index.tsx. The page auto-updates as you edit the file.

API routes can be accessed on http://localhost:3000/api/hello. This endpoint can be edited in pages/api/hello.ts.

The pages/api directory is mapped to /api/*. Files in this directory are treated as API routes instead of React pages.

Learn More

To learn more about Next.js, take a look at the following resources:

Next.js Documentation - learn about Next.js features and API.
Learn Next.js - an interactive Next.js tutorial.
You can check out the Next.js GitHub repository - your feedback and contributions are welcome!

Deploy on Vercel

The easiest way to deploy your Next.js app is to use the Vercel Platform from the creators of Next.js.

Check out our Next.js deployment documentation for more details.