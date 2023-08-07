package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author nigelzor
 */
public final class SkitteringMonstrosity extends CardImpl {

    public SkitteringMonstrosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When you cast a creature spell, sacrifice Skittering Monstrosity.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new SacrificeSourceEffect(), StaticFilters.FILTER_SPELL_A_CREATURE, false
        ).setTriggerPhrase("When you cast a creature spell, "));
    }

    private SkitteringMonstrosity(final SkitteringMonstrosity card) {
        super(card);
    }

    @Override
    public SkitteringMonstrosity copy() {
        return new SkitteringMonstrosity(this);
    }
}
