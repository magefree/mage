package mage.cards.b;

import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BadDeal extends CardImpl {

    public BadDeal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // You draw two cards and each opponent discards two cards. Each player loses 2 life.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2).setText("You draw two cards"));
        this.getSpellAbility().addEffect(new DiscardEachPlayerEffect(
                StaticValue.get(2), false, TargetController.OPPONENT
        ).concatBy("and"));
        this.getSpellAbility().addEffect(new LoseLifeAllPlayersEffect(2).setText("Each player loses 2 life"));
    }

    private BadDeal(final BadDeal card) {
        super(card);
    }

    @Override
    public BadDeal copy() {
        return new BadDeal(this);
    }
}
