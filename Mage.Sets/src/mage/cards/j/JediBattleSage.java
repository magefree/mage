
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.MeditateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class JediBattleSage extends CardImpl {

    public JediBattleSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.SULLUSTAN);
        this.subtype.add(SubType.JEDI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Jedi Battle Sage enters the battlefield, target creature gets +2/+2 until end of turn.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Meditate {1}{G}
        this.addAbility(new MeditateAbility(new ManaCostsImpl<>("{1}{G}")));
    }

    private JediBattleSage(final JediBattleSage card) {
        super(card);
    }

    @Override
    public JediBattleSage copy() {
        return new JediBattleSage(this);
    }
}
