
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.ElfToken;

/**
 *
 * @author Loki
 */
public final class LysAlanaHuntmaster extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("Elf spell");

    static {
        filter.add(new SubtypePredicate(SubType.ELF));
    }

    public LysAlanaHuntmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new ElfToken()), filter, true));
    }

    public LysAlanaHuntmaster(final LysAlanaHuntmaster card) {
        super(card);
    }

    @Override
    public LysAlanaHuntmaster copy() {
        return new LysAlanaHuntmaster(this);
    }
}
