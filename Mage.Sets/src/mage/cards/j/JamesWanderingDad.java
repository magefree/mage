package mage.cards.j;

import mage.ConditionalMana;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class JamesWanderingDad extends AdventureCard {

    public JamesWanderingDad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{2}{U}", "Follow Him", "{X}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {T}: Add {C}{C}. Spend this mana only to activate abilities.
        this.addAbility(new ConditionalColorlessManaAbility(
                new TapSourceCost(), 2, new JamesWanderingDadManaBuilder()
        ));

        // Follow Him
        // {X}{U}{U}
        // Instant — Adventure
        // Investigate X times.
        this.getSpellCard().getSpellAbility().addEffect(
                new InvestigateEffect(ManacostVariableValue.REGULAR)
                        .setText("Investigate X times")
        );

        this.finalizeAdventure();
    }

    private JamesWanderingDad(final JamesWanderingDad card) {
        super(card);
    }

    @Override
    public JamesWanderingDad copy() {
        return new JamesWanderingDad(this);
    }
}

// Mana building same as Cryptic Trilobite
class JamesWanderingDadManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new JamesWanderingDadConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to activate abilities";
    }
}

class JamesWanderingDadConditionalMana extends ConditionalMana {

    JamesWanderingDadConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to activate abilities";
        addCondition(new JamesWanderingDadManaCondition());
    }
}

class JamesWanderingDadManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source != null && !source.isActivated()) {
            // ex: SimpleManaAbility is an ACTIVATED ability, but it is categorized as a MANA ability
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

