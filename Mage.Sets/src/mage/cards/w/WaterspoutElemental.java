
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.abilities.effects.common.turn.SkipNextTurnSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LoneFox
 */
public final class WaterspoutElemental extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public WaterspoutElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Kicker {U}
        this.addAbility(new KickerAbility("{U}"));
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Waterspout Elemental enters the battlefield, if it was kicked, return all other creatures to their owners' hands and you skip your next turn.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandFromBattlefieldAllEffect(filter));
        ability.addEffect(new SkipNextTurnSourceEffect());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, KickedCondition.ONCE,
            "When {this} enters the battlefield, if it was kicked, return all other creatures to their owners' hands and you skip your next turn."));
    }

    private WaterspoutElemental(final WaterspoutElemental card) {
        super(card);
    }

    @Override
    public WaterspoutElemental copy() {
        return new WaterspoutElemental(this);
    }
}
