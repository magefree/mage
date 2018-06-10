
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;

/**
 *
 * @author fireshoes
 */
public final class BorderlandBehemoth extends CardImpl {

    public BorderlandBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}{R}");
        this.subtype.add(SubType.GIANT, SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Borderland Behemoth gets +4/+4 for each other Giant you control.
        FilterCreaturePermanent filter = new FilterCreaturePermanent("other Giant you control");
        filter.add(new SubtypePredicate(SubType.GIANT));
        filter.add(Predicates.not(new PermanentIdPredicate(this.getId())));
        filter.add(new ControllerPredicate(TargetController.YOU));
        DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, 4);
        Effect effect = new BoostSourceEffect(xValue, xValue, Duration.WhileOnBattlefield, false);
        effect.setText("{this} gets +4/+4 for each other Giant you control");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public BorderlandBehemoth(final BorderlandBehemoth card) {
        super(card);
    }

    @Override
    public BorderlandBehemoth copy() {
        return new BorderlandBehemoth(this);
    }
}
