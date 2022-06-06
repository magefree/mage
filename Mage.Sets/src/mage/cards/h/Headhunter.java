
package mage.cards.h;

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
 * @author LevelX2
 */
public final class Headhunter extends CardImpl {

    public Headhunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);


        // Whenever Headhunter deals combat damage to a player, that player discards a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DiscardTargetEffect(1), false, true));
        
        // Morph {B} (You may cast this face down as a 2/2 creature for {3}. Turn it face up any time for its morph cost.)
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{B}")));

    }

    private Headhunter(final Headhunter card) {
        super(card);
    }

    @Override
    public Headhunter copy() {
        return new Headhunter(this);
    }
}

