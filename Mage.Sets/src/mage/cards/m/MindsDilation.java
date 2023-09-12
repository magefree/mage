package mage.cards.m;

import mage.ApprovingObject;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.SpellsCastWatcher;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class MindsDilation extends CardImpl {

    public MindsDilation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{U}{U}");

        // Whenever an opponent casts their first spell each turn, 
        // that player exiles the top card of their library. If it's a nonland card,
        // you may cast it without paying its mana cost.
        this.addAbility(new MindsDilationTriggeredAbility(new MindsDilationEffect(),
                false));
    }

    private MindsDilation(final MindsDilation card) {
        super(card);
    }

    @Override
    public MindsDilation copy() {
        return new MindsDilation(this);
    }
}

class MindsDilationTriggeredAbility extends SpellCastOpponentTriggeredAbility {

    MindsDilationTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, StaticFilters.FILTER_SPELL, optional);
    }

    private MindsDilationTriggeredAbility(MindsDilationTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MindsDilationTriggeredAbility copy() {
        return new MindsDilationTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
            if (watcher != null) {
                List<Spell> spells = watcher.getSpellsCastThisTurn(event.getPlayerId());
                if (spells != null && spells.size() == 1) {
                    for (Effect effect : getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts their first spell each turn, "
                + "that player exiles the top card of their library."
                + " If it's a nonland card, you may cast it without "
                + "paying its mana cost.";
    }
}

class MindsDilationEffect extends OneShotEffect {

    MindsDilationEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "that player exiles the top card of their library. "
                + "If it's a nonland card, you may cast it without paying its mana cost";
    }

    private MindsDilationEffect(final MindsDilationEffect effect) {
        super(effect);
    }

    @Override
    public MindsDilationEffect copy() {
        return new MindsDilationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller != null
                && sourceObject != null
                && opponent != null) {
            if (opponent.getLibrary().hasCards()) {
                Card card = opponent.getLibrary().getFromTop(game);
                if (card != null
                        && opponent.moveCards(card, Zone.EXILED, source, game)) {
                    if (!card.isLand(game)) {
                        if (controller.chooseUse(outcome, "Cast " + card.getLogName()
                                + " without paying its mana cost from exile?", source, game)) {
                            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                            controller.cast(controller.chooseAbilityForCast(card, game, true),
                                    game, true, new ApprovingObject(source, game));
                            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
