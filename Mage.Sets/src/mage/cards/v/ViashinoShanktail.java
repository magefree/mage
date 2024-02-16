
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.BloodrushAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class ViashinoShanktail extends CardImpl {

    public ViashinoShanktail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Bloodrush - 2{R}, Discard Viashino Shanktail: Target attacking creature gets +3/+1 and gains first strike until end of turn.
        Ability ability = new BloodrushAbility("{2}{R}", new BoostTargetEffect(3,1, Duration.EndOfTurn)
                .setText("target attacking creature gets +3/+1"));
        ability.addEffect(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn)
                .setText("and gains first strike until end of turn"));
        this.addAbility(ability);


    }

    private ViashinoShanktail(final ViashinoShanktail card) {
        super(card);
    }

    @Override
    public ViashinoShanktail copy() {
        return new ViashinoShanktail(this);
    }
}
