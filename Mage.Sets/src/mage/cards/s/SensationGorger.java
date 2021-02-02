
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.abilityword.KinshipAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.discard.DiscardHandAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SensationGorger extends CardImpl {

    public SensationGorger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kinship - At the beginning of your upkeep, you may look at the top card of your library. If it shares a creature type with Sensation Gorger, you may reveal it. 
        // If you do, each player discards their hand, then draws four cards.
        KinshipAbility ability = new KinshipAbility(new DiscardHandAllEffect());
        Effect effect = new DrawCardAllEffect(4);
        effect.setText(", then draws four cards");
        ability.addKinshipEffect(effect);
        this.addAbility(ability);
    }

    private SensationGorger(final SensationGorger card) {
        super(card);
    }

    @Override
    public SensationGorger copy() {
        return new SensationGorger(this);
    }
}
