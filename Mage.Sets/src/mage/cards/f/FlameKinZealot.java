package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author Loki
 */
public final class FlameKinZealot extends CardImpl {

    public FlameKinZealot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}{W}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BERSERKER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Flame-Kin Zealot enters the battlefield, creatures you control get +1/+1 and gain haste until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostControlledEffect(1, 1, Duration.EndOfTurn)
                .setText("creatures you control get +1/+1"));
        ability.addEffect(new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES)
                .setText("and gain haste until end of turn"));
        this.addAbility(ability);
    }

    private FlameKinZealot(final FlameKinZealot card) {
        super(card);
    }

    @Override
    public FlameKinZealot copy() {
        return new FlameKinZealot(this);
    }
}
