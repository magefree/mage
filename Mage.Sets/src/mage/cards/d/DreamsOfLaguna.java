package mage.cards.d;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DreamsOfLaguna extends CardImpl {

    public DreamsOfLaguna(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Surveil 1, then draw a card.
        this.getSpellAbility().addEffect(new SurveilEffect(1, false));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));

        // Flashback {3}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{3}{U}")));
    }

    private DreamsOfLaguna(final DreamsOfLaguna card) {
        super(card);
    }

    @Override
    public DreamsOfLaguna copy() {
        return new DreamsOfLaguna(this);
    }
}
