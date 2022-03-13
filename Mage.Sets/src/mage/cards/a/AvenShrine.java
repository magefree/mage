
package mage.cards.a;

import java.util.UUID;
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

/**
 *
 * @author jeffwadsworth
 */
public final class AvenShrine extends CardImpl {

    public AvenShrine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");

        // Whenever a player casts a spell, that player gains X life, where X is the number of cards in all graveyards with the same name as that spell.
        this.addAbility(new AvenShrineTriggeredAbility());

    }

    private AvenShrine(final AvenShrine card) {
        super(card);
    }

    @Override
    public AvenShrine copy() {
        return new AvenShrine(this);
    }
}

class AvenShrineTriggeredAbility extends TriggeredAbilityImpl {

    public AvenShrineTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AvenShrineEffect(), false);
    }

    public AvenShrineTriggeredAbility(final AvenShrineTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AvenShrineTriggeredAbility copy() {
        return new AvenShrineTriggeredAbility(this);
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
            game.getState().setValue("avenShrine" + mageObject, spell);
            return true;
        }
        return false;
    }

}

class AvenShrineEffect extends OneShotEffect {

    public AvenShrineEffect() {
        super(Outcome.GainLife);
        staticText = "Whenever a player casts a spell, that player gains X life, where X is the number of cards in all graveyards with the same name as that spell";
    }

    public AvenShrineEffect(final AvenShrineEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = 0;
        MageObject mageObject = game.getObject(source);
        if(mageObject != null) {
            Spell spell = (Spell) game.getState().getValue("avenShrine" + mageObject);
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
                    controller.gainLife(count, game, source);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public AvenShrineEffect copy() {
        return new AvenShrineEffect(this);
    }
}
