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
        this.addAbility(new EntersBattlefieldTriggeredAbility(new KnowledgePoolExileThreeCardsEffect(), false).setAbilityWord(AbilityWord.IMPRINT));

        // Whenever a player casts a spell from their hand, that player exiles it.
        // If the player does, they may cast another nonland card exiled with Knowledge Pool without paying that card's mana cost.
        this.addAbility(new KnowledgePoolWhenCastFromHandAbility());
    }

    private KnowledgePool(final KnowledgePool card) {
        super(card);
    }

    @Override
    public KnowledgePool copy() {
        return new KnowledgePool(this);
    }
}

class KnowledgePoolExileThreeCardsEffect extends OneShotEffect {

    public KnowledgePoolExileThreeCardsEffect() {
        super(Outcome.Neutral);
        staticText = "each player exiles the top three cards of their library";
    }

    public KnowledgePoolExileThreeCardsEffect(final KnowledgePoolExileThreeCardsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) { return false; }

        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject == null) { return false; }

        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) { continue; }

            player.moveCardsToExile(
                    player.getLibrary().getTopCards(game, 3),
                    source,
                    game,
                    true,
                    CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()),
                    sourceObject.getIdName() + " (" + sourceObject.getZoneChangeCounter(game) + ')'
            );
        }
        return true;
    }

    @Override
    public KnowledgePoolExileThreeCardsEffect copy() {
        return new KnowledgePoolExileThreeCardsEffect(this);
    }
}

class KnowledgePoolWhenCastFromHandAbility extends TriggeredAbilityImpl {

    public KnowledgePoolWhenCastFromHandAbility() {
        super(Zone.BATTLEFIELD, new KnowledgePoolExileAndPlayEffect(), false);
    }

    private KnowledgePoolWhenCastFromHandAbility(final KnowledgePoolWhenCastFromHandAbility ability) {
        super(ability);
    }

    @Override
    public KnowledgePoolWhenCastFromHandAbility copy() {
        return new KnowledgePoolWhenCastFromHandAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getZone() != Zone.HAND) { return false; }

        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null) { return false; }

        for (Effect effect : this.getEffects()) {
            effect.setTargetPointer(new FixedTarget(event.getTargetId()));
        }
        return true;
    }
}

class KnowledgePoolExileAndPlayEffect extends OneShotEffect {

    public KnowledgePoolExileAndPlayEffect() {
        super(Outcome.Neutral);
        staticText = "Whenever a player casts a spell from their hand, that player exiles it. If the player does, they may cast another nonland card exiled with {this} without paying that card's mana cost";
    }

    private KnowledgePoolExileAndPlayEffect(final KnowledgePoolExileAndPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell == null) { return false; }

        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourceObject == null ) { return false; }

        Player spellController = game.getPlayer(spell.getControllerId());
        if (spellController == null) { return false; }

        UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), sourceObject.getZoneChangeCounter(game));

        if (!spellController.moveCardsToExile(spell, source, game, true, exileZoneId, sourceObject.getIdName())) {
            // The card didn't make it to exile, none of Knowledge Pool's effect applied
            return false;
        }

        // From here on down the function returns true since at least part of the effect went off

        if (!spellController.chooseUse(Outcome.PlayForFree, "Cast another nonland card exiled with " + sourceObject.getLogName() + " without paying that card's mana cost?", source, game)) {
            // Pleyer didn't want to cast another spell BUT their original spell was exiled with Knowledge Pool, so return true.
            return true;
        }
        FilterNonlandCard filter = new FilterNonlandCard("nonland card exiled with Knowledge Pool");
        filter.add(Predicates.not(new CardIdPredicate(spell.getSourceId())));

        TargetCardInExile target = new TargetCardInExile(0, 1, filter, source.getSourceId());
        target.setNotTarget(true);

        if (!spellController.choose(Outcome.PlayForFree, game.getExile().getExileZone(exileZoneId), target, game)) {
            // Player chose to not cast any ofthe spells
            return true;
        }

        Card card = game.getCard(target.getFirstTarget());
        if (card == null || card.getId().equals(spell.getSourceId())) { return true; }

        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
        spellController.cast(spellController.chooseAbilityForCast(card, game, true), game, true, new ApprovingObject(source, game));
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);

        return true;
    }

    @Override
    public KnowledgePoolExileAndPlayEffect copy() {
        return new KnowledgePoolExileAndPlayEffect(this);
    }
}
