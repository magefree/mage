
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.abilityword.KinshipAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class WanderingGraybeard extends CardImpl {

    public WanderingGraybeard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Kinship - At the beginning of your upkeep, you may look at the top card of your library. If it shares a creature type with Wandering Graybeard, you may reveal it. 
        // If you do, you gain 4 life.
        this.addAbility(new KinshipAbility(new GainLifeEffect(4)));
    }

    private WanderingGraybeard(final WanderingGraybeard card) {
        super(card);
    }

    @Override
    public WanderingGraybeard copy() {
        return new WanderingGraybeard(this);
    }
}
