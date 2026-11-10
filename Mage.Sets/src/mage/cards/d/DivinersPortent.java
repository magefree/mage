package mage.cards.d;

import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.GetXValue;
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
public final class DivinersPortent extends CardImpl {

    public DivinersPortent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}{U}{U}");

        // Roll a d20 and add the number of cards in your hand.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect(
                20, "roll a d20 and add the number " +
                "of cards in your hand", CardsInControllerHandCount.ANY, 0
        );
        this.getSpellAbility().addEffect(effect);

        // 1-14 | Draw X cards.
        effect.addTableEntry(1, 14, new DrawCardSourceControllerEffect(GetXValue.instance));

        // 15+ | Scry X, then draw X cards.
        effect.addTableEntry(15, Integer.MAX_VALUE,
                new ScryEffect(GetXValue.instance, false),
                new DrawCardSourceControllerEffect(GetXValue.instance).concatBy(", then")
        );
    }

    private DivinersPortent(final DivinersPortent card) {
        super(card);
    }

    @Override
    public DivinersPortent copy() {
        return new DivinersPortent(this);
    }
}
