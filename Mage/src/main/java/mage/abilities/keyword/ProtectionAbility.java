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
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

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
    protected ObjectColor fromColor;
    protected UUID auraIdNotToBeRemoved; // defines an Aura objectId that will not be removed from this protection ability
    private String staticText;

    public ProtectionAbility(Filter filter) {
        super(Zone.BATTLEFIELD, null);
        this.filter = filter;
        this.removeAuras = true;
        this.removeEquipment = true;
        this.doesntRemoveControlled = false;
        this.fromColor = new ObjectColor();
        this.auraIdNotToBeRemoved = null;
        this.staticText = null;
    }

    protected ProtectionAbility(final ProtectionAbility ability) {
        super(ability);
        this.filter = ability.filter.copy();
        this.removeAuras = ability.removeAuras;
        this.removeEquipment = ability.removeEquipment;
        this.doesntRemoveControlled = ability.doesntRemoveControlled;
        this.fromColor = ability.fromColor;
        this.auraIdNotToBeRemoved = ability.auraIdNotToBeRemoved;
        this.staticText = ability.staticText;
    }

    public static ProtectionAbility from(ObjectColor color) {
        FilterObject filter = new FilterObject(getFilterText(color));
        filter.add(new ColorPredicate(color));
        ProtectionAbility ability = new ProtectionAbility(filter);
        ability.getFromColor().addColor(color);
        return ability;
    }

    public static ProtectionAbility from(ObjectColor color1, ObjectColor color2) {
        FilterObject filter = new FilterObject(color1.getDescription() + " and from " + color2.getDescription());
        filter.add(Predicates.or(new ColorPredicate(color1), new ColorPredicate(color2)));
        ProtectionAbility ability = new ProtectionAbility(filter);
        ability.getFromColor().addColor(color1);
        ability.getFromColor().addColor(color2);
        return ability;
    }

    @Override
    public ProtectionAbility copy() {
        return new ProtectionAbility(this);
    }

    @Override
    public String getRule() {
        if (this.staticText != null && !this.staticText.isEmpty()) {
            return this.staticText;
        }
        return (flavorWord == null ? "protection from " : CardUtil.italicizeWithEmDash(flavorWord) + "Protection from ")
                + filter.getMessage() + (removeAuras ? "" : ". This effect doesn't remove Auras.");
    }

    public ProtectionAbility setText(String text) {
        this.staticText = text;
        return this;
    }

    public boolean canTarget(MageObject source, Game game) {
        // TODO: need research, protection ability can be bugged with aura and aura permanents, spells (see below)

        // permanent restriction
        if (filter instanceof FilterPermanent) {
            if (source instanceof Permanent) {
                return !((FilterPermanent) filter).match((Permanent) source, game);
            }
            // TODO: possible bugged, need token too?
            return true;
        }

        // card restriction
        if (filter instanceof FilterCard) {
            if (source instanceof Card) {
                return !((FilterCard) filter).match((Card) source, ((Card) source).getControllerOrOwnerId(), this, game);
            } else if (source instanceof Token) {
                // make fake permanent cause it checked before real permanent create
                // warning, Token don't have controllerId info, so it can be a problem here
                // TODO: wtf, possible bugged for filters that checking controller/player (if so then use with controllerId param)
                PermanentToken fakePermanent = new PermanentToken((Token) source, UUID.randomUUID(), game);
                return !((FilterCard) filter).match(fakePermanent, game);
            }
            return true;
        }

        // spell restriction
        if (filter instanceof FilterSpell) {
            // TODO: need research, possible bugged
            // Problem here is that for the check if a player can play a Spell, the source
            // object is still a card and not a spell yet.
            if (source instanceof Spell || game.inCheckPlayableState() && source.isInstantOrSorcery(game)) {
                return !filter.match(source, game);
            }
            return true;
        }

        // unknown restriction
        if (filter instanceof FilterObject) {
            return !((FilterObject) filter).match(source, game);
        }

        // player restriction
        if (filter instanceof FilterPlayer) {
            Player player = null;
            if (source instanceof Card) {
                player = game.getPlayer(((Card) source).getControllerOrOwnerId());
            } else if (source instanceof Token) {
                // TODO: fakePermanent will not work here like above, so try to rework whole logic
                throw new IllegalArgumentException("Wrong code usage: token can't be checked in player restriction filter");

            }
            return !((FilterPlayer) filter).match(player, this.getControllerId(), this, game);
        }

        return true;
    }

    private static String getFilterText(ObjectColor color) {
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

    public ObjectColor getFromColor() {
        return fromColor;
    }

    public UUID getAuraIdNotToBeRemoved() {
        return auraIdNotToBeRemoved;
    }

    public void setAuraIdNotToBeRemoved(UUID auraIdNotToBeRemoved) {
        this.auraIdNotToBeRemoved = auraIdNotToBeRemoved;
    }

}
