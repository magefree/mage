
package mage.cards.e;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.ElfToken;

/**
 *
 * @author Loki
 */
public final class ElvishPromenade extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Elf you control");

    static {
        filter.add(new SubtypePredicate(SubType.ELF));
    }

    public ElvishPromenade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.SORCERY},"{3}{G}");
        this.subtype.add(SubType.ELF);

        this.getSpellAbility().addEffect(new CreateTokenEffect(new ElfToken(), new PermanentsOnBattlefieldCount(filter)));
    }

    public ElvishPromenade(final ElvishPromenade card) {
        super(card);
    }

    @Override
    public ElvishPromenade copy() {
        return new ElvishPromenade(this);
    }
}
