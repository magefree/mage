
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class BlindHunter extends CardImpl {

    public BlindHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{B}");
        this.subtype.add(SubType.BAT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Haunt
        // When Blind Hunter enters the battlefield or the creature it haunts dies, target player loses 2 life and you gain 2 life.
        Ability ability = new HauntAbility(this, new LoseLifeTargetEffect(2));
        ability.addTarget(new TargetPlayer());
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability);

    }

    private BlindHunter(final BlindHunter card) {
        super(card);
    }

    @Override
    public BlindHunter copy() {
        return new BlindHunter(this);
    }
}
