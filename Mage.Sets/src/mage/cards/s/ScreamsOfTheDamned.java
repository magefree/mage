
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author cbt33
 */
public final class ScreamsOfTheDamned extends CardImpl {

    public ScreamsOfTheDamned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}{B}");


        // {1}{B}, Exile a card from your graveyard: Screams of the Damned deals 1 damage to each creature and each player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageEverythingEffect(1), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard()));
        this.addAbility(ability);
    }

    private ScreamsOfTheDamned(final ScreamsOfTheDamned card) {
        super(card);
    }

    @Override
    public ScreamsOfTheDamned copy() {
        return new ScreamsOfTheDamned(this);
    }
}
