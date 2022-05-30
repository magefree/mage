package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SteadfastUnicorn extends CardImpl {

    public SteadfastUnicorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.UNICORN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {3}{W}: Creatures you control get +1/+1 and gain vigilance until end of turn. Activate only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.EndOfTurn)
                .setText("creatures you control get +1/+1"), new ManaCostsImpl<>("{3}{W}"), MyTurnCondition.instance
        );
        ability.addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and gain vigilance until end of turn"));
        this.addAbility(ability.addHint(MyTurnHint.instance));
    }

    private SteadfastUnicorn(final SteadfastUnicorn card) {
        super(card);
    }

    @Override
    public SteadfastUnicorn copy() {
        return new SteadfastUnicorn(this);
    }
}
