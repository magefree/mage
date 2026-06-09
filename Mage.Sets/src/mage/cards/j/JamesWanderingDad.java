package mage.cards.j;

import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
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
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SCIENTIST}, "{2}{U}",
                "Follow Him",
                new CardType[]{CardType.INSTANT}, "{X}{U}{U}");

        // James, Wandering Dad
        this.getLeftHalfCard().setPT(2, 4);

        // {T}: Add {C}{C}. Spend this mana only to activate abilities.
        this.getLeftHalfCard().addAbility(new ConditionalColorlessManaAbility(
                new TapSourceCost(), 2, new JamesWanderingDadManaBuilder()
        ));

        // Follow Him
        // {X}{U}{U}
        // Instant — Adventure
        // Investigate X times.
        this.getRightHalfCard().getSpellAbility().addEffect(
                new InvestigateEffect(GetXValue.instance)
                        .setText("Investigate X times")
        );

        finalizeCard();
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
        return source != null
                && !source.isActivated()
                && source.isActivatedAbility();
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        return apply(game, source);
    }
}

