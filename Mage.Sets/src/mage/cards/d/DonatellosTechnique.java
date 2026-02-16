package mage.cards.d;

import java.util.UUID;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.SneakAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class DonatellosTechnique extends CardImpl {

    public DonatellosTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Sneak {U}
        this.addAbility(new SneakAbility(this, "{U}"));

        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    private DonatellosTechnique(final DonatellosTechnique card) {
        super(card);
    }

    @Override
    public DonatellosTechnique copy() {
        return new DonatellosTechnique(this);
    }
}
