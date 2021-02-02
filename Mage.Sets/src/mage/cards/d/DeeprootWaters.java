
package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.game.permanent.token.MerfolkHexproofToken;

/**
 *
 * @author TacomenX
 */
public final class DeeprootWaters extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell("a Merfolk spell");

    static {
        filter.add(SubType.MERFOLK.getPredicate());
    }

    public DeeprootWaters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        

        // Whenever you cast a Merfolk spell, create a 1/1 blue Merfolk creature token with hexproof.
        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new MerfolkHexproofToken()), filter, false));
    }

    private DeeprootWaters(final DeeprootWaters card) {
        super(card);
    }

    @Override
    public DeeprootWaters copy() {
        return new DeeprootWaters(this);
    }
}
