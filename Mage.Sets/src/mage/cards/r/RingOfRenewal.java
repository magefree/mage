
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class RingOfRenewal extends CardImpl {

    public RingOfRenewal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // {5}, {tap}: Discard a card at random, then draw two cards.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DiscardControllerEffect(1, true), new ManaCostsImpl<>("{5}"));
        ability.addCost(new TapSourceCost());
        Effect effect = new DrawCardSourceControllerEffect(2);
        effect.setText(", then draw two cards");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private RingOfRenewal(final RingOfRenewal card) {
        super(card);
    }

    @Override
    public RingOfRenewal copy() {
        return new RingOfRenewal(this);
    }
}
