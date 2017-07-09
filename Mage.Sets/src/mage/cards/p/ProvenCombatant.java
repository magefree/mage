package mage.cards.p;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.EternalizeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

public class ProvenCombatant extends CardImpl {

    public ProvenCombatant(UUID ownerId, CardSetInfo cardSetInfo){
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{U}");


        subtype.add("Human");
        subtype.add("Warrior");

        power = new MageInt(1);
        toughness = new MageInt(1);

        //Eternalize 4UU
        addAbility(new EternalizeAbility(new ManaCostsImpl("{4}{U}{U}"), this));
    }

    public ProvenCombatant(final ProvenCombatant provenCombatant){
        super(provenCombatant);
    }

    public ProvenCombatant copy(){
        return new ProvenCombatant(this);
    }


}
