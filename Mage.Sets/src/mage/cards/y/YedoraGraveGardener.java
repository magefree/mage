package mage.cards.y;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public final class YedoraGraveGardener extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("another nontoken creature you control");

    static {
        filter.add(Predicates.not(TokenPredicate.instance));
        filter.add(AnotherPredicate.instance);
    }

    public YedoraGraveGardener(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever another nontoken creature you control dies, you may return it to the battlefield face down under its owner's control. It's a Forest land.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new YedoraEffect(), true, filter, true
        ));
    }

    private YedoraGraveGardener(final YedoraGraveGardener card) {
        super(card);
    }

    @Override
    public YedoraGraveGardener copy() {
        return new YedoraGraveGardener(this);
    }
}

class YedoraEffect extends OneShotEffect {

    public YedoraEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = " return it to the battlefield face down under its owner's control. It's a Forest land";
    }

    public YedoraEffect(final YedoraEffect effect) {
        super(effect);
    }

    @Override
    public YedoraEffect copy() {
        return new YedoraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(targetPointer.getFirst(game, source));
            if (card != null) {
                YedoraReplacementEffect effect = new YedoraReplacementEffect();
                effect.setTargetPointer(new FixedTarget(card.getId()));
                game.addEffect(effect, source);
                controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, true, true, null);
            }
            return true;
        }
        return false;
    }

}

class YedoraReplacementEffect extends ReplacementEffectImpl {

    YedoraReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
    }

    YedoraReplacementEffect(YedoraReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(getTargetPointer().getFirst(game, source));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null) {
            YedoraGraveGardenerContinuousEffect effect = new YedoraGraveGardenerContinuousEffect();
            effect.setTargetPointer(new FixedTarget(creature.getId(), creature.getZoneChangeCounter(game) + 1));
            game.addEffect(effect, source);
        }
        return false;
    }

    @Override
    public YedoraReplacementEffect copy() {
        return new YedoraReplacementEffect(this);
    }
}

class YedoraGraveGardenerContinuousEffect extends ContinuousEffectImpl {

    YedoraGraveGardenerContinuousEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        this.staticText = "It is a forest";
        this.dependencyTypes.add(DependencyType.BecomeForest);
    }

    YedoraGraveGardenerContinuousEffect(final YedoraGraveGardenerContinuousEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public YedoraGraveGardenerContinuousEffect copy() {
        return new YedoraGraveGardenerContinuousEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent deadCreature;
        if (source.getTargets().getFirstTarget() == null) {
            deadCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        } else {
            deadCreature = game.getPermanent(source.getTargets().getFirstTarget());
            if (deadCreature == null) {
                deadCreature = game.getPermanentEntering(source.getTargets().getFirstTarget());
            }
        }
        switch (layer) {
            case TypeChangingEffects_4:
                // 305.7 Note that this doesn't remove any abilities that were granted to the land by other effects
                // So the ability removing has to be done before Layer 6
                // Lands have their mana ability intrinsically, so that is added in layer 4
                // If someone wants to clear up the un-needed code below, please do, but test it to verify it still works!!  Some *removeAll* code simply didn't work.
                deadCreature.getSuperType().clear();
                deadCreature.removeAllSubTypes(game);
                deadCreature.removeAllSubTypes(game, SubTypeSet.CreatureType);
                deadCreature.removeAllCardTypes();
                deadCreature.removeAllCardTypes(game);
                deadCreature.removeAllAbilities(source.getSourceId(), game);
                deadCreature.addCardType(game, CardType.LAND);
                deadCreature.addCardType(CardType.LAND);
                deadCreature.addSubType(game, SubType.FOREST);
                deadCreature.addSuperType(SuperType.BASIC);
                deadCreature.addSubType(SubType.FOREST);
                deadCreature.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                break;
            case ColorChangingEffects_5:
                deadCreature.getColor(game).setWhite(false);
                deadCreature.getColor(game).setGreen(false);
                deadCreature.getColor(game).setBlack(false);
                deadCreature.getColor(game).setBlue(false);
                deadCreature.getColor(game).setRed(false);
                break;
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4
                || layer == Layer.ColorChangingEffects_5;
    }
}
