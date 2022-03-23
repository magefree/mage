
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapAllEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LevelX2
 */
public final class IvoryGiant extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonwhite creatures");
    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.WHITE)));
    }

    public IvoryGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{W}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Ivory Giant enters the battlefield, tap all nonwhite creatures.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TapAllEffect(filter)));
        // Suspend 5-{W}
        this.addAbility(new SuspendAbility(5, new ManaCostsImpl("{W}"), this));
    }

    private IvoryGiant(final IvoryGiant card) {
        super(card);
    }

    @Override
    public IvoryGiant copy() {
        return new IvoryGiant(this);
    }
}
