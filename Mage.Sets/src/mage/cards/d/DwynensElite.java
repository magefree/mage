
package mage.cards.d;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.permanent.token.ElfToken;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class DwynensElite extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another Elf");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new SubtypePredicate(SubType.ELF));
    }

    public DwynensElite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Dwynen's Elite enters the battlefield, if you control another Elf, create a 1/1 green Elf Warrior creature token.
        TriggeredAbility triggeredAbility = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ElfToken()));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                triggeredAbility,
                new PermanentsOnTheBattlefieldCondition(filter),
                "When {this} enters the battlefield, if you control another Elf, create a 1/1 green Elf Warrior creature token."));
    }

    public DwynensElite(final DwynensElite card) {
        super(card);
    }

    @Override
    public DwynensElite copy() {
        return new DwynensElite(this);
    }
}
