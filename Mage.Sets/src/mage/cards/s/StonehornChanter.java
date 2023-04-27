
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author jeffwadsworth
 */
public final class StonehornChanter extends CardImpl {

    public StonehornChanter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}");
        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {5}{W}: Stonehorn Chanter gains vigilance and lifelink until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{5}{W}"));
        ability.addEffect(new GainAbilitySourceEffect(LifelinkAbility.getInstance()));
        this.addAbility(ability);

    }

    private StonehornChanter(final StonehornChanter card) {
        super(card);
    }

    @Override
    public StonehornChanter copy() {
        return new StonehornChanter(this);
    }
}
