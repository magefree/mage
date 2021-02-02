

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileCardYouChooseTargetOpponentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */


public final class SinCollector extends CardImpl {

    public SinCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);


        // When Sin Collector enters the battlefield, target opponent reveals their hand. You choose an instant or sorcery card from it and exile that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileCardYouChooseTargetOpponentEffect(new FilterInstantOrSorceryCard("an instant or sorcery card")));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private SinCollector(final SinCollector card) {
        super(card);
    }

    @Override
    public SinCollector copy() {
        return new SinCollector(this);
    }
}
