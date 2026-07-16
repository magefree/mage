package mage.cards.q;

import java.util.UUID;

import mage.ConditionalMana;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.keyword.PowerUpAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.constants.SubType;
import mage.game.Game;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class QuinjetTechnician extends CardImpl {

    public QuinjetTechnician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());

        // {T}: Add {R}{R}. Spend this mana only to activate power-up abilities.
        this.addAbility(new ConditionalColoredManaAbility(
            new TapSourceCost(), Mana.RedMana(2), new QuinjetTechnicianManaBuilder()
        ));
    }

    private QuinjetTechnician(final QuinjetTechnician card) {
        super(card);
    }

    @Override
    public QuinjetTechnician copy() {
        return new QuinjetTechnician(this);
    }
}

class QuinjetTechnicianManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new QuinjetTechnicianConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to activate power-up abilities";
    }
  }

class QuinjetTechnicianConditionalMana extends ConditionalMana {

    QuinjetTechnicianConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to activate power-up abilities";
        addCondition(new QuinjetTechnicianManaCondition());
    }
}

class QuinjetTechnicianManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        return source instanceof PowerUpAbility
            && !source.isActivated()
            && source.isActivatedAbility();
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        return apply(game, source);
    }
}
