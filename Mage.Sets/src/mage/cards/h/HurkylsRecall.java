
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class HurkylsRecall extends CardImpl {

    public HurkylsRecall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return all artifacts target player owns to their hand.
        this.getSpellAbility().addEffect(new HurkylsRecallReturnToHandEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public HurkylsRecall(final HurkylsRecall card) {
        super(card);
    }

    @Override
    public HurkylsRecall copy() {
        return new HurkylsRecall(this);
    }
}

class HurkylsRecallReturnToHandEffect extends OneShotEffect {

    public HurkylsRecallReturnToHandEffect() {
        super(Outcome.ReturnToHand);
        staticText = "Return all artifacts target player owns to their hand";
    }

    public HurkylsRecallReturnToHandEffect(final HurkylsRecallReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (targetPointer.getFirst(game, source) != null) {
            FilterArtifactPermanent filter = new FilterArtifactPermanent();
            filter.add(new OwnerIdPredicate(targetPointer.getFirst(game, source)));
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                permanent.moveToZone(Zone.HAND, source.getSourceId(), game, true);
            }
            return true;
        }
        return false;
    }

    @Override
    public HurkylsRecallReturnToHandEffect copy() {
        return new HurkylsRecallReturnToHandEffect(this);
    }
}
