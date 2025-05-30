package mage.cards.c;

import mage.MageInt;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class CinderGiant extends CardImpl {

    public CinderGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, Cinder Giant deals 2 damage to each other creature you control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DamageAllEffect(2, StaticFilters.FILTER_OTHER_CONTROLLED_CREATURE)));
    }

    private CinderGiant(final CinderGiant card) {
        super(card);
    }

    @Override
    public CinderGiant copy() {
        return new CinderGiant(this);
    }
}
