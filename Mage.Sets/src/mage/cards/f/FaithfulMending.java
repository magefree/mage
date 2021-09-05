package mage.cards.f;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FaithfulMending extends CardImpl {

    public FaithfulMending(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{U}");

        // You gain 2 life, draw two cards, then discard two cards.
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2).concatBy(","));
        this.getSpellAbility().addEffect(new DiscardControllerEffect(2).concatBy(", then"));

        // Flashback {1}{W}{U}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl<>("{1}{W}{U}"), TimingRule.INSTANT));
    }

    private FaithfulMending(final FaithfulMending card) {
        super(card);
    }

    @Override
    public FaithfulMending copy() {
        return new FaithfulMending(this);
    }
}
