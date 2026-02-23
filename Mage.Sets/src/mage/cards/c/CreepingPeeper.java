package mage.cards.c;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.RoomUnlockAbility;
import mage.abilities.common.TurnFaceUpAbility;
import mage.abilities.condition.Condition;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.command.Commander;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class CreepingPeeper extends CardImpl {

    public CreepingPeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        
        this.subtype.add(SubType.EYE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {T}: Add {U}. Spend this mana only to cast an enchantment spell, unlock a door, or turn a permanent face up.
        this.addAbility(new ConditionalColoredManaAbility(Mana.BlueMana(1), new CreepingPeeperManaBuilder()));

    }

    private CreepingPeeper(final CreepingPeeper card) {
        super(card);
    }

    @Override
    public CreepingPeeper copy() {
        return new CreepingPeeper(this);
    }
}

class CreepingPeeperManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new CreepingPeeperConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast an enchantment spell, unlock a door, or turn a permanent face up";
    }
}

class CreepingPeeperConditionalMana extends ConditionalMana {

    public CreepingPeeperConditionalMana(Mana mana) {
        super(mana);
        addCondition(new CreepingPeeperManaCondition());
    }
}

class CreepingPeeperManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        // cast an enchantment
        if (source instanceof SpellAbility && !source.isActivated()) {
            MageObject object = game.getObject(source);
            if ((object instanceof StackObject)) {
                return object.isEnchantment(game);
            }
            // checking mana without real cast
            if (game.inCheckPlayableState()) {
                Spell spell = null;
                if (object instanceof Card) {
                    spell = new Spell((Card) object, (SpellAbility) source, source.getControllerId(), game.getState().getZone(source.getSourceId()), game);
                } else if (object instanceof Commander) {
                    spell = new Spell(((Commander) object).getSourceObject(), (SpellAbility) source, source.getControllerId(), game.getState().getZone(source.getSourceId()), game);
                }
                return spell != null && spell.isEnchantment(game);
            }
        }
        // unlock a door
        if (source instanceof RoomUnlockAbility) {
            return true;
        }
        // turn a permanent face up
        if (source instanceof TurnFaceUpAbility) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent == null) {
                return false;
            }
            return permanent.isManifested()
                    || permanent.isMorphed()
                    || permanent.isDisguised()
                    || permanent.isCloaked();
        }
        return false;
    }
}
