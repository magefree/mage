
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class LoreBroker extends CardImpl {

    public LoreBroker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {tap}: Each player draws a card, then discards a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardAllEffect(1), new TapSourceCost());
        ability.addEffect(new DiscardEachPlayerEffect().setText(", then discards a card"));
        this.addAbility(ability);
    }

    private LoreBroker(final LoreBroker card) {
        super(card);
    }

    @Override
    public LoreBroker copy() {
        return new LoreBroker(this);
    }
}
