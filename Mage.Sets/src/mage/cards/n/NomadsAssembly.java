
package mage.cards.n;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.KorSoldierToken;

/**
 *
 * @author North
 */
public final class NomadsAssembly extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    public NomadsAssembly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{W}{W}");


        this.getSpellAbility().addEffect(new CreateTokenEffect(new KorSoldierToken(), new PermanentsOnBattlefieldCount(filter)));
        this.addAbility(new ReboundAbility());
    }

    private NomadsAssembly(final NomadsAssembly card) {
        super(card);
    }

    @Override
    public NomadsAssembly copy() {
        return new NomadsAssembly(this);
    }
}
