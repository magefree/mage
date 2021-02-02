
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;

/**
 *
 * @author LevelX2
 */
public final class SultaiFlayer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature you control with toughness 4 or greater");

    static {
        filter.add(new ToughnessPredicate(ComparisonType.MORE_THAN, 3));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public SultaiFlayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever a creature you control with toughness 4 or greater dies, you gain 4 life.
        this.addAbility(new DiesCreatureTriggeredAbility(new GainLifeEffect(4), false, filter));

    }

    private SultaiFlayer(final SultaiFlayer card) {
        super(card);
    }

    @Override
    public SultaiFlayer copy() {
        return new SultaiFlayer(this);
    }
}
