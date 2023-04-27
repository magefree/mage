
package mage.cards.t;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.keyword.ExploreSourceEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author JayDi85
 */
public final class TombRobber extends CardImpl {

    public TombRobber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // {1}, Discard a card: Tomb Robber explores. (Reveal the top card of your library. Put that card into your hand if it's a land. Otherwise, put a +1/+1 counter on this creature, then put the card back or put it into your graveyard.)
        Effect effect = new ExploreSourceEffect(true, "{this}");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new DiscardCardCost());
        ability.addCost(new ManaCostsImpl<>("{1}"));
        this.addAbility(ability);
    }

    private TombRobber(final TombRobber card) {
        super(card);
    }

    @Override
    public TombRobber copy() {
        return new TombRobber(this);
    }
}