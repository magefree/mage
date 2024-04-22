package mage.cards.b;

import mage.abilities.effects.common.CreateTokenControllerTargetPermanentEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.BeastToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author North, Loki
 */
public final class BeastWithin extends CardImpl {

    public BeastWithin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Destroy target permanent. Its controller creates a 3/3 green Beast creature token.
        this.getSpellAbility().addTarget(new TargetPermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenControllerTargetPermanentEffect(new BeastToken()));
    }

    private BeastWithin(final BeastWithin card) {
        super(card);
    }

    @Override
    public BeastWithin copy() {
        return new BeastWithin(this);
    }
}