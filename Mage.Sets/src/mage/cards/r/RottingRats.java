

package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class RottingRats extends CardImpl {

    public RottingRats (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.RAT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Rotting Rats enters the battlefield, each player discards a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DiscardEachPlayerEffect()));

        // Unearth {1}{B} ({1}{B}: Return this card from your graveyard to the battlefield. It gains haste. Exile it at the beginning of the next end step or if it would leave the battlefield. Unearth only as a sorcery.)
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{1}{B}")));

    }

    public RottingRats (final RottingRats card) {
        super(card);
    }

    @Override
    public RottingRats copy() {
        return new RottingRats(this);
    }
}
