package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.Elemental11BlueRedToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfMercadia extends TransformingDoubleFacedCard {

    public InvasionOfMercadia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{1}{R}",
                "Kyren Flamewright",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GOBLIN, SubType.SPELLSHAPER}, "R"
        );

        // Invasion of Mercadia
        this.getLeftHalfCard().setStartingDefense(4);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Mercadia enters the battlefield, you may discard a card. If you do, draw two cards.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(new DrawCardSourceControllerEffect(2), new DiscardCardCost())));

        // Kyren Flamewright
        this.getRightHalfCard().setPT(3, 3);

        // {2}{R}, {T}, Discard a card: Create two 1/1 blue and red Elemental creature tokens. Creatures you control get +1/+0 and gain haste until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new Elemental11BlueRedToken(), 2), new ManaCostsImpl<>("{2}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        ability.addEffect(new BoostControlledEffect(1, 0, Duration.EndOfTurn)
                .setText("creatures you control get +1/+0"));
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("and gain haste until end of turn"));
        this.getRightHalfCard().addAbility(ability);
    }

    private InvasionOfMercadia(final InvasionOfMercadia card) {
        super(card);
    }

    @Override
    public InvasionOfMercadia copy() {
        return new InvasionOfMercadia(this);
    }
}
