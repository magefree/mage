package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.PowerPredicate;

/**
 *
 * @author weirddan455
 */
public final class WelcomingVampire extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("one or more other creatures with power 2 or less");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public WelcomingVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever one or more other creatures with power 2 or less enter the battlefield under your control, draw a card. This ability triggers only once each turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new DrawCardSourceControllerEffect(1), filter).setTriggersOnceEachTurn(true));
    }

    private WelcomingVampire(final WelcomingVampire card) {
        super(card);
    }

    @Override
    public WelcomingVampire copy() {
        return new WelcomingVampire(this);
    }
}
