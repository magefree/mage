package mage.cards.k;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;
import mage.ApprovingObject;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class KnowledgePool extends CardImpl {

    public KnowledgePool(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // Imprint - When Knowledge Pool enters the battlefield, each player exiles the top three cards of their library
        this.addAbility(new EntersBattlefieldTriggeredAbility(new KnowledgePoolEffect1(), false).setAbilityWord(AbilityWord.IMPRINT));

        // Whenever a player casts a spell from their hand, that player exiles it. If the player does, they may cast another nonland card exiled with Knowledge Pool without paying that card's mana cost.
        this.addAbility(new KnowledgePoolAbility());
    }

    private KnowledgePool(final KnowledgePool card) {
        super(card);
    }

    @Override
    public KnowledgePool copy() {
        return new KnowledgePool(this);
    }

}

class KnowledgePoolEffect1 extends OneShotEffect {

    public KnowledgePoolEffect1() {
        super(Outcome.Neutral);
        staticText = "each player exiles the top three cards of their library";
    }

    public KnowledgePoolEffect1(final KnowledgePoolEffect1 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller == null || sourceObject == null) {
            return false;
        }
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.moveCardsToExile(player.getLibrary().getTopCards(game, 3), source, game, true,
                        CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()),
                        sourceObject.getIdName() + " (" + sourceObject.getZoneChangeCounter(game) + ')');
            }
        }
        return true;
    }

    @Override
    public KnowledgePoolEffect1 copy() {
        return new KnowledgePoolEffect1(this);
    }

}

class KnowledgePoolAbility extends TriggeredAbilityImpl {

    public KnowledgePoolAbility() {
        super(Zone.BATTLEFIELD, new KnowledgePoolEffect2(), false);
    }

    public KnowledgePoolAbility(final KnowledgePoolAbility ability) {
        super(ability);
    }

    @Override
    public KnowledgePoolAbility copy() {
        return new KnowledgePoolAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getZone() == Zone.HAND) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }

}

class KnowledgePoolEffect2 extends OneShotEffect {

    private static FilterNonlandCard filter = new FilterNonlandCard("nonland card exiled with Knowledge Pool");

    public KnowledgePoolEffect2() {
        super(Outcome.Neutral);
        staticText = "Whenever a player casts a spell from their hand, that player exiles it. If the player does, they may cast another nonland card exiled with {this} without paying that card's mana cost";
    }

    public KnowledgePoolEffect2(final KnowledgePoolEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && spell != null && sourceObject != null) {
            UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), sourceObject.getZoneChangeCounter(game));
            if (controller.moveCardsToExile(spell, source, game, true, exileZoneId, sourceObject.getIdName())) {
                Player player = game.getPlayer(spell.getControllerId());
                if (player != null && player.chooseUse(Outcome.PlayForFree, "Cast another nonland card exiled with " + sourceObject.getLogName() + " without paying that card's mana cost?", source, game)) {
                    FilterNonlandCard realFilter = filter.copy();
                    realFilter.add(Predicates.not(new CardIdPredicate(spell.getSourceId())));
                    TargetCardInExile target = new TargetCardInExile(0, 1, realFilter, source.getSourceId());
                    target.setNotTarget(true);
                    if (player.choose(Outcome.PlayForFree, game.getExile().getExileZone(exileZoneId), target, game)) {
                        Card card = game.getCard(target.getFirstTarget());
                        if (card != null && !card.getId().equals(spell.getSourceId())) {
                            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                            player.cast(player.chooseAbilityForCast(card, game, true), game, true, new ApprovingObject(source, game));
                            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public KnowledgePoolEffect2 copy() {
        return new KnowledgePoolEffect2(this);
    }

}
