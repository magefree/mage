package mage.cards.c;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ContactOtherPlane extends CardImpl {

    public ContactOtherPlane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Roll a d20.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect();
        this.getSpellAbility().addEffect(effect);

        // 1-9 | Draw two cards.
        effect.addTableEntry(1, 9, new DrawCardSourceControllerEffect(2));

        // 10-19 | Scry 2, then draw two cards.
        effect.addTableEntry(
                10, 19, new ScryEffect(2, false),
                new DrawCardSourceControllerEffect(2).concatBy(", then")
        );

        // 20 | Scry 3, then draw three cards.
        effect.addTableEntry(
                20, 20, new ScryEffect(3, false),
                new DrawCardSourceControllerEffect(3).concatBy(", then")
        );
    }

    private ContactOtherPlane(final ContactOtherPlane card) {
        super(card);
    }

    @Override
    public ContactOtherPlane copy() {
        return new ContactOtherPlane(this);
    }
}
