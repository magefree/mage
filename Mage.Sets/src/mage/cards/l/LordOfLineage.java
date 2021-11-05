
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.VampireToken;

/**
 *
 * @author Loki
 */
public final class LordOfLineage extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Other Vampire creatures");

    static {
        filter.add(SubType.VAMPIRE.getPredicate());
    }

    public LordOfLineage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.VAMPIRE);

        this.color.setBlack(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // this card is the second face of double-faced card Bloodline Keeper
        this.nightCard = true;

        this.addAbility(FlyingAbility.getInstance());
        // Other Vampire creatures you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield, filter, true)));
        // {tap}: Create a 2/2 black Vampire creature token with flying.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new VampireToken()), new TapSourceCost()));
    }

    private LordOfLineage(final LordOfLineage card) {
        super(card);
    }

    @Override
    public LordOfLineage copy() {
        return new LordOfLineage(this);
    }
}
