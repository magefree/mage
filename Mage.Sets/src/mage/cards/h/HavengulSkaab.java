package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author noxx
 */
public final class HavengulSkaab extends CardImpl {

    public HavengulSkaab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Whenever Havengul Skaab attacks, return another creature you control to its owner's hand.
        this.addAbility(new AttacksTriggeredAbility(new ReturnToHandChosenControlledPermanentEffect(StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL)));
    }

    private HavengulSkaab(final HavengulSkaab card) {
        super(card);
    }

    @Override
    public HavengulSkaab copy() {
        return new HavengulSkaab(this);
    }
}
