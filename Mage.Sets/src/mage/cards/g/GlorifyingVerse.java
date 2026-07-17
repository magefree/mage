package mage.cards.g;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.abilityword.ReparteeAbility;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class GlorifyingVerse extends CardImpl {

    public GlorifyingVerse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W/B}");

        // Exalted
        this.addAbility(new ExaltedAbility());

        // Repartee -- Whenever you cast an instant or sorcery spell that targets a creature, conjure a card named Glorifying Verse into your hand.
        Ability ability = new ReparteeAbility(new ConjureCardEffect("Glorifying Verse"));
        this.addAbility(ability);
    }

    private GlorifyingVerse(final GlorifyingVerse card) {
        super(card);
    }

    @Override
    public GlorifyingVerse copy() {
        return new GlorifyingVerse(this);
    }
}
