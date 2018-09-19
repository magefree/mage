
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class EssenceFlux extends CardImpl {

    public EssenceFlux(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Exile target creature you control, then return that card to the battlefield under its owner's control. If it's a Spirit, put a +1/+1 counter on it.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        Effect effect = new ExileTargetForSourceEffect();
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new EssenceFluxEffect());
    }

    public EssenceFlux(final EssenceFlux card) {
        super(card);
    }

    @Override
    public EssenceFlux copy() {
        return new EssenceFlux(this);
    }
}

class EssenceFluxEffect extends OneShotEffect {

    EssenceFluxEffect() {
        super(Outcome.Benefit);
        staticText = "return that card to the battlefield under its owner's control.  If it's a Spirit, put a +1/+1 counter on it";
    }

    EssenceFluxEffect(final EssenceFluxEffect effect) {
        super(effect);
    }

    @Override
    public EssenceFluxEffect copy() {
        return new EssenceFluxEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cardsToBattlefield = new CardsImpl();
            UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
            if (exileZoneId != null) {
                ExileZone exileZone = game.getExile().getExileZone(exileZoneId);
                if (exileZone != null) {
                    for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
                        if (exileZone.contains(targetId)) {
                            cardsToBattlefield.add(targetId);
                        } else {
                            Card card = game.getCard(targetId);
                            if (card instanceof MeldCard) {
                                MeldCard meldCard = (MeldCard) card;
                                Card topCard = meldCard.getTopHalfCard();
                                Card bottomCard = meldCard.getBottomHalfCard();
                                if (topCard.getZoneChangeCounter(game) == meldCard.getTopLastZoneChangeCounter() && exileZone.contains(topCard.getId())) {
                                    cardsToBattlefield.add(topCard);
                                }
                                if (bottomCard.getZoneChangeCounter(game) == meldCard.getBottomLastZoneChangeCounter() && exileZone.contains(bottomCard.getId())) {
                                    cardsToBattlefield.add(bottomCard);
                                }
                            }
                        }
                    }
                }
            }
            if (!cardsToBattlefield.isEmpty()) {
                controller.moveCards(cardsToBattlefield.getCards(game), Zone.BATTLEFIELD, source, game, false, false, true, null);
                for (UUID cardId : cardsToBattlefield) {
                    Permanent permanent = game.getPermanent(cardId);
                    if (permanent != null && permanent.hasSubtype(SubType.SPIRIT, game)) {
                        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
                        effect.setTargetPointer(new FixedTarget(permanent, game));
                        return effect.apply(game, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
