
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class MinotaurSkullcleaver extends CardImpl {

    public MinotaurSkullcleaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.BERSERKER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // When Minotaur Skullcleaver enters the battlefield, it gets +2/+0 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BoostSourceEffect(2,0, Duration.EndOfTurn)));
    }

    private MinotaurSkullcleaver(final MinotaurSkullcleaver card) {
        super(card);
    }

    @Override
    public MinotaurSkullcleaver copy() {
        return new MinotaurSkullcleaver(this);
    }
}
