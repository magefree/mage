package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.other.SpellZonePredicate;

/**
 *
 * @author BetaSteward
 */
public final class SecretsOfTheDead extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell from your graveyard");

    static {
        filter.add(new SpellZonePredicate(Zone.GRAVEYARD));
    }

    public SecretsOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Whenever you cast a spell from your graveyard, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DrawCardSourceControllerEffect(1), filter, false));
    }

    private SecretsOfTheDead(final SecretsOfTheDead card) {
        super(card);
    }

    @Override
    public SecretsOfTheDead copy() {
        return new SecretsOfTheDead(this);
    }
}
