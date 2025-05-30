package mage.cards.s;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.ConditionalColorlessManaAbility;
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
public final class SageOfTheUnknowable extends CardImpl {

    public SageOfTheUnknowable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // {T}: Add {C}. Spend this mana only to cast a colorless spell or to activate an ability.
        this.addAbility(new ConditionalColorlessManaAbility(new TapSourceCost(), 1, new SageOfTheUnknowableManaBuilder()));
    }

    private SageOfTheUnknowable(final SageOfTheUnknowable card) {
        super(card);
    }

    @Override
    public SageOfTheUnknowable copy() {
        return new SageOfTheUnknowable(this);
    }
}

class SageOfTheUnknowableManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new SageOfTheUnknowableConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a colorless spell or to activate an ability";
    }
}

class SageOfTheUnknowableConditionalMana extends ConditionalMana {

    public SageOfTheUnknowableConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast a colorless spell or to activate an ability";
        addCondition(new SageOfTheUnknowableManaCondition());
    }
}

class SageOfTheUnknowableManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        if (source instanceof SpellAbility && !source.isActivated()) {
            MageObject object = game.getObject(source);
            return object != null && object.getColor(game).isColorless();
        }
        return source.isActivatedAbility();
    }
}
