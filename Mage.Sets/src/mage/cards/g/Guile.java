package mage.cards.g;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByOneEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class Guile extends CardImpl {

    public Guile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}{U}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.INCARNATION);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Guile can't be blocked except by three or more creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedByOneEffect(3)));

        // If a spell or ability you control would counter a spell, instead exile that 
        // spell and you may play that card without paying its mana cost.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GuileReplacementEffect()));

        // When Guile is put into a graveyard from anywhere, shuffle it into its owner's library.
        this.addAbility(new PutIntoGraveFromAnywhereSourceTriggeredAbility(new ShuffleIntoLibrarySourceEffect()));
    }

    private Guile(final Guile card) {
        super(card);
    }

    @Override
    public Guile copy() {
        return new Guile(this);
    }
}

class GuileReplacementEffect extends ReplacementEffectImpl {

    GuileReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If a spell or ability you control would counter a spell, "
                + "instead exile that spell and you may play that card without paying its mana cost";
    }

    GuileReplacementEffect(final GuileReplacementEffect effect) {
        super(effect);
    }

    @Override
    public GuileReplacementEffect copy() {
        return new GuileReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        Player controller = game.getPlayer(source.getControllerId());
        if (spell != null
                && controller != null) {
            controller.moveCards(spell, Zone.EXILED, source, game);
            if (!spell.isCopy()) { // copies doesn't exists in exile zone
                Card spellCard = spell.getCard();
                if (spellCard != null
                        && controller.chooseUse(Outcome.PlayForFree, "Play " + spellCard.getIdName() + " for free?", source, game)) {
                    controller.playCard(spellCard, game, true, new ApprovingObject(source, game));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Spell counteredSpell = game.getStack().getSpell(event.getTargetId());
        StackObject counteringObject = game.getStack().getStackObject(event.getSourceId());
        return counteredSpell != null
                && counteringObject != null
                && counteringObject.isControlledBy(source.getControllerId());
    }
}
