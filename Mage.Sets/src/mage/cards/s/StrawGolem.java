
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author Derpthemeus
 */
public final class StrawGolem extends CardImpl {

    public StrawGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When an opponent casts a creature spell, sacrifice Straw Golem.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new SacrificeSourceEffect(), StaticFilters.FILTER_SPELL_A_CREATURE, false)
                .setTriggerPhrase("When an opponent casts a creature spell, "));
    }

    private StrawGolem(final StrawGolem card) {
        super(card);
    }

    @Override
    public StrawGolem copy() {
        return new StrawGolem(this);
    }
}
