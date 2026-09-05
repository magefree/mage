package mage.cards.t;

import java.util.UUID;

import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.TeamworkCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.TeamworkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TimelineInquiry extends CardImpl {

    public TimelineInquiry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");


        // Teamwork 2
        this.addAbility(new TeamworkAbility(2));

        // Draw three cards. Then discard a card unless this spell was cast using teamwork.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
            new DiscardControllerEffect(1),
            new InvertCondition(TeamworkCondition.instance),
            "Then discard a card unless this spell was cast using teamwork"
        ));
    }

    private TimelineInquiry(final TimelineInquiry card) {
        super(card);
    }

    @Override
    public TimelineInquiry copy() {
        return new TimelineInquiry(this);
    }
}
