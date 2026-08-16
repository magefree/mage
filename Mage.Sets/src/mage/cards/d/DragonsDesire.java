package mage.cards.d;

import java.util.UUID;

import mage.Mana;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.mana.DynamicManaEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;

/**
 *
 * @author muz
 */
public final class DragonsDesire extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("artifact your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getOwnerPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Artifacts your opponents control", xValue);

    public DragonsDesire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");

        // Add {R} for each artifact your opponents control.
        this.getSpellAbility().addEffect(new DynamicManaEffect(Mana.RedMana(1), xValue));
        this.getSpellAbility().addHint(hint);
    }

    private DragonsDesire(final DragonsDesire card) {
        super(card);
    }

    @Override
    public DragonsDesire copy() {
        return new DragonsDesire(this);
    }
}
