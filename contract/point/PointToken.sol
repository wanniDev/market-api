// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.0;

import "./ERC20.sol";
import "./modifier/Ownable.sol";
import "./lib/SafeMath.sol";

contract PointToken is ERC20 {

    string name;
    string symbol;
    address admin;
    uint8 decimals;

    using SafeMath for uint256;

    mapping(address => uint) accountToBalance;
    mapping(address => mapping(address => uint)) allowed;

    constructor(string _name, string _symbol){
        name = _name;
        symbol = _symbol;
        admin = msg.sender;
        decimals = 18;
    }

    function charge(address account, uint256 amount) public returns (bool) {
        address owner = msg.sender;

        require(owner == admin, "ERC20Permit: invalid signature");
        require(account != address(0), "ERC20: deposit account the zero address");

        _mint(account, amount);
        return true;
    }
}
