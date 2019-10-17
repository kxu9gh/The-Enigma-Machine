# Enigma
    This is a project from UC Berkeley CS61BL summer 2019.
Enigma is used by Germany during World War II to encrypt its military communications. It performs a permutation–a one-to-one mapping–of the alphabet onto itself. The one difference from other cipher is that it encryptions are different for each subsequent letter of the message, making decryption significantly more difficult.

The machine consists of a set of rotors and one reflector that make electrical contact with each other. On each side of the rotor, there is an electrical contact which connects to a corresponding contact on the opposite side, creating a one-to-one mapping between the sides. 

Since each rotor and each reflector have different mappings and can be rotated, the overall permutation of a contact signal depends on their configuration: which rotors and reflector are selected, what order they are placed in the machine, and which rotational position they are initially set to.

The overall permutation changes with each successive letter because some of the rotors rotate before encrypting a letter. Each rotor has a circular ratchet on its right half and an “alphabet ring” on its left half that fits over the ratchet of the rotor to its left...

For more detailed information, visit the course website https://cs61bl.org/su19/projects/enigma/#background.
