package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.ExileAndGainLifeEqualPowerTargetEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Solitude extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("other creature");
    private static final FilterCard filter2 = new FilterCard("a white card from your hand");

    static {
        filter.add(AnotherPredicate.instance);
        filter2.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public Solitude(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.INCARNATION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Solitude enters the battlefield, exile up to one other target creature. That creature's controller gains life equal to its power.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileAndGainLifeEqualPowerTargetEffect()
                .setText("exile up to one other target creature. That creature's controller gains life equal to its power"));
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // Evoke—Exile a white card from your hand.
        this.addAbility(new EvokeAbility(new ExileFromHandCost(new TargetCardInHand(filter2))));
    }

    private Solitude(final Solitude card) {
        super(card);
    }

    @Override
    public Solitude copy() {
        return new Solitude(this);
    }
}
