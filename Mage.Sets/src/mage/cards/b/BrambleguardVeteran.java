package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ExpendTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrambleguardVeteran extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.RACCOON, "");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.RACCOON, "");

    public BrambleguardVeteran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.subtype.add(SubType.RACCOON);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever you expend 4, Raccoons you control get +1/+1 and gain vigilance until end of turn.
        Ability ability = new ExpendTriggeredAbility(
                new BoostControlledEffect(
                        1, 1, Duration.EndOfTurn, filter
                ).setText("Raccoons you control get +1/+1"),
                ExpendTriggeredAbility.Expend.FOUR
        );
        ability.addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn, filter2
        ).setText("and gain vigilance until end of turn"));
        this.addAbility(ability);
    }

    private BrambleguardVeteran(final BrambleguardVeteran card) {
        super(card);
    }

    @Override
    public BrambleguardVeteran copy() {
        return new BrambleguardVeteran(this);
    }
}
