package mage.cards.r;

import mage.abilities.common.ActivateAbilityTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.FilterStackObject;
import mage.filter.common.FilterActivatedOrTriggeredAbility;
import mage.filter.predicate.other.NotManaAbilityPredicate;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RingsOfBrighthearth extends CardImpl {
    private static final FilterStackObject filter = new FilterActivatedOrTriggeredAbility("an ability, if it isn't a mana ability");

    static {
        filter.add(NotManaAbilityPredicate.instance);
    }

    public RingsOfBrighthearth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever you activate an ability, if it isn't a mana ability, you may pay {2}. If you do, copy that ability. You may choose new targets for the copy.
        this.addAbility(new ActivateAbilityTriggeredAbility(new DoIfCostPaid(new CopyStackObjectEffect(), new ManaCostsImpl<>("{2}")), filter, SetTargetPointer.SPELL));
    }

    private RingsOfBrighthearth(final RingsOfBrighthearth card) {
        super(card);
    }

    @Override
    public RingsOfBrighthearth copy() {
        return new RingsOfBrighthearth(this);
    }
}
