package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AkulTheUnrepentant extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("other creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AkulTheUnrepentant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SCORPION);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Sacrifice three other creatures: You may put a creature card from your hand onto the battlefield. Activate only as a sorcery and only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD,
                new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_CREATURE_A),
                new SacrificeTargetCost(3, filter)
        ).setTiming(TimingRule.SORCERY));
    }

    private AkulTheUnrepentant(final AkulTheUnrepentant card) {
        super(card);
    }

    @Override
    public AkulTheUnrepentant copy() {
        return new AkulTheUnrepentant(this);
    }
}
