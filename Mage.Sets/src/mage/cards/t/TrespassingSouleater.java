
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.PhyrexianManaCost;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class TrespassingSouleater extends CardImpl {

    public TrespassingSouleater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {U/P}: Trespassing Souleater can't be blocked this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CantBeBlockedSourceEffect(Duration.EndOfTurn),
                new PhyrexianManaCost(ColoredManaSymbol.U)));
    }

    private TrespassingSouleater(final TrespassingSouleater card) {
        super(card);
    }

    @Override
    public TrespassingSouleater copy() {
        return new TrespassingSouleater(this);
    }
}
