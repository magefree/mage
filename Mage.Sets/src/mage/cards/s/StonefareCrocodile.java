
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class StonefareCrocodile extends CardImpl {

    public StonefareCrocodile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.CROCODILE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {2}{B}: Stonefare Crocodile gains lifelink until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{2}{B}")));
    }

    private StonefareCrocodile(final StonefareCrocodile card) {
        super(card);
    }

    @Override
    public StonefareCrocodile copy() {
        return new StonefareCrocodile(this);
    }
}
