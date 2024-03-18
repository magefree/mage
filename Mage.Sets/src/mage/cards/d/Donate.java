package mage.cards.d;

import mage.abilities.effects.common.TargetPlayerGainControlTargetPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 *
 * @author Quercitron
 */
public final class Donate extends CardImpl {

    public Donate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");

        // Target player gains control of target permanent you control.
        this.getSpellAbility().addEffect(new TargetPlayerGainControlTargetPermanentEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new TargetControlledPermanent());
    }

    private Donate(final Donate card) {
        super(card);
    }

    @Override
    public Donate copy() {
        return new Donate(this);
    }
}
