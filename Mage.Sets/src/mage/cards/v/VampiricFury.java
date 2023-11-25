package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author nantuko
 */
public final class VampiricFury extends CardImpl {

    private static final FilterCreaturePermanent vampires = new FilterCreaturePermanent("Vampire creatures");

    static {
        vampires.add(SubType.VAMPIRE.getPredicate());
        vampires.add(TargetController.YOU.getControllerPredicate());
    }

    public VampiricFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");


        // Vampire creatures you control get +2/+0 and gain first strike until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 0, Duration.EndOfTurn, vampires)
                .setText("Vampire creatures you control get +2/+0"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, vampires)
                .setText("and gain first strike until end of turn"));
    }

    private VampiricFury(final VampiricFury card) {
        super(card);
    }

    @Override
    public VampiricFury copy() {
        return new VampiricFury(this);
    }
}
