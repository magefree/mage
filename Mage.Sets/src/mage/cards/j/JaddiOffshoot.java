
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class JaddiOffshoot extends CardImpl {

    public JaddiOffshoot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.PLANT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // <i>Landfall</i> &mdash; Whenever a land enters the battlefield under your control, you gain 1 life.
        this.addAbility(new LandfallAbility(new GainLifeEffect(1), false));
    }

    private JaddiOffshoot(final JaddiOffshoot card) {
        super(card);
    }

    @Override
    public JaddiOffshoot copy() {
        return new JaddiOffshoot(this);
    }
}
