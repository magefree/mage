package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
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

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class WaterspoutElemental extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public WaterspoutElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Kicker {U}
        this.addAbility(new KickerAbility("{U}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Waterspout Elemental enters the battlefield, if it was kicked, return all other creatures to their owners' hands and you skip your next turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandFromBattlefieldAllEffect(filter)).withInterveningIf(KickedCondition.ONCE);
        ability.addEffect(new SkipNextTurnSourceEffect().concatBy("and"));
        this.addAbility(ability);
    }

    private WaterspoutElemental(final WaterspoutElemental card) {
        super(card);
    }

    @Override
    public WaterspoutElemental copy() {
        return new WaterspoutElemental(this);
    }
}
