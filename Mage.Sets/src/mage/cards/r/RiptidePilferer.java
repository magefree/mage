
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class RiptidePilferer extends CardImpl {

    public RiptidePilferer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Riptide Pilferer deals combat damage to a player, that player discards a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DiscardTargetEffect(1), false, true));                                                                                               // Morph {U}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{U}")));
    }

    private RiptidePilferer(final RiptidePilferer card) {
        super(card);
    }

    @Override
    public RiptidePilferer copy() {
        return new RiptidePilferer(this);
    }
}
