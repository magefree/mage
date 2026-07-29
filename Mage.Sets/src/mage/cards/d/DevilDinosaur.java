package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class DevilDinosaur extends CardImpl {

    // The +1/+1 effect wants a FilterCreaturePermanent
    private static final FilterCreaturePermanent filterCreature =
            new FilterCreaturePermanent(SubType.DINOSAUR, "Other Dinosaurs you control");
    private static final FilterControlledPermanent filter =
            new FilterControlledPermanent(SubType.DINOSAUR, "Other Dinosaurs you control");

    public DevilDinosaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Other Dinosaurs you control get +1/+1 and have hexproof.
        Ability ability = new SimpleStaticAbility(
            new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterCreature, true)
                .setText("Other Dinosaurs you control get +1/+1"));
        ability.addEffect(
                new GainAbilityControlledEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield, filter, true)
                        .setText("and have hexproof"));
        this.addAbility(ability);
    }

    private DevilDinosaur(final DevilDinosaur card) {
        super(card);
    }

    @Override
    public DevilDinosaur copy() {
        return new DevilDinosaur(this);
    }
}
