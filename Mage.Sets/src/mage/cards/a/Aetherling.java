
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileReturnBattlefieldOwnerNextEndStepSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class Aetherling extends CardImpl {

    public Aetherling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // {U}: Exile Aetherling. Return it to the battlefield under its owner's control at the beginning of the next end step.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileReturnBattlefieldOwnerNextEndStepSourceEffect(), new ManaCostsImpl("{U}")));
        // {U}: Aetherling can't be blocked this turn
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBeBlockedSourceEffect(Duration.EndOfTurn), new ManaCostsImpl("{U}")));
        // {1}: Aetherling gets +1/-1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, -1, Duration.EndOfTurn), new ManaCostsImpl("{1}")));
        // {1}: Aetherling gets -1/+1 until end of turn
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(-1, 1, Duration.EndOfTurn), new ManaCostsImpl("{1}")));
    }

    private Aetherling(final Aetherling card) {
        super(card);
    }

    @Override
    public Aetherling copy() {
        return new Aetherling(this);
    }

}
