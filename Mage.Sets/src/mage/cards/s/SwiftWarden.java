
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class SwiftWarden extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Merfolk you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new SubtypePredicate(SubType.MERFOLK));
    }

    public SwiftWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Swift Warden enters the battlefield, target Merfolk you control gains hexproof until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainAbilityTargetEffect(HexproofAbility.getInstance(), Duration.EndOfTurn));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    public SwiftWarden(final SwiftWarden card) {
        super(card);
    }

    @Override
    public SwiftWarden copy() {
        return new SwiftWarden(this);
    }
}
