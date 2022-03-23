package mage.cards.d;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class DwarvenShrine extends CardImpl {

    public DwarvenShrine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");


        // Whenever a player casts a spell, Dwarven Shrine deals X damage to that player, where X is twice the number of cards in all graveyards with the same name as that spell.
        this.addAbility(new DwarvenShrineTriggeredAbility());
    }

    private DwarvenShrine(final DwarvenShrine card) {
        super(card);
    }

    @Override
    public DwarvenShrine copy() {
        return new DwarvenShrine(this);
    }
}

class DwarvenShrineTriggeredAbility extends TriggeredAbilityImpl {

    public DwarvenShrineTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DwarvenShrineEffect(), false);
    }

    public DwarvenShrineTriggeredAbility(final DwarvenShrineTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DwarvenShrineTriggeredAbility copy() {
        return new DwarvenShrineTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        MageObject mageObject = game.getObject(sourceId);
        if (spell != null && mageObject != null) {
            game.getState().setValue("dwarvenShrine" + mageObject, spell);
            return true;
        }
        return false;
    }
}

class DwarvenShrineEffect extends OneShotEffect {

    public DwarvenShrineEffect() {
        super(Outcome.Detriment);
        staticText = "Whenever a player casts a spell, {this} deals X damage to that player, where X is twice the number of cards in all graveyards with the same name as that spell.";
    }

    public DwarvenShrineEffect(final DwarvenShrineEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = 0;
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            Spell spell = (Spell) game.getState().getValue("dwarvenShrine" + mageObject);
            if (spell != null) {
                Player controller = game.getPlayer(spell.getControllerId());
                if (controller != null) {
                    String name = spell.getName();
                    FilterCard filterCardName = new FilterCard();
                    filterCardName.add(new NamePredicate(name));
                    for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                        Player player = game.getPlayer(playerId);
                        if (player != null) {
                            count += player.getGraveyard().count(filterCardName, game);
                        }
                    }
                    controller.damage(count * 2, mageObject.getId(), source, game);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public DwarvenShrineEffect copy() {
        return new DwarvenShrineEffect(this);
    }
}
