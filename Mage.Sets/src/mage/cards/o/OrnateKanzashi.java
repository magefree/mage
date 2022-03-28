

package mage.cards.o;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
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
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class OrnateKanzashi extends CardImpl {


    public OrnateKanzashi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // {2}, {T}: Target opponent exiles the top card of their library. You may play that card this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new OrnateKanzashiEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private OrnateKanzashi(final OrnateKanzashi card) {
        super(card);
    }

    @Override
    public OrnateKanzashi copy() {
        return new OrnateKanzashi(this);
    }

}

class OrnateKanzashiEffect extends OneShotEffect {

    public OrnateKanzashiEffect() {
        super(Outcome.Detriment);
        this.staticText = "Target opponent exiles the top card of their library. You may play that card this turn";
    }

    public OrnateKanzashiEffect(final OrnateKanzashiEffect effect) {
        super(effect);
    }

    @Override
    public OrnateKanzashiEffect copy() {
        return new OrnateKanzashiEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null && opponent != null) {
            if (opponent.getLibrary().hasCards()) {
                Library library = opponent.getLibrary();
                Card card = library.getFromTop(game);
                if (card != null) {
                    opponent.moveCardToExileWithInfo(card, source.getSourceId(), sourceObject.getName(), source, game, Zone.LIBRARY, true);
                    ContinuousEffect effect = new OrnateKanzashiCastFromExileEffect();
                    effect.setTargetPointer(new FixedTarget(card.getId()));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}

class OrnateKanzashiCastFromExileEffect extends AsThoughEffectImpl {

    public OrnateKanzashiCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may play that card from exile this turn";
    }

    public OrnateKanzashiCastFromExileEffect(final OrnateKanzashiCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public OrnateKanzashiCastFromExileEffect copy() {
        return new OrnateKanzashiCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {        
        return source.isControlledBy(affectedControllerId)
                && objectId.equals(getTargetPointer().getFirst(game, source));
    }
}
