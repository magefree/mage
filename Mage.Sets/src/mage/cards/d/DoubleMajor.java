package mage.cards.d;

import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreatureSpell;
import mage.target.TargetSpell;
import mage.util.functions.RemoveTypeCopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DoubleMajor extends CardImpl {

    private static final FilterSpell filter = new FilterCreatureSpell("creature spell you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public DoubleMajor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{U}");

        // Copy target creature spell you control, except it isn't legendary if the spell is legendary.
        this.getSpellAbility().addEffect(
                new CopyTargetSpellEffect(false, false, false, 1, new RemoveTypeCopyApplier(SuperType.LEGENDARY))
                        .setText(
                                "Copy target creature spell you control, except it isn't legendary if the spell is legendary."));
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private DoubleMajor(final DoubleMajor card) {
        super(card);
    }

    @Override
    public DoubleMajor copy() {
        return new DoubleMajor(this);
    }
}