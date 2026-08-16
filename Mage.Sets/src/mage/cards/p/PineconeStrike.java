package mage.cards.p;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class PineconeStrike extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("artifact token");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public PineconeStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Choose one or both --
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Pinecone Strike deals 3 damage to target creature. If that creature would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addEffect(new ExileTargetIfDiesEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // * Destroy target artifact token.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetPermanent(filter)));
    }

    private PineconeStrike(final PineconeStrike card) {
        super(card);
    }

    @Override
    public PineconeStrike copy() {
        return new PineconeStrike(this);
    }
}
