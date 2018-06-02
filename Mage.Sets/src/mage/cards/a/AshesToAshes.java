
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class AshesToAshes extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonartifact creature");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.ARTIFACT)));
    }

    public AshesToAshes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}{B}");


        // Exile two target nonartifact creatures. Ashes to Ashes deals 5 damage to you.
        this.getSpellAbility().addEffect(new AshesToAshesEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(2, filter));
        this.getSpellAbility().addEffect(new DamageControllerEffect(5));
    }

    public AshesToAshes(final AshesToAshes card) {
        super(card);
    }

    @Override
    public AshesToAshes copy() {
        return new AshesToAshes(this);
    }
}

class AshesToAshesEffect extends OneShotEffect {

    public AshesToAshesEffect() {
        super(Outcome.Benefit);
        staticText = "Exile two target nonartifact creatures";
    }

    public AshesToAshesEffect(final AshesToAshesEffect effect) {
        super(effect);
    }

    @Override
    public AshesToAshesEffect copy() {
        return new AshesToAshesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID exileId = source.getSourceId();
        for (UUID permanentId : targetPointer.getTargets(game, source)) {
            Permanent target = game.getPermanent(permanentId);
            if (target != null) {
                target.moveToExile(exileId, "Ashes to Ashes", source.getSourceId(), game);
            }
        }
        return true;
    }
}
