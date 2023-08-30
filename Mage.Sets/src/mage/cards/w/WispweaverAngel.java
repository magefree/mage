package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class WispweaverAngel extends CardImpl {

    public WispweaverAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Wispweaver Angel enters the battlefield, you may exile another target creature you control, then return that card to the battlefield under its owner's control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileThenReturnTargetEffect(false, true), true);
        ability.addTarget(new TargetControlledCreaturePermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private WispweaverAngel(final WispweaverAngel card) {
        super(card);
    }

    @Override
    public WispweaverAngel copy() {
        return new WispweaverAngel(this);
    }
}
