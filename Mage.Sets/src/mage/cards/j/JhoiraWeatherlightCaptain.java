package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterHistoricSpell;

import java.util.UUID;

public class JhoiraWeatherlightCaptain extends CardImpl {

    public JhoiraWeatherlightCaptain(UUID ownerId, CardSetInfo cardSetInfo){
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");
        addSuperType(SuperType.LEGENDARY);
        subtype.add(SubType.HUMAN, SubType.ARTIFICER);
        power = new MageInt(3);
        toughness = new MageInt(3);

        Ability ability = new SpellCastControllerTriggeredAbility(new DrawCardSourceControllerEffect(1), new FilterHistoricSpell(), false);
        addAbility(ability);

    }

    public JhoiraWeatherlightCaptain(final JhoiraWeatherlightCaptain jhoiraWeatherlightCaptain){
        super(jhoiraWeatherlightCaptain);
    }

    public JhoiraWeatherlightCaptain copy(){
        return new JhoiraWeatherlightCaptain(this);
    }
}
