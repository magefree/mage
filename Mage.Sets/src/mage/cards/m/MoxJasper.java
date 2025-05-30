package mage.cards.m;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.mana.ActivateIfConditionManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoxJasper extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.DRAGON, "you control a Dragon")
    );
    private static final Hint hint = new ConditionHint(condition, "You control a Dragon");

    public MoxJasper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");

        this.supertype.add(SuperType.LEGENDARY);

        // {T}: Add one mana of any color. Activate only if you control a Dragon.
        this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(1),
                new TapSourceCost(), condition
        ).addHint(hint));
    }

    private MoxJasper(final MoxJasper card) {
        super(card);
    }

    @Override
    public MoxJasper copy() {
        return new MoxJasper(this);
    }
}
