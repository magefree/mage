package mage.game.permanent.token;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.Card;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.command.Commander;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PowerstoneToken extends TokenImpl {

    public PowerstoneToken() {
        super("Powerstone Token", "Powerstone token");
        cardType.add(CardType.ARTIFACT);

        // {T}: Add {C}. This mana can't be spent to cast a nonartifact spell.
        this.addAbility(new ConditionalColorlessManaAbility(1, new PowerstoneTokenManaBuilder()));

        availableImageSetCodes = Arrays.asList("DMU");
    }

    public PowerstoneToken(final PowerstoneToken token) {
        super(token);
    }

    public PowerstoneToken copy() {
        return new PowerstoneToken(this);
    }
}

class PowerstoneTokenManaBuilder extends ConditionalManaBuilder {

    public PowerstoneTokenManaBuilder() {
    }

    @Override
    public ConditionalMana build(Object... options) {
        this.mana.setFlag(true); // indicates that the mana is from second ability
        return new PowerstoneTokenConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "This mana can't be spent to cast a nonartifact spell.";
    }
}

class PowerstoneTokenConditionalMana extends ConditionalMana {

    PowerstoneTokenConditionalMana(Mana mana) {
        super(mana);
        staticText = "This mana can't be spent to cast a nonartifact spell.";
        addCondition(new PowerstoneTokenManaCondition());
    }
}

class PowerstoneTokenManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (!(source instanceof SpellAbility)) {
            return false;
        }
        MageObject object = game.getObject(source);
        if (object instanceof StackObject) {
            return object instanceof StackAbility || !object.isArtifact(game);
        }
        if (!game.inCheckPlayableState()) {
            return false;
        }
        Spell spell;
        if (object instanceof Card) {
            spell = new Spell(
                    (Card) object, (SpellAbility) source, source.getControllerId(),
                    game.getState().getZone(source.getSourceId()), game
            );
        } else if (object instanceof Commander) {
            spell = new Spell(
                    ((Commander) object).getSourceObject(),
                    (SpellAbility) source, source.getControllerId(),
                    game.getState().getZone(source.getSourceId()), game
            );
        } else {
            spell = null;
        }
        return spell == null || spell.isArtifact(game);
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        return apply(game, source);
    }
}
