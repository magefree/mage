package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.TreasureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SurlyBadgersaur extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard("a noncreature, nonland card");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public SurlyBadgersaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.BADGER);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you discard a creature card, put a +1/+1 counter on Surly Badgersaur.
        this.addAbility(new DiscardCardControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                false, StaticFilters.FILTER_CARD_CREATURE_A
        ));

        // Whenever you discard a land card, create a treasure token.
        this.addAbility(new DiscardCardControllerTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()),
                false, StaticFilters.FILTER_CARD_LAND_A
        ));

        // Whenever you discard a noncreature, nonland card, Surly Badgersaur fights up to one target creature you don't control.
        Ability ability = new DiscardCardControllerTriggeredAbility(
                new FightTargetSourceEffect().setText(
                        "{this} fights up to one target creature you don't control"
                ), false, filter
        );
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL, false));
        this.addAbility(ability);
    }

    private SurlyBadgersaur(final SurlyBadgersaur card) {
        super(card);
    }

    @Override
    public SurlyBadgersaur copy() {
        return new SurlyBadgersaur(this);
    }
}
