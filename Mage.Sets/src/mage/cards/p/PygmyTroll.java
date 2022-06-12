
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
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
public final class PygmyTroll extends CardImpl {

    public PygmyTroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.TROLL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Pygmy Troll becomes blocked by a creature, Pygmy Troll gets +1/+1 until end of turn.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), false));
        
        // {G}: Regenerate Pygmy Troll.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{G}")));
    }

    private PygmyTroll(final PygmyTroll card) {
        super(card);
    }

    @Override
    public PygmyTroll copy() {
        return new PygmyTroll(this);
    }
}
