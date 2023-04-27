
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class EmmessiTome extends CardImpl {

    public EmmessiTome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {5}, {tap}: Draw two cards, then discard a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawDiscardControllerEffect(2,1), new ManaCostsImpl<>("{5}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private EmmessiTome(final EmmessiTome card) {
        super(card);
    }

    @Override
    public EmmessiTome copy() {
        return new EmmessiTome(this);
    }
}
