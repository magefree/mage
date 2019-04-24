
package mage.cards.r;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author emerald000
 */
public final class RashmiEternitiesCrafter extends CardImpl {

    public RashmiEternitiesCrafter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cast your first spell each turn, reveal the top card of your library. If it's a nonland card with converted mana cost less than that spell's, you may cast it without paying its mana cost. If you don't cast the revealed card, put it into your hand.
        this.addAbility(new RashmiEternitiesCrafterTriggeredAbility(), new SpellsCastWatcher());
    }

    public RashmiEternitiesCrafter(final RashmiEternitiesCrafter card) {
        super(card);
    }

    @Override
    public RashmiEternitiesCrafter copy() {
        return new RashmiEternitiesCrafter(this);
    }
}

class RashmiEternitiesCrafterTriggeredAbility extends SpellCastControllerTriggeredAbility {

    RashmiEternitiesCrafterTriggeredAbility() {
        super(new RashmiEternitiesCrafterEffect(), false);
    }

    RashmiEternitiesCrafterTriggeredAbility(RashmiEternitiesCrafterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RashmiEternitiesCrafterTriggeredAbility copy() {
        return new RashmiEternitiesCrafterTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            SpellsCastWatcher watcher = (SpellsCastWatcher) game.getState().getWatchers().get(SpellsCastWatcher.class.getSimpleName());
            if (watcher != null) {
                List<Spell> spells = watcher.getSpellsCastThisTurn(event.getPlayerId());
                if (spells != null && spells.size() == 1) {
                    Spell spell = game.getStack().getSpell(event.getTargetId());
                    if (spell != null) {
                        for (Effect effect : getEffects()) {
                            effect.setValue("RashmiEternitiesCrafterCMC", spell.getConvertedManaCost());
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast your first spell each turn, reveal the top card of your library. If it's a nonland card with converted mana cost less than that spell's, you may cast it without paying its mana cost. If you don't cast the revealed card, put it into your hand.";
    }
}

class RashmiEternitiesCrafterEffect extends OneShotEffect {

    RashmiEternitiesCrafterEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "reveal the top card of your library. If it's a nonland card with converted mana cost less than that spell's, you may cast it without paying its mana cost. If you don't cast the revealed card, put it into your hand";
    }

    RashmiEternitiesCrafterEffect(final RashmiEternitiesCrafterEffect effect) {
        super(effect);
    }

    @Override
    public RashmiEternitiesCrafterEffect copy() {
        return new RashmiEternitiesCrafterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                controller.revealCards("Rashmi, Eternities Crafter", new CardsImpl(card), game);
                Object cmcObject = this.getValue("RashmiEternitiesCrafterCMC");
                if (cmcObject == null
                        || card.isLand()
                        || card.getConvertedManaCost() >= (int) cmcObject
                        || !controller.chooseUse(Outcome.PlayForFree, "Cast " + card.getName() + " without paying its mana cost?", source, game)
                        || !controller.cast(card.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game))) {
                    controller.moveCards(card, Zone.HAND, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
