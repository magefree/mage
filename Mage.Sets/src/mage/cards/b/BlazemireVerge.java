package mage.cards.b;

import mage.Mana;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.mana.ActivateIfConditionManaAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlazemireVerge extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("you control a Swamp or a Mountain");

    static {
        filter.add(Predicates.or(
                SubType.SWAMP.getPredicate(),
                SubType.MOUNTAIN.getPredicate()
        ));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition, "You control a Swamp or a Mountain");

    public BlazemireVerge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {T}: Add {R}. Activate only if you control a Swamp or a Mountain.
        this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD, new BasicManaEffect(Mana.RedMana(1)), new TapSourceCost(), condition
        ).addHint(hint));
    }

    private BlazemireVerge(final BlazemireVerge card) {
        super(card);
    }

    @Override
    public BlazemireVerge copy() {
        return new BlazemireVerge(this);
    }
}
