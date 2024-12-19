package mage.cards.r;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.DragonToken2;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class RiteOfTheDragoncaller extends CardImpl {

    public RiteOfTheDragoncaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}{R}");

        // Whenever you cast an instant or sorcery spell, create a 5/5 red Dragon creature token with flying.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new DragonToken2()),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));
    }

    private RiteOfTheDragoncaller(final RiteOfTheDragoncaller card) {
        super(card);
    }

    @Override
    public RiteOfTheDragoncaller copy() {
        return new RiteOfTheDragoncaller(this);
    }
}
