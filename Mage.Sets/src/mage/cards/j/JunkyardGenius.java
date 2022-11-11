package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.PowerstoneToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JunkyardGenius extends CardImpl {

    public JunkyardGenius(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Junkyard Genius enters the battlefield, create a tapped Powerstone token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new PowerstoneToken(), 1, true)
        ));

        // {1}{B}{R}, Sacrifice another creature or artifact: Until end of turn, other creatures you control get +1/+0 and gain menace and haste.
        Ability ability = new SimpleActivatedAbility(new BoostControlledEffect(
                1, 1, Duration.EndOfTurn, true
        ).setText("until end of turn, other creatures you control get +1/+0"), new ManaCostsImpl<>("{1}{B}{R}"));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT_OR_CREATURE));
        ability.addEffect(new GainAbilityControlledEffect(
                new MenaceAbility(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES, true
        ).setText("and gain menace"));
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES, true
        ).setText("and haste"));
        this.addAbility(ability);
    }

    private JunkyardGenius(final JunkyardGenius card) {
        super(card);
    }

    @Override
    public JunkyardGenius copy() {
        return new JunkyardGenius(this);
    }
}
