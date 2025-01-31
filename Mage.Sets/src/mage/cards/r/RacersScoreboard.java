package mage.cards.r;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RacersScoreboard extends CardImpl {

    private static final FilterCard filter = new FilterCard("spells");

    public RacersScoreboard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // When this artifact enters, draw two cards, then discard a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DrawDiscardControllerEffect(2, 1)
        ));

        // Max speed -- Spells you cast cost {1} less to cast.
        this.addAbility(new MaxSpeedAbility(new SpellsCostReductionControllerEffect(filter, 1)));
    }

    private RacersScoreboard(final RacersScoreboard card) {
        super(card);
    }

    @Override
    public RacersScoreboard copy() {
        return new RacersScoreboard(this);
    }
}
