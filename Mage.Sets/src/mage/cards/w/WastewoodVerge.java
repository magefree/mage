package mage.cards.w;

import mage.Mana;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.mana.ActivateIfConditionManaAbility;
import mage.abilities.mana.GreenManaAbility;
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
public final class WastewoodVerge extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("you control a Swamp or a Forest");

    static {
        filter.add(Predicates.or(
                SubType.SWAMP.getPredicate(),
                SubType.FOREST.getPredicate()
        ));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition, "You control a Swamp or a Forest");

    public WastewoodVerge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {T}: Add {B}. Activate only if you control a Swamp or a Forest.
        this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD, new BasicManaEffect(Mana.BlackMana(1)), new TapSourceCost(), condition
        ).addHint(hint));
    }

    private WastewoodVerge(final WastewoodVerge card) {
        super(card);
    }

    @Override
    public WastewoodVerge copy() {
        return new WastewoodVerge(this);
    }
}
