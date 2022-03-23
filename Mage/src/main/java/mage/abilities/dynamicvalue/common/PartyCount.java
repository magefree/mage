package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;

import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public enum PartyCount implements DynamicValue {
    instance;
    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(Predicates.or(
                SubType.CLERIC.getPredicate(),
                SubType.ROGUE.getPredicate(),
                SubType.WARRIOR.getPredicate(),
                SubType.WIZARD.getPredicate()
        ));
    }

    private static final SubTypeAssignment subTypeAssigner = new SubTypeAssignment(
            SubType.CLERIC,
            SubType.ROGUE,
            SubType.WARRIOR,
            SubType.WIZARD
    );


    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Cards cards = new CardsImpl(game.getBattlefield().getActivePermanents(
                filter, sourceAbility.getControllerId(), sourceAbility, game
        ).stream().collect(Collectors.toSet()));
        return subTypeAssigner.getRoleCount(cards, game);
    }

    @Override
    public PartyCount copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "creature in your party. " + getReminder();
    }

    public static String getReminder() {
        return "<i>(Your party consists of up to one each of Cleric, Rogue, Warrior, and Wizard.)</i>";
    }

    @Override
    public String toString() {
        return "1";
    }
}
