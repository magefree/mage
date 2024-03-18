package mage.cards.h;

import mage.abilities.effects.common.TargetPlayerGainControlTargetPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class HarmlessOffering extends CardImpl {

    public HarmlessOffering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");

        // Target opponent gains control of target permanent you control.
        this.getSpellAbility().addEffect(new TargetPlayerGainControlTargetPermanentEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addTarget(new TargetControlledPermanent());
    }

    private HarmlessOffering(final HarmlessOffering card) {
        super(card);
    }

    @Override
    public HarmlessOffering copy() {
        return new HarmlessOffering(this);
    }
}
