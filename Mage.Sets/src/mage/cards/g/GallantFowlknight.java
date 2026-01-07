package mage.cards.g;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author muz
 */
public final class GallantFowlknight extends CardImpl {

    public GallantFowlknight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When this creature enters, creatures you control get +1/+0 until end of turn. Kithkin creatures you control also gain first strike until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostControlledEffect(
            1, 0, Duration.EndOfTurn
        ).setText("creatures you control get +1/+0 until end of turn."));
        ability.addEffect(new GainAbilityControlledEffect(
            FirstStrikeAbility.getInstance(), Duration.EndOfTurn,
            new FilterCreaturePermanent(SubType.KITHKIN, "Kithkin creatures")
        ).setText("Kithkin creatures you control also gain first strike until end of turn"));
        this.addAbility(ability);
    }

    private GallantFowlknight(final GallantFowlknight card) {
        super(card);
    }

    @Override
    public GallantFowlknight copy() {
        return new GallantFowlknight(this);
    }
}
