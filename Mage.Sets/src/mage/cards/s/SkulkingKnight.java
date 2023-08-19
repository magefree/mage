
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SourceBecomesTargetTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.FlankingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class SkulkingKnight extends CardImpl {

    public SkulkingKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flanking
        this.addAbility(new FlankingAbility());
        // When Skulking Knight becomes the target of a spell or ability, sacrifice it.
        this.addAbility(new SourceBecomesTargetTriggeredAbility(new SacrificeSourceEffect().setText("sacrifice it")));
    }

    private SkulkingKnight(final SkulkingKnight card) {
        super(card);
    }

    @Override
    public SkulkingKnight copy() {
        return new SkulkingKnight(this);
    }
}
