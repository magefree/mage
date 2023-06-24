package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.costs.common.DiscardHandCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.combat.CombatGroup;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AmberGristleOMaul extends CardImpl {

    public AmberGristleOMaul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenver Amber Gristle O'Maul attacks, you may discard your hand. If you do, draw a card for each player you're attacking.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(AmberGristleOMaulValue.instance), new DiscardHandCost()
        )));

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private AmberGristleOMaul(final AmberGristleOMaul card) {
        super(card);
    }

    @Override
    public AmberGristleOMaul copy() {
        return new AmberGristleOMaul(this);
    }
}

enum AmberGristleOMaulValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getCombat()
                .getGroups()
                .stream()
                .filter(combatGroup -> combatGroup
                        .getAttackers()
                        .stream()
                        .map(game::getControllerId)
                        .anyMatch(sourceAbility::isControlledBy))
                .map(CombatGroup::getDefenderId)
                .filter(Objects::nonNull)
                .distinct()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .mapToInt(x -> 1)
                .sum();
    }

    @Override
    public AmberGristleOMaulValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "player you're attacking";
    }

    @Override
    public String toString() {
        return "1";
    }
}
