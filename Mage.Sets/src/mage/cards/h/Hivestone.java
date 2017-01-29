package mage.cards.h;

import java.util.ArrayList;
import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesSubtypeAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 * Created by Alexsandr0x.
 */
public class Hivestone extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public Hivestone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Creatures you control are Slivers in addition to their other creature types.
        ArrayList<String> subTypes = new ArrayList<>();
        subTypes.add("Sliver");
        Effect effect = new BecomesSubtypeAllEffect(Duration.WhileOnBattlefield, subTypes, filter, false);
        effect.setText("Creatures you control are Slivers in addition to their other creature types");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public Hivestone(final Hivestone card) {
        super(card);
    }

    @Override
    public Card copy() {
        return new Hivestone(this);
    }

}
