package mage.cards.r;

import mage.abilities.condition.common.LandfallCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.target.TargetPlayer;
import mage.watchers.common.LandfallWatcher;

import java.util.UUID;

/**
 * @author Loki
 */
public final class RestForTheWeary extends CardImpl {

    public RestForTheWeary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Target player gains 4 life.
        // Landfall - If you had a land enter the battlefield under your control this turn, that player gains 8 life instead.
        this.getSpellAbility().addWatcher(new LandfallWatcher());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new GainLifeTargetEffect(8), new GainLifeTargetEffect(4),
                LandfallCondition.instance, "Target player gains 4 life. <br/>" +
                AbilityWord.LANDFALL.formatWord() + "If you had a land enter the battlefield " +
                "under your control this turn, that player gains 8 life instead"
        ));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private RestForTheWeary(final RestForTheWeary card) {
        super(card);
    }

    @Override
    public RestForTheWeary copy() {
        return new RestForTheWeary(this);
    }
}
