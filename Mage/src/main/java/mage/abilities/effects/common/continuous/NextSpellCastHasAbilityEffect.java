package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.common.SpellsCastWatcher;

/**
 * @author xenohedron
 */
public class NextSpellCastHasAbilityEffect extends ContinuousEffectImpl {

    private int spellsCast;
    private final Ability ability;
    private final FilterCard filter;

    public NextSpellCastHasAbilityEffect(Ability ability) {
        this(ability, StaticFilters.FILTER_CARD);
    }

    public NextSpellCastHasAbilityEffect(Ability ability, FilterCard filter) {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability;
        this.filter = filter;
        staticText = "the next " + filter.getMessage().replace("card", "spell")
                + " you cast this turn has " + CardUtil.getTextWithFirstCharLowerCase(CardUtil.stripReminderText(ability.getRule()));
    }

    private NextSpellCastHasAbilityEffect(final NextSpellCastHasAbilityEffect effect) {
        super(effect);
        this.spellsCast = effect.spellsCast;
        this.ability = effect.ability;
        this.filter = effect.filter;
    }

    @Override
    public NextSpellCastHasAbilityEffect copy() {
        return new NextSpellCastHasAbilityEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher != null) {
            spellsCast = watcher.getCount(source.getControllerId());
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (player == null || watcher == null) {
            return false;
        }
        //check if a spell was cast before
        if (watcher.getCount(source.getControllerId()) > spellsCast) {
            discard(); // only one use
            return false;
        }
        for (Card card : game.getExile().getAllCardsByRange(game, source.getControllerId())) {
            if (filter.match(card, game)) {
                game.getState().addOtherAbility(card, ability);
            }
        }
        for (Card card : player.getLibrary().getCards(game)) {
            if (filter.match(card, game)) {
                game.getState().addOtherAbility(card, ability);
            }
        }
        for (Card card : player.getHand().getCards(game)) {
            if (filter.match(card, game)) {
                game.getState().addOtherAbility(card, ability);
            }
        }
        for (Card card : player.getGraveyard().getCards(game)) {
            if (filter.match(card, game)) {
                game.getState().addOtherAbility(card, ability);
            }
        }
        // workaround to gain cost reduction abilities to commanders before cast (make it playable)
        game.getCommanderCardsFromCommandZone(player, CommanderCardType.ANY)
                .stream()
                .filter(card -> filter.match(card, game))
                .forEach(card -> game.getState().addOtherAbility(card, ability));

        for (StackObject stackObject : game.getStack()) {
            if (!(stackObject instanceof Spell) || !stackObject.isControlledBy(source.getControllerId())) {
                continue;
            }
            // TODO: Distinguish "you cast" to exclude copies
            Card card = game.getCard(stackObject.getSourceId());
            if (card != null && filter.match((Spell) stackObject, game)) {
                game.getState().addOtherAbility(card, ability);
            }
        }
        return true;
    }
}
