package mage.game.command.planes;

import mage.abilities.Ability;
import mage.abilities.common.ChaosEnsuesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.Plane;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.Iterator;
import java.util.UUID;

/**
 * @author spjspj
 */
public class TrailOfTheMageRingsPlane extends Plane {

    public TrailOfTheMageRingsPlane() {
        this.setPlaneType(Planes.PLANE_TRAIL_OF_THE_MAGE_RINGS);

        // Instant and sorcery spells have rebound
        this.addAbility(new SimpleStaticAbility(Zone.COMMAND, new TrailOfTheMageRingsReboundEffect()));

        // Active player can roll the planar die: Whenever you roll {CHAOS}, you may search your library for an instant or sorcery card, reveal it, put it into your hand, then shuffle your library
        this.addAbility(new ChaosEnsuesTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY), true
        ), true));
    }

    private TrailOfTheMageRingsPlane(final TrailOfTheMageRingsPlane plane) {
        super(plane);
    }

    @Override
    public TrailOfTheMageRingsPlane copy() {
        return new TrailOfTheMageRingsPlane(this);
    }
}

class TrailOfTheMageRingsReboundEffect extends ContinuousEffectImpl {

    public TrailOfTheMageRingsReboundEffect() {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "instant and sorcery spells have rebound";
    }

    protected TrailOfTheMageRingsReboundEffect(final TrailOfTheMageRingsReboundEffect effect) {
        super(effect);
    }

    @Override
    public TrailOfTheMageRingsReboundEffect copy() {
        return new TrailOfTheMageRingsReboundEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getPlayers().keySet()) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            for (Card card : player.getHand().getCards(StaticFilters.FILTER_CARD_INSTANT_AND_SORCERY, game)) {
                addReboundAbility(card, source, game);
            }
        }
        for (Iterator<StackObject> iterator = game.getStack().iterator(); iterator.hasNext(); ) {
            StackObject stackObject = iterator.next();
            if (!(stackObject instanceof Spell) || !stackObject.isControlledBy(source.getControllerId())) {
                continue;
            }
            Spell spell = (Spell) stackObject;
            Card card = spell.getCard();
            if (card != null) {
                addReboundAbility(card, source, game);
            }
        }
        return true;
    }

    private void addReboundAbility(Card card, Ability source, Game game) {
        if (card.isInstantOrSorcery(game) && !card.getAbilities(game).containsClass(ReboundAbility.class)) {
            game.getState().addOtherAbility(card, new ReboundAbility());
        }
    }
}
