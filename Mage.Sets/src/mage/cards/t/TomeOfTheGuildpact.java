package mage.cards.t;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TomeOfTheGuildpact extends CardImpl {

    public TomeOfTheGuildpact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // Whenever you cast a multicolored spell, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                StaticFilters.FILTER_SPELL_A_MULTICOLORED, false
        ));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private TomeOfTheGuildpact(final TomeOfTheGuildpact card) {
        super(card);
    }

    @Override
    public TomeOfTheGuildpact copy() {
        return new TomeOfTheGuildpact(this);
    }
}
