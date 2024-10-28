
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class JuzamDjinn extends CardImpl {

    public JuzamDjinn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.DJINN);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // At the beginning of your upkeep, Juzam Djinn deals 1 damage to you.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DamageControllerEffect(1)));
    }

    private JuzamDjinn(final JuzamDjinn card) {
        super(card);
    }

    @Override
    public JuzamDjinn copy() {
        return new JuzamDjinn(this);
    }
}
