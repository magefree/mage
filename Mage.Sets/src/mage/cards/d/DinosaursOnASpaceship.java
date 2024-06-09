package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CounterRemovedFromSourceWhileExiledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.DinosaurFlyingHasteToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DinosaursOnASpaceship extends CardImpl {

    // The +1/+1 effect wants a FilterCreaturePermanent
    private static final FilterCreaturePermanent filterCreature =
            new FilterCreaturePermanent(SubType.DINOSAUR, "Other Dinosaurs you control");
    private static final FilterControlledPermanent filter =
            new FilterControlledPermanent(SubType.DINOSAUR, "Other Dinosaurs you control");

    public DinosaursOnASpaceship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{W}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Other Dinosaurs you control get +1/+1 and have vigilance and trample.
        Effect effect = new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterCreature, true);
        Ability ability = new SimpleStaticAbility(effect.setText("Other Dinosaurs you control get +1/+1"));
        effect = new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filter, true);
        ability.addEffect(effect.setText("and have vigilance"));
        effect = new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter, true);
        ability.addEffect(effect.setText("and trample"));
        this.addAbility(ability);

        // Suspend 4--{3}{R}{W}
        this.addAbility(new SuspendAbility(4, new ManaCostsImpl<>("{3}{R}{W}"), this));

        // Whenever a time counter is removed from Dinosaurs on a Spaceship while it's exiled, create a 2/2 red and white Dinosaur creature token with flying and haste.
        this.addAbility(new CounterRemovedFromSourceWhileExiledTriggeredAbility(
                CounterType.TIME,
                new CreateTokenEffect(new DinosaurFlyingHasteToken())
        ));
    }

    private DinosaursOnASpaceship(final DinosaursOnASpaceship card) {
        super(card);
    }

    @Override
    public DinosaursOnASpaceship copy() {
        return new DinosaursOnASpaceship(this);
    }
}
