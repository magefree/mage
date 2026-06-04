package mage.cards.b;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.mana.AddManaInAnyCombinationEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;

/**
 *
 * @author muz
 */
public final class BaxterBuilding extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("you control a creature with toughness 4 or greater");

    static {
        filter.add(new ToughnessPredicate(ComparisonType.OR_GREATER, 4));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition, "You control a creature with toughness 4 or greater");


    public BaxterBuilding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {4}, {T}: Add four mana in any combination of colors.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaInAnyCombinationEffect(4), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {4}, {T}: Draw a card. Activate only if you control a creature with toughness 4 or greater.
        this.addAbility(new ActivateIfConditionActivatedAbility(
            Zone.BATTLEFIELD,
            new DrawCardSourceControllerEffect(1),
            new TapSourceCost(),
            condition
        ).addHint(hint));
    }

    private BaxterBuilding(final BaxterBuilding card) {
        super(card);
    }

    @Override
    public BaxterBuilding copy() {
        return new BaxterBuilding(this);
    }
}
