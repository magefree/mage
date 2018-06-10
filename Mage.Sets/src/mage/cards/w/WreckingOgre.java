
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.BloodrushAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author Plopman
 */
public final class WreckingOgre extends CardImpl {

    public WreckingOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());
        // Bloodrush - {3}{R}{R}, Discard Wrecking Ogre: Target attacking creature gets +3/+3 and gains double strike until end of turn.
        Ability ability = new BloodrushAbility("{3}{R}{R}", new BoostTargetEffect(3, 3, Duration.EndOfTurn));
        ability.addEffect(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn));
        this.addAbility(ability);
    }

    public WreckingOgre(final WreckingOgre card) {
        super(card);
    }

    @Override
    public WreckingOgre copy() {
        return new WreckingOgre(this);
    }
}
