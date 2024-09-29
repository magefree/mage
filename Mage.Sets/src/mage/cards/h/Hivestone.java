package mage.cards.h;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesSubtypeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;

import java.util.Arrays;
import java.util.UUID;

/**
 * Created by Alexsandr0x.
 */
public final class Hivestone extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public Hivestone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Creatures you control are Slivers in addition to their other creature types.
        Effect effect = new BecomesSubtypeAllEffect(Duration.WhileOnBattlefield, Arrays.asList(SubType.SLIVER), filter, false);
        effect.setText("Creatures you control are Slivers in addition to their other creature types");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private Hivestone(final Hivestone card) {
        super(card);
    }

    @Override
    public Hivestone copy() {
        return new Hivestone(this);
    }

}
