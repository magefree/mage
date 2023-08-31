package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.RatCantBlockToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LordSkittersButcher extends CardImpl {

    public LordSkittersButcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Lord Skitter's Butcher enters the battlefield, choose one --
        // * Create a 1/1 black Rat creature token with "This creature can't block."
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new RatCantBlockToken()));

        // * You may sacrifice another creature. If you do, scry 2, then draw a card.
        ability.addMode(new Mode(new DoIfCostPaid(
                new ScryEffect(2, false),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)
        ).addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"))));

        // * Creatures you control gain menace until end of turn.
        ability.addMode(new Mode(new GainAbilityControlledEffect(
                new MenaceAbility(false), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        )));
        this.addAbility(ability);
    }

    private LordSkittersButcher(final LordSkittersButcher card) {
        super(card);
    }

    @Override
    public LordSkittersButcher copy() {
        return new LordSkittersButcher(this);
    }
}
