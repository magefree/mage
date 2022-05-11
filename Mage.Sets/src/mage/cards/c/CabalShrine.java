
package mage.cards.c;

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
public final class CabalShrine extends CardImpl {

    public CabalShrine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");
        

        // Whenever a player casts a spell, that player discards X cards, where X is the number of cards in all graveyards with the same name as that spell.
        this.addAbility(new CabalShrineTriggeredAbility());
    }

    private CabalShrine(final CabalShrine card) {
        super(card);
    }

    @Override
    public CabalShrine copy() {
        return new CabalShrine(this);
    }
}

class CabalShrineTriggeredAbility extends TriggeredAbilityImpl {

    public CabalShrineTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CabalShrineEffect(), false);
    }

    public CabalShrineTriggeredAbility(final CabalShrineTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CabalShrineTriggeredAbility copy() {
        return new CabalShrineTriggeredAbility(this);
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
            game.getState().setValue("cabalShrine" + mageObject, spell);
            return true;
        }
        return false;
    }

}

class CabalShrineEffect extends OneShotEffect {

    public CabalShrineEffect() {
        super(Outcome.Discard);
        staticText = "Whenever a player casts a spell, that player discards X cards, where X is the number of cards in all graveyards with the same name as that spell";
    }

    public CabalShrineEffect(final CabalShrineEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = 0;
        MageObject mageObject = game.getObject(source);
        if(mageObject != null) {
            Spell spell = (Spell) game.getState().getValue("cabalShrine" + mageObject);
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
                    controller.discard(count, false, false, source, game);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public CabalShrineEffect copy() {
        return new CabalShrineEffect(this);
    }
}
