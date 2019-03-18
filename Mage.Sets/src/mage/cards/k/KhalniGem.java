

package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public final class KhalniGem extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent();

    public KhalniGem (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // When Khalni Gem enters the battlefield, return two lands you control to their owner's hand.
        Ability etbAbility = new EntersBattlefieldTriggeredAbility(new KhalniGemReturnToHandTargetEffect());
        Target target = new TargetControlledPermanent(2, 2, filter, false);
        etbAbility.addTarget(target);
        this.addAbility(etbAbility);
        // {tap}: Add two mana of any one color.
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(2), new TapSourceCost());
        this.addAbility(ability);
    }

    public KhalniGem (final KhalniGem card) {
        super(card);
    }

    @Override
    public KhalniGem copy() {
        return new KhalniGem(this);
    }

}

class KhalniGemReturnToHandTargetEffect extends OneShotEffect {

    private static final String effectText = "return two lands you control to their owner's hand";

    KhalniGemReturnToHandTargetEffect ( ) {
        super(Outcome.ReturnToHand);
        staticText = effectText;
    }

    KhalniGemReturnToHandTargetEffect ( KhalniGemReturnToHandTargetEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for ( UUID target : targetPointer.getTargets(game, source) ) {
            Permanent permanent = game.getPermanent(target);
            if ( permanent != null ) {
                permanent.moveToZone(Zone.HAND, source.getSourceId(), game, true);
            }
        }
        return true;
    }

    @Override
    public KhalniGemReturnToHandTargetEffect copy() {
        return new KhalniGemReturnToHandTargetEffect(this);
    }

}
