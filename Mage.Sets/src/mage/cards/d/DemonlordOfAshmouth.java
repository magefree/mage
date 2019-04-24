
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ExileSourceUnlessPaysEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 * @author noxx
 */
public final class DemonlordOfAshmouth extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(" another creature");

    static {
        filter.add(new AnotherPredicate());
    }

    public DemonlordOfAshmouth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());

        // When Demonlord of Ashmouth enters the battlefield, exile it unless you sacrifice another creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ExileSourceUnlessPaysEffect(new SacrificeTargetCost(new TargetControlledPermanent(filter)))));

        this.addAbility(new UndyingAbility());
    }

    public DemonlordOfAshmouth(final DemonlordOfAshmouth card) {
        super(card);
    }

    @Override
    public DemonlordOfAshmouth copy() {
        return new DemonlordOfAshmouth(this);
    }
}
