package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceIsRingBearerCondition;
import mage.abilities.decorator.ConditionalRequirementEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneSourceEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FrodoBaggins extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("legendary creature");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public FrodoBaggins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever Frodo Baggins or another legendary creature enters the battlefield under your control, the Ring tempts you.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new TheRingTemptsYouEffect(), filter, false, true
        ));

        // As long as Frodo is your Ring-bearer, it must be blocked if able.
        this.addAbility(new SimpleStaticAbility(new ConditionalRequirementEffect(
                new MustBeBlockedByAtLeastOneSourceEffect(Duration.WhileOnBattlefield), SourceIsRingBearerCondition.instance,
                "as long as {this} is your Ring-bearer, it must be blocked if able"
        )));
    }

    private FrodoBaggins(final FrodoBaggins card) {
        super(card);
    }

    @Override
    public FrodoBaggins copy() {
        return new FrodoBaggins(this);
    }
}
