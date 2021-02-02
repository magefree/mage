package mage.cards.f;

import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FeedTheClan extends CardImpl {

    public FeedTheClan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // You gain 5 life.
        // Ferocious - You gain 10 life instead if you control a creature with power 4 or greater
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new GainLifeEffect(10),
                new GainLifeEffect(5),
                FerociousCondition.instance,
                "You gain 5 life. <br><i>Ferocious</i> &mdash; You gain 10 life instead if you control a creature with power 4 or greater"));
        this.getSpellAbility().addHint(FerociousHint.instance);
    }

    private FeedTheClan(final FeedTheClan card) {
        super(card);
    }

    @Override
    public FeedTheClan copy() {
        return new FeedTheClan(this);
    }
}
