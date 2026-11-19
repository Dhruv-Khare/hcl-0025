import {greet} from '../app.js';
import {test} from "node:test";

import assert from "node:assert";

test("greet returns the correct freeting", ()=>{
    const expected = "Hello Dhruv";
    const actual = greet("Dhruv");
    assert.strictEqual(actual, expected);
})