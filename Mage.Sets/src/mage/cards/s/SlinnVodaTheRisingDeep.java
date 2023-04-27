
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author TheElk801
 */
public final class SlinnVodaTheRisingDeep extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.not(
                Predicates.or(
                        SubType.KRAKEN.getPredicate(),
                        SubType.LEVIATHAN.getPredicate(),
                        SubType.OCTOPUS.getPredicate(),
                        SubType.MERFOLK.getPredicate(),
                        SubType.SERPENT.getPredicate())
        ));
    }

    public SlinnVodaTheRisingDeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.LEVIATHAN);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Kicker {1}{U}
        this.addAbility(new KickerAbility("{1}{U}"));

        // When Slinn Voda, the Rising Deep enters the battlefield, if it was kicked, return all creatures to their owners' hands except for Merfolk, Krakens, Leviathans, Octopuses, and Serpents.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ReturnToHandFromBattlefieldAllEffect(filter)),
                KickedCondition.ONCE,
                "when {this} enters the battlefield, if it was kicked, "
                + "return all creatures to their owners' hands except for "
                + "Merfolk, Krakens, Leviathans, Octopuses, and Serpents."
        ));
    }

    private SlinnVodaTheRisingDeep(final SlinnVodaTheRisingDeep card) {
        super(card);
    }

    @Override
    public SlinnVodaTheRisingDeep copy() {
        return new SlinnVodaTheRisingDeep(this);
    }
}
