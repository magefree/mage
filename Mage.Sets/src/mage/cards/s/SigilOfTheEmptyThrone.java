

package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.game.permanent.token.AngelToken;

/**
 *
 * @author Loki, North
 */
public final class SigilOfTheEmptyThrone extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an enchantment spell");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    public SigilOfTheEmptyThrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}{W}");


        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new AngelToken()), filter, false));
    }

    private SigilOfTheEmptyThrone(final SigilOfTheEmptyThrone card) {
        super(card);
    }

    @Override
    public SigilOfTheEmptyThrone copy() {
        return new SigilOfTheEmptyThrone(this);
    }

}
