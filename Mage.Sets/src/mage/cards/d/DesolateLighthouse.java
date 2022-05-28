
package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class DesolateLighthouse extends CardImpl {

    public DesolateLighthouse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {1}{U}{R}, {tap}: Draw a card, then discard a card.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DrawDiscardControllerEffect(),
                new ManaCostsImpl<>("{1}{U}{R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private DesolateLighthouse(final DesolateLighthouse card) {
        super(card);
    }

    @Override
    public DesolateLighthouse copy() {
        return new DesolateLighthouse(this);
    }
}
