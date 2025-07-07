package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EnduringGlimmerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnduringInnocence extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("one or more other creatures you control with power 2 or less");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public EnduringInnocence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.SHEEP);
        this.subtype.add(SubType.GLIMMER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever one or more other creatures you control with power 2 or less enter, draw a card. This ability triggers only once each turn.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter
        ).setTriggersLimitEachTurn(1));

        // When Enduring Innocence dies, if it was a creature, return it to the battlefield under its owner's control. It's an enchantment.
        this.addAbility(new EnduringGlimmerTriggeredAbility());
    }

    private EnduringInnocence(final EnduringInnocence card) {
        super(card);
    }

    @Override
    public EnduringInnocence copy() {
        return new EnduringInnocence(this);
    }
}
