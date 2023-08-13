
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SourceBecomesTargetTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class SkulkingGhost extends CardImpl {

    public SkulkingGhost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Skulking Ghost becomes the target of a spell or ability, sacrifice it.
        this.addAbility(new SourceBecomesTargetTriggeredAbility(new SacrificeSourceEffect()));
    }

    private SkulkingGhost(final SkulkingGhost card) {
        super(card);
    }

    @Override
    public SkulkingGhost copy() {
        return new SkulkingGhost(this);
    }
}
