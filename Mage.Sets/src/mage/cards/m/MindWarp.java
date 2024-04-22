package mage.cards.m;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.discard.LookTargetHandChooseDiscardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class MindWarp extends CardImpl {

    public MindWarp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{3}{B}");

        // Look at target player's hand and choose X cards from it. That player discards those cards.
        this.getSpellAbility().addEffect(new LookTargetHandChooseDiscardEffect(false, ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private MindWarp(final MindWarp card) {
        super(card);
    }

    @Override
    public MindWarp copy() {
        return new MindWarp(this);
    }
}
