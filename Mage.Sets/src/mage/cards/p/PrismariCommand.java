package mage.cards.p;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawDiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TreasureToken;
import mage.target.TargetPlayer;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrismariCommand extends CardImpl {

    public PrismariCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{R}");

        // Choose two —
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // • Prismari Command deals 2 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // • Target player draws two cards, then discards two cards.
        Mode mode = new Mode(new DrawDiscardTargetEffect(2, 2));
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode);

        // • Target player creates a Treasure token.
        mode = new Mode(new CreateTokenTargetEffect(new TreasureToken()));
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode);

        // • Destroy target artifact.
        mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().addMode(mode);
    }

    private PrismariCommand(final PrismariCommand card) {
        super(card);
    }

    @Override
    public PrismariCommand copy() {
        return new PrismariCommand(this);
    }
}
