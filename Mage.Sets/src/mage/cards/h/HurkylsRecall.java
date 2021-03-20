
package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HurkylsRecall extends CardImpl {

    public HurkylsRecall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return all artifacts target player owns to their hand.
        this.getSpellAbility().addEffect(new HurkylsRecallReturnToHandEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private HurkylsRecall(final HurkylsRecall card) {
        super(card);
    }

    @Override
    public HurkylsRecall copy() {
        return new HurkylsRecall(this);
    }
}

class HurkylsRecallReturnToHandEffect extends OneShotEffect {

    HurkylsRecallReturnToHandEffect() {
        super(Outcome.ReturnToHand);
        staticText = "Return all artifacts target player owns to their hand";
    }

    private HurkylsRecallReturnToHandEffect(final HurkylsRecallReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (targetPointer.getFirst(game, source) != null) {
            FilterPermanent filter = new FilterArtifactPermanent();
            filter.add(new OwnerIdPredicate(targetPointer.getFirst(game, source)));
            return new ReturnToHandFromBattlefieldAllEffect(filter).apply(game, source);
        }
        return false;
    }

    @Override
    public HurkylsRecallReturnToHandEffect copy() {
        return new HurkylsRecallReturnToHandEffect(this);
    }
}
