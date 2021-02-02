

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class GoldenglowMoth extends CardImpl {

    public GoldenglowMoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);
        
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Goldenglow Moth blocks, you may gain 4 life.
        this.addAbility(new BlocksSourceTriggeredAbility(new GainLifeEffect(4), true));

    }

    private GoldenglowMoth(final GoldenglowMoth card) {
        super(card);
    }

    @Override
    public GoldenglowMoth copy() {
        return new GoldenglowMoth(this);
    }

}
