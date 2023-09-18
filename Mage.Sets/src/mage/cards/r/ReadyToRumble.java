package mage.cards.r;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReadyToRumble extends CardImpl {

    public ReadyToRumble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Choose one —
        // • Ready to Rumble deals 5 damage to target creature or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());

        // • Destroy target artifact.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetArtifactPermanent()));
    }

    private ReadyToRumble(final ReadyToRumble card) {
        super(card);
    }

    @Override
    public ReadyToRumble copy() {
        return new ReadyToRumble(this);
    }
}
