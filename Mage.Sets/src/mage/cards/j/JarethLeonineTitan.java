
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainProtectionFromColorSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class JarethLeonineTitan extends CardImpl {

    public JarethLeonineTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        // Whenever Jareth, Leonine Titan blocks, it gets +7/+7 until end of turn.
        this.addAbility(new BlocksSourceTriggeredAbility(new BoostSourceEffect(7,7,Duration.EndOfTurn), false));
        // {W}: Jareth gains protection from the color of your choice until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainProtectionFromColorSourceEffect(Duration.EndOfTurn), new ManaCostsImpl("{W}"));
        this.addAbility(ability);

    }

    private JarethLeonineTitan(final JarethLeonineTitan card) {
        super(card);
    }

    @Override
    public JarethLeonineTitan copy() {
        return new JarethLeonineTitan(this);
    }
}
