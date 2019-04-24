
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class BreachingHippocamp extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another target creature you control");
    static {
        filter.add(new AnotherPredicate());
    }

    public BreachingHippocamp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.HORSE, SubType.FISH);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // When Breaching Hippocamp enters the battlefield, untap another target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new UntapTargetEffect(), false);
        ability.addTarget(new TargetControlledCreaturePermanent(1,1,filter, false));
        this.addAbility(ability);
    }

    public BreachingHippocamp(final BreachingHippocamp card) {
        super(card);
    }

    @Override
    public BreachingHippocamp copy() {
        return new BreachingHippocamp(this);
    }
}
