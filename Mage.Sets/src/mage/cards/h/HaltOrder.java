package mage.cards.h;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class HaltOrder extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("artifact spell");
    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public HaltOrder (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        // Counter target artifact spell. Draw a card.
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private HaltOrder(final HaltOrder card) {
        super(card);
    }

    @Override
    public HaltOrder copy() {
        return new HaltOrder(this);
    }
}
