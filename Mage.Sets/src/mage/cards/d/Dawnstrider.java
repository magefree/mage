
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author Plopman
 */
public final class Dawnstrider extends CardImpl {

    public Dawnstrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.DRYAD);
        this.subtype.add(SubType.SPELLSHAPER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {G}, {tap}, Discard a card: Prevent all combat damage that would be dealt this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true), new ManaCostsImpl<>("{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardTargetCost(new TargetCardInHand()));
        this.addAbility(ability);
    }

    private Dawnstrider(final Dawnstrider card) {
        super(card);
    }

    @Override
    public Dawnstrider copy() {
        return new Dawnstrider(this);
    }
}
