import { greet,greetInHindi } from '../app.js';
import { test, describe ,mock} from "node:test";

import assert from "node:assert";

describe("Greeting Function", () => {
    test("greet returns the correct freeting", () => {
        const expected = "Hello Dhruv";
        const actual = greet("Dhruv");
        assert.strictEqual(actual, expected);
    });

    test("greet returns in hindi", ()=>{
        const expected = "Namaste, Dhruv";
        const actual = greetInHindi("Dhruv");
        assert.strictEqual(actual, expected);
    })
});

describe("Order Processing", () => {
    test("processOrder returns the correct payment info", () => {
        const mockedProcessPayment = mock.fn((ammount) => {
            console.log("I am mocked processPayment function");
            return { status: "success", id: Math.random(), amount: ammount };
        });
        const expected = { status: "success", id: Math.random(), amount: 100 };
        const actual = processOrder({ ammount: 100 }, { processPayment: mockedProcessPayment });
        assert.strictEqual(actual, expected);
    });
});
