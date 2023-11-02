
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author L_J
 */
public final class MishrasGroundbreaker extends CardImpl {

    public MishrasGroundbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {tap}, Sacrifice Mishra's Groundbreaker: Target land becomes a 3/3 artifact creature that's still a land. (This effect lasts indefinitely.)
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureTargetEffect(new MishrasGroundbreakerToken(), false, true, Duration.Custom), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private MishrasGroundbreaker(final MishrasGroundbreaker card) {
        super(card);
    }

    @Override
    public MishrasGroundbreaker copy() {
        return new MishrasGroundbreaker(this);
    }

}

class MishrasGroundbreakerToken extends TokenImpl {

    public MishrasGroundbreakerToken() {
        super("", "3/3 artifact creature");
        this.cardType.add(CardType.ARTIFACT);
        this.cardType.add(CardType.CREATURE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
    }
    private MishrasGroundbreakerToken(final MishrasGroundbreakerToken token) {
        super(token);
    }

    public MishrasGroundbreakerToken copy() {
        return new MishrasGroundbreakerToken(this);
    }
}
