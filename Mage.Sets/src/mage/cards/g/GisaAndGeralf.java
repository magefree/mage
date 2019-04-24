
package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class GisaAndGeralf extends CardImpl {

    public GisaAndGeralf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Gisa and Geralf enters the battlefield, put the top four cards of your library into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PutTopCardOfLibraryIntoGraveControllerEffect(4)));

        // During each of your turns, you may cast a Zombie creature card from your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GisaAndGeralfContinuousEffect()), new GisaAndGeralfWatcher());
    }

    public GisaAndGeralf(final GisaAndGeralf card) {
        super(card);
    }

    @Override
    public GisaAndGeralf copy() {
        return new GisaAndGeralf(this);
    }
}

class GisaAndGeralfContinuousEffect extends ContinuousEffectImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("Zombie creature card");

    static {
        filter.add(new SubtypePredicate(SubType.ZOMBIE));
    }

    GisaAndGeralfContinuousEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "During each of your turns, you may cast a Zombie creature card from your graveyard";
    }

    GisaAndGeralfContinuousEffect(final GisaAndGeralfContinuousEffect effect) {
        super(effect);
    }

    @Override
    public GisaAndGeralfContinuousEffect copy() {
        return new GisaAndGeralfContinuousEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (!game.isActivePlayer(player.getId())) {
                return false;
            }
            for (Card card : player.getGraveyard().getCards(filter, game)) {
                ContinuousEffect effect = new GisaAndGeralfCastFromGraveyardEffect();
                effect.setTargetPointer(new FixedTarget(card.getId()));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

class GisaAndGeralfCastFromGraveyardEffect extends AsThoughEffectImpl {

    GisaAndGeralfCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may cast a Zombie creature card from your graveyard";
    }

    GisaAndGeralfCastFromGraveyardEffect(final GisaAndGeralfCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GisaAndGeralfCastFromGraveyardEffect copy() {
        return new GisaAndGeralfCastFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (objectId.equals(getTargetPointer().getFirst(game, source))) {
            if (affectedControllerId.equals(source.getControllerId())) {
                GisaAndGeralfWatcher watcher = game.getState().getWatcher(GisaAndGeralfWatcher.class, source.getSourceId());
                return watcher != null && !watcher.isAbilityUsed();
            }
        }
        return false;
    }
}

class GisaAndGeralfWatcher extends Watcher {

   private boolean abilityUsed = false;

    GisaAndGeralfWatcher() {
        super(WatcherScope.CARD);
    }

    GisaAndGeralfWatcher(final GisaAndGeralfWatcher watcher) {
        super(watcher);
        this.abilityUsed = watcher.abilityUsed;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getZone() == Zone.GRAVEYARD) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell != null && spell.isCreature() && spell.hasSubtype(SubType.ZOMBIE, game)) {
                abilityUsed = true;
            }
        }
    }

    @Override
    public GisaAndGeralfWatcher copy() {
        return new GisaAndGeralfWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        abilityUsed = false;
    }

    public boolean isAbilityUsed() {
        return abilityUsed;
    }
}
