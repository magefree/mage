package mage.abilities.keyword;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.StaticAbility;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.*;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ProtectionAbility extends StaticAbility {

    protected Filter filter;
    protected boolean removeAuras;
    protected static List<ObjectColor> colors = new ArrayList<>();
    protected UUID auraIdNotToBeRemoved; // defines an Aura objectId that will not be removed from this protection ability

    public ProtectionAbility(Filter filter) {
        super(Zone.BATTLEFIELD, null);
        this.filter = filter;
        this.removeAuras = true;
        this.auraIdNotToBeRemoved = null;
    }

    public ProtectionAbility(final ProtectionAbility ability) {
        super(ability);
        this.filter = ability.filter.copy();
        this.removeAuras = ability.removeAuras;
        this.auraIdNotToBeRemoved = ability.auraIdNotToBeRemoved;
    }

    public static ProtectionAbility from(ObjectColor color) {
        FilterObject filter = new FilterObject(color.getDescription());
        filter.add(new ColorPredicate(color));
        colors.add(color);
        return new ProtectionAbility(filter);
    }

    public static ProtectionAbility from(ObjectColor color1, ObjectColor color2) {
        FilterObject filter = new FilterObject(color1.getDescription() + " and from " + color2.getDescription());
        filter.add(Predicates.or(new ColorPredicate(color1), new ColorPredicate(color2)));
        colors.add(color1);
        colors.add(color2);
        return new ProtectionAbility(filter);
    }

    @Override
    public ProtectionAbility copy() {
        return new ProtectionAbility(this);
    }

    @Override
    public String getRule() {

        return "protection from " + filter.getMessage() + (removeAuras ? "" : ". This effect doesn't remove auras.");
    }

    public boolean canTarget(MageObject source, Game game) {
        if (filter instanceof FilterPermanent) {
            if (source instanceof Permanent) {
                return !filter.match(source, game);
            }
            return true;
        }

        if (filter instanceof FilterCard) {
            if (source instanceof Permanent) {
                return !((FilterCard) filter).match((Card) source, getSourceId(), ((Permanent) source).getControllerId(), game);
            } else if (source instanceof Card) {
                return !((FilterCard) filter).match((Card) source, getSourceId(), ((Card) source).getOwnerId(), game);
            }
            return true;
        }

        if (filter instanceof FilterSpell) {
            if (source instanceof Spell) {
                return !filter.match(source, game);
            }
            // Problem here is that for the check if a player can play a Spell, the source
            // object is still a card and not a spell yet. So return only if the source object can't be a spell
            // otherwise the following FilterObject check will be applied
            if (source instanceof StackObject
                    || (!source.isInstant() && !source.isSorcery())) {
                return true;
            }
        }
        
        // Emrakul, the Aeons Torn
        if (filter instanceof FilterStackObject) {
            if (filter.match(source, game)) {
                return (!source.isInstantOrSorcery());
            }
        }
        
        if (filter instanceof FilterObject) {
            return !filter.match(source, game);
        }

        if (filter instanceof FilterPlayer) {
            Player player = null;
            if (source instanceof Permanent) {
                player = game.getPlayer(((Permanent) source).getControllerId());
            } else if (source instanceof Card) {
                player = game.getPlayer(((Card) source).getOwnerId());
            }
            return !((FilterPlayer) filter).match(player, getSourceId(), this.getControllerId(), game);
        }

        return true;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(FilterCard filter) {
        this.filter = filter;
    }

    public void setRemovesAuras(boolean removeAuras) {
        this.removeAuras = removeAuras;
    }

    public boolean removesAuras() {
        return removeAuras;
    }

    public List<ObjectColor> getColors() {
        return colors;
    }

    public UUID getAuraIdNotToBeRemoved() {
        return auraIdNotToBeRemoved;
    }

    public void setAuraIdNotToBeRemoved(UUID auraIdNotToBeRemoved) {
        this.auraIdNotToBeRemoved = auraIdNotToBeRemoved;
    }

}
