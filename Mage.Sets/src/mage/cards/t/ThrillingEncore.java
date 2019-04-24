
package mage.cards.t;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

/**
 *
 * @author L_J
 */
public final class ThrillingEncore extends CardImpl {

    public ThrillingEncore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");
                
        // Put onto the battlefield under your control all creature cards in all graveyards that were put there from the battlefield this turn.
        this.getSpellAbility().addEffect(new ThrillingEncoreEffect());
        this.getSpellAbility().addWatcher(new CardsPutIntoGraveyardWatcher());
    }

    public ThrillingEncore(final ThrillingEncore card) {
        super(card);
    }

    @Override
    public ThrillingEncore copy() {
        return new ThrillingEncore(this);
    }
}

class ThrillingEncoreEffect extends OneShotEffect {
    
    public ThrillingEncoreEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Put onto the battlefield under your control all creature cards in all graveyards that were put there from the battlefield this turn";
    }
    
    public ThrillingEncoreEffect(final ThrillingEncoreEffect effect) {
        super(effect);
    }
    
    @Override
    public ThrillingEncoreEffect copy() {
        return new ThrillingEncoreEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        CardsPutIntoGraveyardWatcher watcher = (CardsPutIntoGraveyardWatcher) game.getState().getWatchers().get(CardsPutIntoGraveyardWatcher.class.getSimpleName());
        if (watcher != null) {
            for (MageObjectReference mor : watcher.getCardsPutToGraveyardFromBattlefield()) {
                if (game.getState().getZoneChangeCounter(mor.getSourceId()) == mor.getZoneChangeCounter()) {
                    Card card = mor.getCard(game);
                    if (card != null && card.isCreature()) {
                        Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
                        effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game)));
                        effect.apply(game, source);
                    }
                }
            }
        }
        return true;
    }
}
