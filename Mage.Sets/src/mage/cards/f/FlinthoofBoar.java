
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceWhileControlsEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
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
public final class FlinthoofBoar extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Mountain");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public FlinthoofBoar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.BOAR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flinthoof Boar gets +1/+1 as long as you control a Mountain.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceWhileControlsEffect(filter, 1, 1)));

        // {R}: Flinthoof Boar gains haste until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.EndOfTurn),
                new ManaCostsImpl<>("{R}")));
    }

    private FlinthoofBoar(final FlinthoofBoar card) {
        super(card);
    }

    @Override
    public FlinthoofBoar copy() {
        return new FlinthoofBoar(this);
    }
}
