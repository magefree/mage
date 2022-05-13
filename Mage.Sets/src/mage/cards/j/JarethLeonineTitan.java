
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainProtectionFromColorSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

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
        this.addAbility(new BlocksSourceTriggeredAbility(
                new BoostSourceEffect(7, 7, Duration.EndOfTurn, "it")
        ));
        // {W}: Jareth gains protection from the color of your choice until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new GainProtectionFromColorSourceEffect(Duration.EndOfTurn),
                new ColoredManaCost(ColoredManaSymbol.W)
        ));
    }

    private JarethLeonineTitan(final JarethLeonineTitan card) {
        super(card);
    }

    @Override
    public JarethLeonineTitan copy() {
        return new JarethLeonineTitan(this);
    }
}
