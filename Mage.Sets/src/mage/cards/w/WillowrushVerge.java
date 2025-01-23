package mage.cards.w;

import mage.Mana;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.mana.ActivateIfConditionManaAbility;
import mage.abilities.mana.BlueManaAbility;
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
public final class WillowrushVerge extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("you control a Forest or an Island");

    static {
        filter.add(Predicates.or(
                SubType.FOREST.getPredicate(),
                SubType.ISLAND.getPredicate()
        ));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition, "You control a Forest or an Island");

    public WillowrushVerge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // {T}: Add {G}. Activate only if you control a Forest or an Island.
        this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD, new BasicManaEffect(Mana.GreenMana(1)), new TapSourceCost(), condition
        ).addHint(hint));
    }

    private WillowrushVerge(final WillowrushVerge card) {
        super(card);
    }

    @Override
    public WillowrushVerge copy() {
        return new WillowrushVerge(this);
    }
}
