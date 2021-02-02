
package mage.cards.d;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.SoldierToken;

/**
 *
 * @author LevelX2
 */
public final class DeployToTheFront extends CardImpl {

    public DeployToTheFront(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{W}{W}");

        // create X 1/1 white Soldier creature tokens, where X is the number of creatures on the battlefield.
        Effect effect = new CreateTokenEffect(new SoldierToken(), new PermanentsOnBattlefieldCount(new FilterCreaturePermanent("the number of creatures on the battlefield")));
        effect.setText("create X 1/1 white Soldier creature tokens, where X is the number of creatures on the battlefield");
        this.getSpellAbility().addEffect(effect);
    }

    private DeployToTheFront(final DeployToTheFront card) {
        super(card);
    }

    @Override
    public DeployToTheFront copy() {
        return new DeployToTheFront(this);
    }
}
