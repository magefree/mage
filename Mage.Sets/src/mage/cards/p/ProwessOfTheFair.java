package mage.cards.p;

import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.ElfWarriorToken;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class ProwessOfTheFair extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another nontoken Elf");

    static {
        filter.add(SubType.ELF.getPredicate());
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public ProwessOfTheFair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.ENCHANTMENT}, "{1}{B}");
        this.subtype.add(SubType.ELF);

        // Whenever another nontoken Elf is put into your graveyard from the battlefield, you may create a 1/1 green Elf Warrior creature token.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(new CreateTokenEffect(new ElfWarriorToken()),
                true, filter, false, true));
    }

    private ProwessOfTheFair(final ProwessOfTheFair card) {
        super(card);
    }

    @Override
    public ProwessOfTheFair copy() {
        return new ProwessOfTheFair(this);
    }
}
