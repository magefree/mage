package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.command.emblems.RalCracklingWitEmblem;
import mage.game.permanent.token.OtterProwessToken;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class RalCracklingWit extends CardImpl {

    public RalCracklingWit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RAL);
        this.setStartingLoyalty(4);

        // Whenever you cast a noncreature spell, put a loyalty counter on Ral, Crackling Wit.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.LOYALTY.createInstance()), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));

        // +1: Create a 1/1 blue and red Otter creature token with prowess.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new OtterProwessToken()), 1));

        // −3: Draw three cards, then discard two cards.
        this.addAbility(new LoyaltyAbility(new DrawDiscardControllerEffect(3, 2), -3));

        // −10: Draw three cards. You get an emblem with "Instant and sorcery spells you cast have storm."
        // (Whenever you cast an instant or sorcery spell, copy it for each spell cast before it this turn.)
        Ability ability = new LoyaltyAbility(new DrawCardSourceControllerEffect(3), -10);
        ability.addEffect(new GetEmblemEffect(new RalCracklingWitEmblem()));
        this.addAbility(ability);
    }

    private RalCracklingWit(final RalCracklingWit card) {
        super(card);
    }

    @Override
    public RalCracklingWit copy() {
        return new RalCracklingWit(this);
    }
}
