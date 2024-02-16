
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PhaseOutSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.LoseAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class MistDragon extends CardImpl {

    public MistDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {0}: Mist Dragon gains flying. <i>This effect lasts indefinitely</i>
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield), new GenericManaCost(0)));
        
        // {0}: Mist Dragon loses flying. <i>This effect lasts indefinitely</i>
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new LoseAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield), new GenericManaCost(0)));
        
        // {3}{U}{U}: Mist Dragon phases out.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new PhaseOutSourceEffect(), new ManaCostsImpl<>("{3}{U}{U}")));
    }

    private MistDragon(final MistDragon card) {
        super(card);
    }

    @Override
    public MistDragon copy() {
        return new MistDragon(this);
    }
}
