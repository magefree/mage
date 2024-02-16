package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author weirddan455
 */
public final class BalmorBattlemageCaptain extends CardImpl {

    public BalmorBattlemageCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast an instant or sorcery spell, creatures you control get +1/+0 and gain trample until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new BoostControlledEffect(1, 0, Duration.EndOfTurn).setText("creatures you control get +1/+0"),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY,
                false
        );
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES)
                .setText("and gain trample until end of turn")
        );
        this.addAbility(ability);
    }

    private BalmorBattlemageCaptain(final BalmorBattlemageCaptain card) {
        super(card);
    }

    @Override
    public BalmorBattlemageCaptain copy() {
        return new BalmorBattlemageCaptain(this);
    }
}
