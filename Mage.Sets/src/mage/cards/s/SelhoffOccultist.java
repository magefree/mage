
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class SelhoffOccultist extends CardImpl {

    public SelhoffOccultist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Selhoff Occultist or another creature dies, target player puts the top card of their library into their graveyard.
        Ability ability = new DiesThisOrAnotherCreatureTriggeredAbility(new MillCardsTargetEffect(1), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private SelhoffOccultist(final SelhoffOccultist card) {
        super(card);
    }

    @Override
    public SelhoffOccultist copy() {
        return new SelhoffOccultist(this);
    }
}
