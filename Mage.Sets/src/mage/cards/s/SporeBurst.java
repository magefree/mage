
package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author North
 */
public final class SporeBurst extends CardImpl {

    public SporeBurst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");


        // Domain - Create a 1/1 green Saproling creature token for each basic land type among lands you control.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SaprolingToken(), new DomainValue()));
        this.getSpellAbility().addHint(DomainHint.instance);
        this.getSpellAbility().setAbilityWord(AbilityWord.DOMAIN);
    }

    private SporeBurst(final SporeBurst card) {
        super(card);
    }

    @Override
    public SporeBurst copy() {
        return new SporeBurst(this);
    }
}
