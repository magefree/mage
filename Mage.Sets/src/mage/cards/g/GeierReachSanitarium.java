
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class GeierReachSanitarium extends CardImpl {

    public GeierReachSanitarium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.supertype.add(SuperType.LEGENDARY);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {2}, {T}: Each player draws a card, then discards a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardAllEffect(1), new GenericManaCost(2));
        Effect effect = new DiscardEachPlayerEffect();
        effect.setText(", then discards a card");
        ability.addEffect(effect);
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private GeierReachSanitarium(final GeierReachSanitarium card) {
        super(card);
    }

    @Override
    public GeierReachSanitarium copy() {
        return new GeierReachSanitarium(this);
    }
}
