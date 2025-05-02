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
public final class BleachboneVerge extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("you control a Plains or a Swamp");

    static {
        filter.add(Predicates.or(
                SubType.PLAINS.getPredicate(),
                SubType.SWAMP.getPredicate()
        ));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition, "You control a Plains or a Swamp");

    public BleachboneVerge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {T}: Add {W}. Activate only if you control a Plains or a Swamp.
        this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD, new BasicManaEffect(Mana.WhiteMana(1)), new TapSourceCost(), condition
        ).addHint(hint));
    }

    private BleachboneVerge(final BleachboneVerge card) {
        super(card);
    }

    @Override
    public BleachboneVerge copy() {
        return new BleachboneVerge(this);
    }
}
