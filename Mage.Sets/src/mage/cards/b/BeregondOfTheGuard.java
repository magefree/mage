package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BeregondOfTheGuard extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.HUMAN, "Human");

    public BeregondOfTheGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Beregond of the Guard or another Human enters the battlefield under your control, creatures you control get +1/+1 and gain vigilance until end of turn.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new BoostControlledEffect(1, 1, Duration.EndOfTurn)
                        .setText("creatures you control get +1/+1"),
                filter, false, true
        );
        ability.addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and gain vigilance until end of turn"));
        this.addAbility(ability);
    }

    private BeregondOfTheGuard(final BeregondOfTheGuard card) {
        super(card);
    }

    @Override
    public BeregondOfTheGuard copy() {
        return new BeregondOfTheGuard(this);
    }
}
