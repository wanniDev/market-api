module.exports = {
  contracts_directory: "./point",
  networks: {
    ganache: {
      host: "127.0.0.1",
      port: 8545,
      network_id: "*",
      gas: 100000000
    }
  },
  compilers: {
    solc: {
      version: "0.8.0", // Fetch exact version from solc-bin (default: truffle's version)
      settings: {          // See the solidity docs for advice about optimization and evmVersion
        optimizer: {
          enabled: true,
          runs: 200
        }
      }
    }
  }
};
