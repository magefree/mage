
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.constants.ZoneDetail;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public final class Spelljack extends CardImpl {

    public Spelljack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}{U}{U}");

        // Counter target spell. If that spell is countered this way, exile it instead of putting it into its owner's graveyard. You may play it without paying its mana cost for as long as it remains exiled.
        this.getSpellAbility().addEffect(new SpelljackEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public Spelljack(final Spelljack card) {
        super(card);
    }

    @Override
    public Spelljack copy() {
        return new Spelljack(this);
    }
}

class SpelljackEffect extends OneShotEffect {

    SpelljackEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Counter target spell. If that spell is countered this way, exile it instead of putting it into its owner's graveyard. You may play it without paying its mana cost for as long as it remains exiled";
    }

    SpelljackEffect(final SpelljackEffect effect) {
        super(effect);
    }

    @Override
    public SpelljackEffect copy() {
        return new SpelljackEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID targetId = targetPointer.getFirst(game, source);
            StackObject stackObject = game.getStack().getStackObject(targetId);
            if (stackObject != null && game.getStack().counter(targetId, source.getSourceId(), game, Zone.EXILED, false, ZoneDetail.NONE)) {
                Card card = ((Spell) stackObject).getCard();
                if (card != null) {
                    ContinuousEffect effect = new SpelljackCastFromExileEffect();
                    effect.setTargetPointer(new FixedTarget(card.getId(), game.getState().getZoneChangeCounter(card.getId())));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}

class SpelljackCastFromExileEffect extends AsThoughEffectImpl {

    SpelljackCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "You may cast that card without paying its mana cost as long as it remains exiled";
    }

    SpelljackCastFromExileEffect(final SpelljackCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SpelljackCastFromExileEffect copy() {
        return new SpelljackCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            if (getTargetPointer().getFirst(game, source) == null) {
                this.discard();
                return false;
            }
            if (sourceId.equals(getTargetPointer().getFirst(game, source))) {
                Card card = game.getCard(sourceId);
                if (card != null) {
                    if (game.getState().getZone(sourceId) == Zone.EXILED) {
                        Player player = game.getPlayer(affectedControllerId);
                        player.setCastSourceIdWithAlternateMana(sourceId, null, null);
                        return true;
                    } else {
                        this.discard();
                    }
                }
            }
        }
        return false;
    }
}
