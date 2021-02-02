
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SplittingSlime extends CardImpl {

    public SplittingSlime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {4}{G}{G}: Monstrosity 3.
        this.addAbility(new MonstrosityAbility("{4}{G}{G}", 3));

        // When Splitting Slime becomes monstrous, create a token that's a copy of Splitting Slime.
        this.addAbility(new BecomesMonstrousSourceTriggeredAbility(new CreateTokenCopySourceEffect()));

    }

    private SplittingSlime(final SplittingSlime card) {
        super(card);
    }

    @Override
    public SplittingSlime copy() {
        return new SplittingSlime(this);
    }
}
