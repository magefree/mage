/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

/**
 *
 * @author jeffwadsworth
 */
public class TheMyriadPools extends CardImpl {

    public TheMyriadPools(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.LAND}, null);
        this.supertype.add(SuperType.LEGENDARY);

        // this is the second face of The Everflowing Well
        this.nightCard = true;

        // {T}: Add {U}.
        Ability ability = new BlueManaAbility();
        // Whenever you cast a permanent spell using mana produced by The Myriad Pools, up to one other target permanent you control becomes a copy of that spell until end of turn.
        this.addAbility(ability, new TheMyriadPoolsWatcher(ability.getOriginalId().toString()));
        this.addAbility(new TheMyriadPoolsTriggeredAbility());

    }

    private TheMyriadPools(final TheMyriadPools card) {
        super(card);
    }

    @Override
    public TheMyriadPools copy() {
        return new TheMyriadPools(this);
    }
}

class TheMyriadPoolsWatcher extends Watcher {

    private UUID permanentId = UUID.randomUUID();
    private final String originalId;

    public TheMyriadPoolsWatcher(String originalId) {
        super(WatcherScope.CARD);
        this.originalId = originalId;
    }

    public boolean manaUsedToCastPermanentPart(UUID id) {
        return permanentId.equals(id);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.MANA_PAID) {
            if (event.getData() != null
                    && event.getData().equals(originalId)) {
                Spell spell = game.getStack().getSpell(event.getTargetId());
                if (spell != null
                        && spell.isPermanent(game)) {
                    Card card = spell.getCard();
                    permanentId = card.getId();
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
    }
}

class TheMyriadPoolsTriggeredAbility extends TriggeredAbilityImpl {

    public TheMyriadPoolsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TheMyriadPoolsCopyEffect());
    }

    private TheMyriadPoolsTriggeredAbility(final TheMyriadPoolsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheMyriadPoolsTriggeredAbility copy() {
        return new TheMyriadPoolsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        TheMyriadPoolsWatcher watcher = game.getState().getWatcher(TheMyriadPoolsWatcher.class, this.getSourceId());
        if (watcher != null
                && watcher.manaUsedToCastPermanentPart(event.getSourceId())) {
            Spell spell = game.getSpell(event.getSourceId());
            if (spell != null
                    && spell.isControlledBy(getControllerId())) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getSourceId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a permanent spell using mana produced by {this}, up to one other target permanent you control becomes a copy of that spell until end of turn.";
    }

}

class TheMyriadPoolsCopyEffect extends OneShotEffect {

    TheMyriadPoolsCopyEffect() {
        super(Outcome.Neutral);
        this.staticText = "copy of card on stack";
    }

    private TheMyriadPoolsCopyEffect(final TheMyriadPoolsCopyEffect effect) {
        super(effect);
    }

    @Override
    public TheMyriadPoolsCopyEffect copy() {
        return new TheMyriadPoolsCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetPermanentToCopyTo = null;
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_PERMANENT, false);
        if (controller.choose(Outcome.Neutral, target, source, game)) {
            targetPermanentToCopyTo = game.getPermanent(target.getFirstTarget());
        }
        Card copyFromCardOnStack = game.getCard(getTargetPointer().getFirst(game, source));
        Permanent newBluePrint = null;
        if (targetPermanentToCopyTo != null) {
            if (copyFromCardOnStack != null) {
                newBluePrint = new PermanentCard(copyFromCardOnStack, source.getControllerId(), game);
                newBluePrint.assignNewId();
                CopyEffect copyEffect = new CopyEffect(Duration.EndOfTurn, newBluePrint, targetPermanentToCopyTo.getId());
                Ability newAbility = source.copy();
                copyEffect.init(newAbility, game);
                game.addEffect(copyEffect, newAbility);
            }
            return true;
        }
        return false;
    }
}
