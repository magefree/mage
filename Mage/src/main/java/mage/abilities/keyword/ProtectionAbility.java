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
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ProtectionAbility extends StaticAbility {

    protected Filter filter;
    protected boolean removeAuras;
    protected boolean removeEquipment;
    protected boolean doesntRemoveControlled;
    protected UUID auraIdNotToBeRemoved; // defines an Aura objectId that will not be removed from this protection ability

    public ProtectionAbility(Filter filter) {
        super(Zone.BATTLEFIELD, null);
        this.filter = filter;
        this.removeAuras = true;
        this.removeEquipment = true;
        this.doesntRemoveControlled = false;
        this.auraIdNotToBeRemoved = null;
    }

    public ProtectionAbility(final ProtectionAbility ability) {
        super(ability);
        this.filter = ability.filter.copy();
        this.removeAuras = ability.removeAuras;
        this.removeEquipment = ability.removeEquipment;
        this.doesntRemoveControlled = ability.doesntRemoveControlled;
        this.auraIdNotToBeRemoved = ability.auraIdNotToBeRemoved;
    }

    public static ProtectionAbility from(ObjectColor color) {
        FilterObject filter = new FilterObject(getFilterText(color));
        filter.add(new ColorPredicate(color));
        return new ProtectionAbility(filter);
    }

    public static ProtectionAbility from(ObjectColor color1, ObjectColor color2) {
        FilterObject filter = new FilterObject(color1.getDescription() + " and from " + color2.getDescription());
        filter.add(Predicates.or(new ColorPredicate(color1), new ColorPredicate(color2)));
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
                    || !source.isInstantOrSorcery(game)) {
                return true;
            }
        }

        // Emrakul, the Aeons Torn
        if (filter instanceof FilterStackObject) {
            if (filter.match(source, game)) {
                return !source.isInstantOrSorcery(game);
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

    private static final String getFilterText(ObjectColor color) {
        return CardUtil.concatWithAnd(
                color.getColors()
                        .stream()
                        .map(ObjectColor::getDescription)
                        .map(s -> "from " + s)
                        .collect(Collectors.toList())
        ).replaceFirst("from ", "");
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

    public void setRemoveEquipment(boolean removeEquipment) {
        this.removeEquipment = removeEquipment;
    }

    public boolean removesEquipment() {
        return removeEquipment;
    }

    public void setDoesntRemoveControlled(boolean doesntRemoveControlled) {
        this.doesntRemoveControlled = doesntRemoveControlled;
    }

    public boolean getDoesntRemoveControlled() {
        return doesntRemoveControlled;
    }

    public UUID getAuraIdNotToBeRemoved() {
        return auraIdNotToBeRemoved;
    }

    public void setAuraIdNotToBeRemoved(UUID auraIdNotToBeRemoved) {
        this.auraIdNotToBeRemoved = auraIdNotToBeRemoved;
    }

}
