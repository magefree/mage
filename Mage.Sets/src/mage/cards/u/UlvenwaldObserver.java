
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
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
 * @author fireshoes
 */
public final class UlvenwaldObserver extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature you control with toughness 4 or greater");

    static {
        filter.add(new ToughnessPredicate(ComparisonType.MORE_THAN, 3));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public UlvenwaldObserver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever a creature you control with toughness 4 or greater dies, draw a card.
        this.addAbility(new DiesCreatureTriggeredAbility(new DrawCardSourceControllerEffect(1), false, filter));
    }

    private UlvenwaldObserver(final UlvenwaldObserver card) {
        super(card);
    }

    @Override
    public UlvenwaldObserver copy() {
        return new UlvenwaldObserver(this);
    }
}
