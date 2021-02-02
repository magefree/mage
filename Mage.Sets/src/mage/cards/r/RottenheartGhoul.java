
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class RottenheartGhoul extends CardImpl {

    public RottenheartGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Rottenheart Ghoul dies, target player discards a card.
        Ability ability = new DiesSourceTriggeredAbility(new DiscardTargetEffect(1));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private RottenheartGhoul(final RottenheartGhoul card) {
        super(card);
    }

    @Override
    public RottenheartGhoul copy() {
        return new RottenheartGhoul(this);
    }
}
