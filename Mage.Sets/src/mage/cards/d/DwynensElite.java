package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.ElfWarriorToken;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DwynensElite extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ELF, "you control another Elf");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public DwynensElite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When this creature enters, if you control another Elf, create a 1/1 green Elf Warrior creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ElfWarriorToken())).withInterveningIf(condition));
    }

    private DwynensElite(final DwynensElite card) {
        super(card);
    }

    @Override
    public DwynensElite copy() {
        return new DwynensElite(this);
    }
}
