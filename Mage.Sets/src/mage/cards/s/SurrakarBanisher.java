
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class SurrakarBanisher extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public SurrakarBanisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.SURRAKAR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), true);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private SurrakarBanisher(final SurrakarBanisher card) {
        super(card);
    }

    @Override
    public SurrakarBanisher copy() {
        return new SurrakarBanisher(this);
    }
}
