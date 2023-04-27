package mage.cards.m;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MeetingOfMinds extends CardImpl {

    public MeetingOfMinds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    private MeetingOfMinds(final MeetingOfMinds card) {
        super(card);
    }

    @Override
    public MeetingOfMinds copy() {
        return new MeetingOfMinds(this);
    }
}
