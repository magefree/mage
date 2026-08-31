package mage.abilities.effects.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author muz
 */
public class AssimilateTargetEffect extends OneShotEffect {

     public AssimilateTargetEffect() {
        super(Outcome.PutCreatureInPlay);
    }

    protected AssimilateTargetEffect(final AssimilateTargetEffect effect) {
        super(effect);
    }

    @Override
    public AssimilateTargetEffect copy() {
        return new AssimilateTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Counters counters = new Counters();
        counters.addCounter(new Counter(CounterType.P1P1.getName(), 1));

        Set<Card> cardsToMove = new HashSet<>();
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Card card = game.getCard(targetId);
            if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
                cardsToMove.add(card);
                game.setEnterWithCounters(targetId, counters.copy());

                // Apply the continuous effect before moving the card to have the proper types on entering.
                ContinuousEffect continuousEffect = new AssimilateContinuousEffect(new MageObjectReference(card, game, 1));
                game.addEffect(continuousEffect, source);
            }
        }
        controller.moveCards(cardsToMove, Zone.BATTLEFIELD, source, game, false, false, false, null);

        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("assimilate ");
        String targetName = mode.getTargets().get(0).getTargetName();
        sb.append(targetName);
        sb.append(". <i>(Put it onto the battlefield under your control with a +1/+1 counter. "
            + "It's a Borg artifact creature and loses all other creature types.)</i>");
        return sb.toString();
    }
}


class AssimilateContinuousEffect extends ContinuousEffectImpl {

    private final MageObjectReference mor;

    AssimilateContinuousEffect(MageObjectReference mor) {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        this.staticText = "It's a Borg artifact creature and loses all other creature types.";
        this.mor = mor;
        dependencyTypes.add(DependencyType.ArtifactAddingRemoving);
    }

    private AssimilateContinuousEffect(final AssimilateContinuousEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public AssimilateContinuousEffect copy() {
        return new AssimilateContinuousEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        affectedObjectList.add(mor);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = affectedObjectList.get(0).getPermanent(game);
        if (permanent == null) {
            this.discard();
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.setIsAllCreatureTypes(game, false);
                permanent.retainAllArtifactSubTypes(game);
                permanent.removeAllCardTypes(game);
                permanent.addCardType(game, CardType.ARTIFACT);
                permanent.addCardType(game, CardType.CREATURE);
                permanent.addSubType(game, SubType.BORG);
                break;
        }
        return true;
    }


    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4;
    }
}
