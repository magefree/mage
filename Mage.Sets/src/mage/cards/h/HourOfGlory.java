
package mage.cards.h;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class HourOfGlory extends CardImpl {

    public HourOfGlory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Exile target creature. If that creature was a God, its controller reveals their hand and exiles all cards with the same name as that creature.
        this.getSpellAbility().addEffect(new HourOfGloryEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private HourOfGlory(final HourOfGlory card) {
        super(card);
    }

    @Override
    public HourOfGlory copy() {
        return new HourOfGlory(this);
    }
}

class HourOfGloryEffect extends OneShotEffect {

    public HourOfGloryEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile target creature. If that creature was a God, its controller reveals their hand and exiles all cards with the same name as that creature";
    }

    private HourOfGloryEffect(final HourOfGloryEffect effect) {
        super(effect);
    }

    @Override
    public HourOfGloryEffect copy() {
        return new HourOfGloryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetCreature != null) {
                controller.moveCards(targetCreature, Zone.EXILED, source, game);
                if (targetCreature.hasSubtype(SubType.GOD, game)) {
                    game.getState().processAction(game);
                    Player targetController = game.getPlayer(targetCreature.getControllerId());
                    if (targetController != null) {
                        targetController.revealCards(sourceObject.getIdName(), targetController.getHand(), game);
                        Set<Card> toExile = new HashSet<>();
                        for (Card card : targetController.getHand().getCards(game)) {
                            if (card.getName().equals(targetCreature.getName())) {
                                toExile.add(card);
                            }
                        }
                        targetController.moveCards(toExile, Zone.EXILED, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
