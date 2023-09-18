
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author cbt33
 */
public final class LuminousGuardian extends CardImpl {

    public LuminousGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOMAD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // {W}: Luminous Guardian gets +0/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(0, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{W}")));
        // {2}: Luminous Guardian can block an additional creature this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CanBlockAdditionalCreatureEffect(Duration.EndOfTurn, 1), new ManaCostsImpl<>("{2}")));
    }

    private LuminousGuardian(final LuminousGuardian card) {
        super(card);
    }

    @Override
    public LuminousGuardian copy() {
        return new LuminousGuardian(this);
    }
}
