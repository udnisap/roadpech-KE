/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roadpech_neuralnetwork;

/**
 *
 * @author Rashmika
 */
public class PreProcessor {
    
    /**
     * Convert time intervals to bit sequence
     * @param timeZone
     * @return 
     */
    public static String toCharSequence(String timeZone){        
        String sequence = "";
        switch(timeZone){
            case "06:00:00#06:10:00" : sequence = "000000"; break;
            case "06:10:00#06:20:00" : sequence = "000001"; break;
            case "06:20:00#06:30:00" : sequence = "000010"; break;
            case "06:30:00#06:40:00" : sequence = "000011"; break;
            case "06:40:00#06:50:00" : sequence = "000100"; break;
            case "06:50:00#07:00:00" : sequence = "000101"; break;

            case "07:00:00#07:10:00" : sequence = "000110"; break;
            case "07:10:00#07:20:00" : sequence = "000111"; break;
            case "07:20:00#07:30:00" : sequence = "001000"; break;
            case "07:30:00#07:40:00" : sequence = "001001"; break;
            case "07:40:00#07:50:00" : sequence = "001010"; break;
            case "07:50:00#08:00:00" : sequence = "001011"; break;

            case "08:00:00#08:10:00" : sequence = "001100"; break;
            case "08:10:00#08:20:00" : sequence = "001101"; break;
            case "08:20:00#08:30:00" : sequence = "001110"; break;
            case "08:30:00#08:40:00" : sequence = "001111"; break;
            case "08:40:00#08:50:00" : sequence = "010000"; break;
            case "08:50:00#09:00:00" : sequence = "010001"; break;

            case "09:00:00#09:10:00" : sequence = "010010"; break;
            case "09:10:00#09:20:00" : sequence = "010011"; break;
            case "09:20:00#09:30:00" : sequence = "010100"; break;
            case "09:30:00#09:40:00" : sequence = "010101"; break;
            case "09:40:00#09:50:00" : sequence = "010110"; break;
            case "09:50:00#10:00:00" : sequence = "010111"; break;

            case "10:00:00#10:10:00" : sequence = "011000"; break;
            case "10:10:00#10:20:00" : sequence = "011001"; break;
            case "10:20:00#10:30:00" : sequence = "011010"; break;
            case "10:30:00#10:40:00" : sequence = "011011"; break;
            case "10:40:00#10:50:00" : sequence = "011100"; break;
            case "10:50:00#11:00:00" : sequence = "011101"; break;

            case "11:00:00#11:10:00" : sequence = "011110"; break;
            case "11:10:00#11:20:00" : sequence = "011111"; break;
            case "11:20:00#11:30:00" : sequence = "100000"; break;
            case "11:30:00#11:40:00" : sequence = "100001"; break;
            case "11:40:00#11:50:00" : sequence = "100010"; break;
            case "11:50:00#12:00:00" : sequence = "100011"; break;

            case "12:00:00#12:10:00" : sequence = "100100"; break;
            case "12:10:00#12:20:00" : sequence = "100101"; break;
            case "12:20:00#12:30:00" : sequence = "100110"; break;
            case "12:30:00#12:40:00" : sequence = "100111"; break;
            case "12:40:00#12:50:00" : sequence = "101000"; break;
            case "12:50:00#13:00:00" : sequence = "101001"; break;

            case "13:00:00#13:10:00" : sequence = "101010"; break;
            case "13:10:00#13:20:00" : sequence = "101011"; break;
            case "13:20:00#13:30:00" : sequence = "101100"; break;
            case "13:30:00#13:40:00" : sequence = "101101"; break;
            case "13:40:00#13:50:00" : sequence = "101110"; break;
            case "13:50:00#14:00:00" : sequence = "101111"; break;

            case "14:00:00#14:10:00" : sequence = "110000"; break;
            case "14:10:00#14:20:00" : sequence = "110001"; break;
            case "14:20:00#14:30:00" : sequence = "110010"; break;
            case "14:30:00#14:40:00" : sequence = "110011"; break;
            case "14:40:00#14:50:00" : sequence = "110100"; break;
            case "14:50:00#15:00:00" : sequence = "110101"; break;

            case "15:00:00#15:10:00" : sequence = "110110"; break;
            case "15:10:00#15:20:00" : sequence = "110111"; break;
            case "15:20:00#15:30:00" : sequence = "111000"; break;
            case "15:30:00#15:40:00" : sequence = "111001"; break;
            case "15:40:00#15:50:00" : sequence = "111010"; break;
            case "15:50:00#16:00:00" : sequence = "111011"; break;
                
            default: sequence = "101011";
        }        
        return sequence;
    }

}