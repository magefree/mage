
package mage.cards.m;

import java.util.UUID;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class MindSludge extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.SWAMP));
    private static final Hint hint = new ValueHint("Swamps you control", xValue);

    private static final FilterLandPermanent filter = new FilterLandPermanent("Swamp you control");

    static {
        filter.add(SubType.SWAMP.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public MindSludge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}");


        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DiscardTargetEffect(new PermanentsOnBattlefieldCount(filter)));
        this.getSpellAbility().addHint(hint);
    }

    private MindSludge(final MindSludge card) {
        super(card);
    }

    @Override
    public MindSludge copy() {
        return new MindSludge(this);
    }
}
