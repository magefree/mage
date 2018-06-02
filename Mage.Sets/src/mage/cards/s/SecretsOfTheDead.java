
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.Spell;

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
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");


        // Whenever you cast a spell from your graveyard, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DrawCardSourceControllerEffect(1), filter, false));
    }

    public SecretsOfTheDead(final SecretsOfTheDead card) {
        super(card);
    }

    @Override
    public SecretsOfTheDead copy() {
        return new SecretsOfTheDead(this);
    }
}

class SpellZonePredicate implements Predicate<Spell> {

    private final Zone zone;

    public SpellZonePredicate(Zone zone) {
        this.zone = zone;
    }

    @Override
    public boolean apply(Spell input, Game game) {
        return input.getFromZone().match(zone);
    }

    @Override
    public String toString() {
        return "SpellZone(" + zone + ')';
    }
}
