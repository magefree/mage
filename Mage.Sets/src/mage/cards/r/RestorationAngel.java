package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author noxx
 *
 */
public final class RestorationAngel extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("non-Angel creature you control");

    static {
        filter.add(Predicates.not(SubType.ANGEL.getPredicate()));
    }

    public RestorationAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.addAbility(FlashAbility.getInstance());
        this.addAbility(FlyingAbility.getInstance());

        // When Restoration Angel enters the battlefield, you may exile target non-Angel creature you control, then return that card to the battlefield under your control
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileThenReturnTargetEffect(true, true), true);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private RestorationAngel(final RestorationAngel card) {
        super(card);
    }

    @Override
    public RestorationAngel copy() {
        return new RestorationAngel(this);
    }
}
