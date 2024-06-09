

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;

/**
 *
 * @author nantuko
 */
public final class SunblastAngel extends CardImpl {

    private static final FilterPermanent tappedFilter = new FilterCreaturePermanent("tapped creatures");

    static {
        tappedFilter.add(TappedPredicate.TAPPED);
    }

    public SunblastAngel (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        addAbility(FlyingAbility.getInstance());
        addAbility(new EntersBattlefieldTriggeredAbility(new DestroyAllEffect(tappedFilter), false));
    }

    private SunblastAngel(final SunblastAngel card) {
        super(card);
    }

    @Override
    public SunblastAngel copy() {
        return new SunblastAngel(this);
    }

}
