package mage.cards.g;

import mage.abilities.dynamicvalue.common.SnowManaSpentValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GravenLore extends CardImpl {

    public GravenLore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");

        this.supertype.add(SuperType.SNOW);

        // Scry X, where X is the amount of {S} spent to cast this spell, then draw three cards.
        this.getSpellAbility().addEffect(new ScryEffect(SnowManaSpentValue.instance, false)
                .setText("scry X, where X is the amount of {S} spent to cast this spell"));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3).concatBy(", then"));
    }

    private GravenLore(final GravenLore card) {
        super(card);
    }

    @Override
    public GravenLore copy() {
        return new GravenLore(this);
    }
}
