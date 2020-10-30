package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.AnyColorManaAbility;


/**
 *
 * @author antoni-g
 */
public final class CentaurNurturer extends CardImpl {

    public CentaurNurturer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Centaur Nurturer enters the battlefield, you gain 3 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3)));
        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private CentaurNurturer(final CentaurNurturer card) {
        super(card);
    }

    @Override
    public CentaurNurturer copy() {
        return new CentaurNurturer(this);
    }
}
