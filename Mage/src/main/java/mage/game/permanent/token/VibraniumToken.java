package mage.game.permanent.token;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.constants.CardType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author muz
 */
public final class VibraniumToken extends TokenImpl {

    public VibraniumToken() {
        super("Vibranium Token", "Vibranium token");
        cardType.add(CardType.ARTIFACT);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // {T}: Add {C}. This mana can't be spent to cast a nonartifact spell.
        this.addAbility(new ConditionalColorlessManaAbility(1, makeBuilder()));
    }

    private VibraniumToken(final VibraniumToken token) {
        super(token);
    }

    public VibraniumToken copy() {
        return new VibraniumToken(this);
    }

    public static VibraniumTokenManaBuilder makeBuilder() {
        return new VibraniumTokenManaBuilder();
    }
}

class VibraniumTokenManaBuilder extends ConditionalManaBuilder {

    public VibraniumTokenManaBuilder() {
    }

    @Override
    public ConditionalMana build(Object... options) {
        this.mana.setFlag(true); // indicates that the mana is from second ability
        return new VibraniumTokenConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "This mana can't be spent to cast a nonartifact spell.";
    }
}

class VibraniumTokenConditionalMana extends ConditionalMana {

    VibraniumTokenConditionalMana(Mana mana) {
        super(mana);
        staticText = "This mana can't be spent to cast a nonartifact spell.";
        addCondition(new VibraniumTokenManaCondition());
    }
}

class VibraniumTokenManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (!(source instanceof SpellAbility) || source.isActivated()) {
            return true;
        }
        MageObject object = game.getObject(source);
        return object != null && object.isArtifact(game);
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        return apply(game, source);
    }
}
