const pointToken = artifacts.require("PointToken")

module.exports = function (deployer) {
    deployer.deploy(pointToken);
};