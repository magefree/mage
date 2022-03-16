
package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DefendingPlayerControlsCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author Backfir3
 */
public final class BubblingBeebles extends CardImpl {

    public BubblingBeebles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.BEEBLE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Bubbling Beebles can't be blocked as long as defending player controls an enchantment.
        Effect effect = new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(),
                new DefendingPlayerControlsCondition(StaticFilters.FILTER_PERMANENT_ENCHANTMENT));
        effect.setText("{this} can't be blocked as long as defending player controls an enchantment");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private BubblingBeebles(final BubblingBeebles card) {
        super(card);
    }

    @Override
    public BubblingBeebles copy() {
        return new BubblingBeebles(this);
    }
}
