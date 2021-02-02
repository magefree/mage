
package mage.cards.d;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class DragonsClaw extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a red spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public DragonsClaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Whenever a player casts a red spell, you may gain 1 life.
        this.addAbility(new SpellCastAllTriggeredAbility(new GainLifeEffect(1), filter, true));
    }

    private DragonsClaw(final DragonsClaw card) {
        super(card);
    }

    @Override
    public DragonsClaw copy() {
        return new DragonsClaw(this);
    }

}
