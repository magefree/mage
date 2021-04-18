
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class ThadaAdelAcquisitor extends CardImpl {

    public ThadaAdelAcquisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());

        // Whenever Thada Adel, Acquisitor deals combat damage to a player, search that player's library for an artifact card and exile it. Then that player shuffles their library. Until end of turn, you may play that card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ThadaAdelAcquisitorEffect(), false, true));
    }

    private ThadaAdelAcquisitor(final ThadaAdelAcquisitor card) {
        super(card);
    }

    @Override
    public ThadaAdelAcquisitor copy() {
        return new ThadaAdelAcquisitor(this);
    }
}

class ThadaAdelAcquisitorEffect extends OneShotEffect {

    ThadaAdelAcquisitorEffect() {
        super(Outcome.Exile);
        staticText = "search that player's library for an artifact card and exile it. Then that player shuffles. Until end of turn, you may play that card";
    }

    ThadaAdelAcquisitorEffect(final ThadaAdelAcquisitorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player damagedPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller == null || damagedPlayer == null || sourceObject == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(new FilterArtifactCard());
        if (controller.searchLibrary(target, source, game, damagedPlayer.getId())) {
            if (!target.getTargets().isEmpty()) {
                Card card = damagedPlayer.getLibrary().remove(target.getFirstTarget(), game);
                if (card != null) {
                    controller.moveCardToExileWithInfo(card, source.getSourceId(), sourceObject.getIdName(), source, game, Zone.LIBRARY, true);
                    ContinuousEffect effect = new ThadaAdelPlayFromExileEffect();
                    effect.setTargetPointer(new FixedTarget(card.getId()));
                    game.addEffect(effect, source);
                }
            }
        }
        damagedPlayer.shuffleLibrary(source, game);
        return true;
    }

    @Override
    public ThadaAdelAcquisitorEffect copy() {
        return new ThadaAdelAcquisitorEffect(this);
    }
}

class ThadaAdelPlayFromExileEffect extends AsThoughEffectImpl {

    public ThadaAdelPlayFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may play this card from exile";
    }

    public ThadaAdelPlayFromExileEffect(final ThadaAdelPlayFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ThadaAdelPlayFromExileEffect copy() {
        return new ThadaAdelPlayFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId)
                && sourceId.equals(getTargetPointer().getFirst(game, source));
    }
}
