package mage.cards.s;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SteelswarmOperator extends CardImpl {

    public SteelswarmOperator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {T}: Add {U}. Spend this mana only to cast an artifact spell.
        this.addAbility(new ConditionalColoredManaAbility(Mana.BlueMana(1), new SteelswarmOperatorSpellManaBuilder()));

        // {T}: Add {U}{U}. Spend this mana only to activate abilities of artifact sources.
        this.addAbility(new ConditionalColoredManaAbility(Mana.BlueMana(2), new SteelswarmOperatorAbilitiesManaBuilder()));
    }

    private SteelswarmOperator(final SteelswarmOperator card) {
        super(card);
    }

    @Override
    public SteelswarmOperator copy() {
        return new SteelswarmOperator(this);
    }
}

class SteelswarmOperatorSpellManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new SteelswarmOperatorSpellConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast an artifact spell";
    }
}

class SteelswarmOperatorSpellConditionalMana extends ConditionalMana {

    public SteelswarmOperatorSpellConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast an artifact spell";
        addCondition(new SteelswarmOperatorSpellManaCondition());
    }
}

class SteelswarmOperatorSpellManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source);
        return source instanceof SpellAbility
                && !source.isActivated()
                && sourceObject != null
                && sourceObject.isArtifact(game);
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        return apply(game, source);
    }
}

class SteelswarmOperatorAbilitiesManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new SteelswarmOperatorAbilitiesConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to activate abilities of artifact sources";
    }
}

class SteelswarmOperatorAbilitiesConditionalMana extends ConditionalMana {

    public SteelswarmOperatorAbilitiesConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to activate abilities of artifact sources";
        addCondition(new SteelswarmOperatorAbilitiesManaCondition());
    }
}

class SteelswarmOperatorAbilitiesManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source);
        return source != null
                && !source.isActivated()
                && source.isActivatedAbility()
                && sourceObject != null
                && sourceObject.isArtifact(game);
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        return apply(game, source);
    }
}
