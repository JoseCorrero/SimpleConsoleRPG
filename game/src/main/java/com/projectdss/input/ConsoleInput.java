package com.projectdss.input;

import com.projectdss.CharacterStats;
import com.projectdss.Item;
import com.projectdss.inventory.Inventory;
import com.projectdss.inventory.InventoryManager;
import com.projectdss.item.CombatItem;
import com.projectdss.item.ConsumableItem;
import com.projectdss.item.combatitem.EquipableCombatItem;
import com.projectdss.item.combatitem.RuneCombatItem;
import com.projectdss.item.combatitem.equipablecombatitem.ArmorEquipableCombatItem;
import com.projectdss.item.combatitem.equipablecombatitem.WeaponEquipableCombatItem;
import com.projectdss.item.combatitem.runecombatitem.ArmorRuneCombatItem;
import com.projectdss.item.combatitem.runecombatitem.WeaponRuneCombatItem;
import java.io.IOException;
import java.util.Scanner;

/**
 * ConsoleInput.java
 * 
 * @author Santiago Godoy Poce
 */
public class ConsoleInput implements InputHandler {
    @Override
    public int getInput(int firstOption, int lastOption) {
        Scanner input = new Scanner(System.in);
        int inputValue = input.nextInt();
        try {
            if(inputValue < firstOption || inputValue > lastOption) 
                throw new IOException();
            else
                return inputValue;
        } catch(IOException ioe) {
            System.out.println("Incorrect option");
            return 0;
        } finally {
            input.close();
        }
    }

    @Override
    public void getItemInput(EquipableCombatItem item, Inventory inventory, CharacterStats characterStats, int option) {
        switch(option) {
            case 1: InventoryManager manager = InventoryManager.getInstance(inventory);
                    if(item.isEquipped())
                        if(item instanceof WeaponEquipableCombatItem)
                            manager.discardWeapon(characterStats);
                        else if(item instanceof ArmorEquipableCombatItem)
                            manager.discardArmor(characterStats);
                    else
                        if(item instanceof WeaponEquipableCombatItem)
                            manager.equipWeapon((WeaponEquipableCombatItem) item, characterStats);
                        else if(item instanceof ArmorEquipableCombatItem)
                            manager.equipArmor((ArmorEquipableCombatItem) item, characterStats);
                break;
            case 2: inventory.removeItem(item);
                break;
            case 0:
                break;
            default: 
        }
    }

    @Override
    public void getItemInput(ConsumableItem item, Inventory inventory, CharacterStats characterStats, int option) {
        switch(option) {
            case 1: item.use(characterStats);
                    inventory.removeItem(item);
                break;
            case 2: inventory.removeItem(item);
                break;
            case 0:
                break;
            default: 
        }
    }

    @Override
    public void getItemInput(RuneCombatItem item, Inventory inventory, CharacterStats characterStats, int option) {
        switch(option){
            case 1: InventoryManager manager = InventoryManager.getInstance(inventory);
                    if(item instanceof WeaponRuneCombatItem)
                        if(manager.mergeWeaponRune(inventory.getEquippedWeapon(),
                                                  (WeaponRuneCombatItem) item, 
                                                  characterStats))
                            inventory.removeItem(item);
                            
                    else if(item instanceof ArmorRuneCombatItem)
                        if(manager.mergeArmorRune(inventory.getEquippedArmor(),
                                                  (ArmorRuneCombatItem) item, 
                                                  characterStats))
                            inventory.removeItem(item);

                break;
            case 2: inventory.removeItem(item);
                break;
            case 0:
                break;
            default: 
        }
    }
}