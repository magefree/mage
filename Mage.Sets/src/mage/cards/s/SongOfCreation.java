package mage.cards.s;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SongOfCreation extends CardImpl {

    public SongOfCreation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{U}{R}");

        // You may play an additional land on each of your turns.
        this.addAbility(new SimpleStaticAbility(
                new PlayAdditionalLandsControllerEffect(1, Duration.WhileOnBattlefield)
        ));

        // Whenever you cast a spell, draw two cards.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(2), false
        ));

        // At the beginning of your end step, discard your hand.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new DiscardHandControllerEffect(), TargetController.YOU, false
        ));
    }

    private SongOfCreation(final SongOfCreation card) {
        super(card);
    }

    @Override
    public SongOfCreation copy() {
        return new SongOfCreation(this);
    }
}
