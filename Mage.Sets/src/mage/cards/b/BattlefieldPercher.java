
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class BattlefieldPercher extends CardImpl {

    public BattlefieldPercher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Battlefield Percher can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());
        // {1}{B}: Battlefield Percher gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn),
            new ManaCostsImpl<>("{1}{B}")));
    }

    private BattlefieldPercher(final BattlefieldPercher card) {
        super(card);
    }

    @Override
    public BattlefieldPercher copy() {
        return new BattlefieldPercher(this);
    }
}
