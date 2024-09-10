
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.BloodrushAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author Plopman
 */
public final class WastelandViper extends CardImpl {

    public WastelandViper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.SNAKE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // Bloodrush - {G}, Discard Wasteland Viper: Target attacking creature gets +1/+2 and gains deathtouch until end of turn.
        Ability ability = new BloodrushAbility("{G}", new BoostTargetEffect(1, 2, Duration.EndOfTurn)
                .setText("target attacking creature gets +1/+2"));
        ability.addEffect(new GainAbilityTargetEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn)
                .setText("and gains deathtouch until end of turn"));
        this.addAbility(ability);
    }

    private WastelandViper(final WastelandViper card) {
        super(card);
    }

    @Override
    public WastelandViper copy() {
        return new WastelandViper(this);
    }
}
