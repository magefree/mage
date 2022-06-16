

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author Loki
 */
public final class SelesnyaGuildmage extends CardImpl {

    public SelesnyaGuildmage (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G/W}{G/W}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WIZARD);


        this.power = new MageInt(2);    
        this.toughness = new MageInt(2);
        
        // {3}{G}: Create a 1/1 green Saproling creature token.        
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SaprolingToken()), new ManaCostsImpl<>("{3}{G}")));
        // {3}{W}: Creatures you control get +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{3}{W}")));
    }

    public SelesnyaGuildmage (final SelesnyaGuildmage card) {
        super(card);
    }

    @Override
    public SelesnyaGuildmage copy() {
        return new SelesnyaGuildmage(this);
    }

}
