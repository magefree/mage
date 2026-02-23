package mage.cards.m;

import java.util.UUID;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class MindTransferProtocol extends CardImpl {

    public MindTransferProtocol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Until end of turn, target artifact or creature becomes an artifact creature with base power and toughness 4/5.
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.getSpellAbility().addEffect(new BecomesCreatureTargetEffect(
            new CreatureToken(4, 5).withType(CardType.ARTIFACT),
            false, false, Duration.EndOfTurn
        ));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private MindTransferProtocol(final MindTransferProtocol card) {
        super(card);
    }

    @Override
    public MindTransferProtocol copy() {
        return new MindTransferProtocol(this);
    }
}
