
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.abilityword.KinshipAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class KithkinZephyrnaut extends CardImpl {

    public KithkinZephyrnaut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kinship - At the beginning of your upkeep, you may look at the top card of your library. If it shares a creature type with Kithkin Zephyrnaut, you may reveal it. 
        // If you do, Kithkin Zephyrnaut gets +2/+2 and gains flying and vigilance until end of turn.
        Effect effect = new BoostSourceEffect(2,2,Duration.EndOfTurn);
        effect.setText("{this} gets +2/+2");
        KinshipAbility ability = new KinshipAbility(effect);
        effect = new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains flying");
        ability.addKinshipEffect(effect);
        effect = new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and vigilance until end of turn");
        ability.addKinshipEffect(effect);
        this.addAbility(ability);
    }

    private KithkinZephyrnaut(final KithkinZephyrnaut card) {
        super(card);
    }

    @Override
    public KithkinZephyrnaut copy() {
        return new KithkinZephyrnaut(this);
    }
}
