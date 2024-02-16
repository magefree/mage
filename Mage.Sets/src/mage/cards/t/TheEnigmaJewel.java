package mage.cards.t;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.keyword.CraftAbility;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheEnigmaJewel extends CardImpl {

    public TheEnigmaJewel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.secondSideCardClazz = mage.cards.l.LocusOfEnlightenment.class;

        // The Enigma Jewel enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {C}{C}. Spend this mana only to activate abilities.
        this.addAbility(new ConditionalColorlessManaAbility(2, new TheEnigmaJewelManaBuilder()));

        // Craft with four or more nonlands with activated abilities {8}{U}
        this.addAbility(new CraftAbility(
                "{8}{U}", "four or more nonlands with activated abilities", "other " +
                "nonland permanents you control with activated abilities and/or nonland cards in your " +
                "graveyard with activated abilities", 4, Integer.MAX_VALUE, TheEnigmaJewelPredicate.instance
        ));
    }

    private TheEnigmaJewel(final TheEnigmaJewel card) {
        super(card);
    }

    @Override
    public TheEnigmaJewel copy() {
        return new TheEnigmaJewel(this);
    }
}

enum TheEnigmaJewelPredicate implements Predicate<MageObject> {
    instance;

    @Override
    public boolean apply(MageObject input, Game game) {
        return !input.isLand(game)
                && getAbilities(input, game)
                .stream()
                .anyMatch(ActivatedAbilityImpl.class::isInstance);
    }

    private static Abilities<Ability> getAbilities(MageObject input, Game game) {
        if (input instanceof Permanent) {
            return ((Permanent) input).getAbilities(game);
        } else if (input instanceof Card) {
            return ((Card) input).getAbilities(game);
        } else {
            throw new UnsupportedOperationException("there shouldn't be a nonpermanent, noncard object here");
        }
    }
}

class TheEnigmaJewelManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new TheEnigmaJewelConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to activate abilities";
    }
}

class TheEnigmaJewelConditionalMana extends ConditionalMana {

    TheEnigmaJewelConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to activate abilities";
        addCondition(new TheEnigmaJewelManaCondition());
    }
}

class TheEnigmaJewelManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source != null && !source.isActivated()) {
            return source.getAbilityType() == AbilityType.MANA
                    || source.getAbilityType() == AbilityType.ACTIVATED;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        return apply(game, source);
    }
}
