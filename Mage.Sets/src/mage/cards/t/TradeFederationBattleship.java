
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.SpaceflightAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.DroidToken;

/**
 *
 * @author Styxo
 */
public final class TradeFederationBattleship extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Droid creatures");

    static {
        filter.add(SubType.DROID.getPredicate());
    }

    public TradeFederationBattleship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{W}{U}{B}");
        this.subtype.add(SubType.STARSHIP);
        this.power = new MageInt(0);
        this.toughness = new MageInt(6);

        // Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());

        // Droid creatures you control get +1/+1
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, false)));

        // {T}: Create two 1/1 colorless Droid artifact token cretures.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new DroidToken(), 2), new TapSourceCost()));
    }

    private TradeFederationBattleship(final TradeFederationBattleship card) {
        super(card);
    }

    @Override
    public TradeFederationBattleship copy() {
        return new TradeFederationBattleship(this);
    }
}
