
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.DelveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author LevelX2
 */
public final class SetAdrift extends CardImpl {

    public SetAdrift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{U}");


        // Delve
        this.addAbility(new DelveAbility());
        // Put target nonland permanent on top of its owner's library
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private SetAdrift(final SetAdrift card) {
        super(card);
    }

    @Override
    public SetAdrift copy() {
        return new SetAdrift(this);
    }
}
