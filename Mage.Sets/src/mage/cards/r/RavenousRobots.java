package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.Robot11Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RavenousRobots extends CardImpl {

    public RavenousRobots(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast an artifact spell, create a 1/1 colorless Robot artifact creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new Robot11Token()), StaticFilters.FILTER_SPELL_AN_ARTIFACT, false
        ));

        // {R}, {T}: Creature tokens you control gain haste until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CREATURE_TOKENS
        ), new ManaCostsImpl<>("{R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private RavenousRobots(final RavenousRobots card) {
        super(card);
    }

    @Override
    public RavenousRobots copy() {
        return new RavenousRobots(this);
    }
}
