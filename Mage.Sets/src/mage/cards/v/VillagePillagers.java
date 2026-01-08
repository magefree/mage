package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class VillagePillagers extends CardImpl {

    private static final FilterPermanent filter
            = new FilterOpponentsCreaturePermanent("a creature an opponent controls with a counter on it");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public VillagePillagers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.subtype.add(SubType.GOBLIN, SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Wither
        this.addAbility(WitherAbility.getInstance());

        // When this creature enters, it deals 1 damage to each creature your opponents control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DamageAllEffect(
                1, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE
        ).setText("it deals 1 damage to each creature your opponents control")));

        // Whenever a creature an opponent controls with a counter on it dies, you create a tapped Treasure token.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new CreateTokenEffect(new TreasureToken(), 1, true),  false, filter
        ));
    }

    private VillagePillagers(final VillagePillagers card) {
        super(card);
    }

    @Override
    public VillagePillagers copy() {
        return new VillagePillagers(this);
    }
}
