
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceWhileControlsEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author North
 */
public final class ArcticAven extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Plains");

    static {
        filter.add(SubType.PLAINS.getPredicate());
    }

    public ArcticAven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Arctic Aven gets +1/+1 as long as you control a Plains.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceWhileControlsEffect(filter, 1, 1)));
        // {W}: Arctic Aven gains lifelink until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn),
                new ManaCostsImpl<>("{W}")));
    }

    private ArcticAven(final ArcticAven card) {
        super(card);
    }

    @Override
    public ArcticAven copy() {
        return new ArcticAven(this);
    }
}
