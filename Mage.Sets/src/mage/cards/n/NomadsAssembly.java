package mage.cards.n;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.KorSoldierToken;

/**
 *
 * @author North
 */
public final class NomadsAssembly extends CardImpl {

    public NomadsAssembly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{W}{W}");

        // Create a 1/1 white Kor Soldier creature token for each creature you control.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new KorSoldierToken(), new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE)));
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
