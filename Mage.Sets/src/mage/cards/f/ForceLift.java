package mage.cards.f;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class ForceLift extends CardImpl {
    public ForceLift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W/U}");

        //Exile target creature. Return that creature to the battlefield under its owner's control at the beginning of the next end step.
        this.getSpellAbility().addEffect(new ForceLiftEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        //Scry 1.
        this.getSpellAbility().addEffect(new ScryEffect(1));
    }

    public ForceLift(final ForceLift card) {
        super(card);
    }

    @Override
    public ForceLift copy() {
        return new ForceLift(this);
    }
}

class ForceLiftEffect extends OneShotEffect {

    private static final String effectText = "Exile target creature. Return that creature to the battlefield under its owner's control at the beginning of the next end step.";

    ForceLiftEffect() {
        super(Outcome.Benefit);
        staticText = effectText;
    }

    ForceLiftEffect(ForceLiftEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            if (permanent.moveToExile(source.getSourceId(), "Force Lift", source, game)) {
                ExileZone exile = game.getExile().getExileZone(source.getSourceId());
                // only if permanent is in exile (tokens would be stop to exist)
                if (exile != null && !exile.isEmpty()) {
                    Card card = game.getCard(permanent.getId());
                    if (card != null) {
                        //create delayed triggered ability
                        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                                new ForceLiftReturnFromExileEffect(new MageObjectReference(card, game))), source);
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public ForceLiftEffect copy() {
        return new ForceLiftEffect(this);
    }

}

class ForceLiftReturnFromExileEffect extends OneShotEffect {

    MageObjectReference objectToReturn;

    public ForceLiftReturnFromExileEffect(MageObjectReference objectToReturn) {
        super(Outcome.PutCardInPlay);
        this.objectToReturn = objectToReturn;
        staticText = "return that card to the battlefield under its owner's control";
    }

    public ForceLiftReturnFromExileEffect(final ForceLiftReturnFromExileEffect effect) {
        super(effect);
        this.objectToReturn = effect.objectToReturn;
    }

    @Override
    public ForceLiftReturnFromExileEffect copy() {
        return new ForceLiftReturnFromExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(objectToReturn.getSourceId());
        if (card != null && objectToReturn.refersTo(card, game)) {
            Player owner = game.getPlayer(card.getOwnerId());
            if (owner != null) {
                owner.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, true, null);
            }
        }
        return true;
    }
}