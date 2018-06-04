
package mage.cards.p;

import java.util.UUID;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.ElfToken;

/**
 *
 * @author LoneFox
 */
public final class ProwessOfTheFair extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another nontoken Elf");

    static {
        filter.add(new SubtypePredicate(SubType.ELF));
        filter.add(new AnotherPredicate());
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public ProwessOfTheFair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.ENCHANTMENT},"{1}{B}");
        this.subtype.add(SubType.ELF);

        // Whenever another nontoken Elf is put into your graveyard from the battlefield, you may create a 1/1 green Elf Warrior creature token.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(new CreateTokenEffect(new ElfToken()),
            true, filter, false, true));
    }

    public ProwessOfTheFair(final ProwessOfTheFair card) {
        super(card);
    }

    @Override
    public ProwessOfTheFair copy() {
        return new ProwessOfTheFair(this);
    }
}
