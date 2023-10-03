package mage.cards.t;

import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TimeLordRegeneration extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.TIME_LORD);
    private static final FilterCard filter2 = new FilterCreatureCard("a Time Lord creature card");

    static {
        filter.add(SubType.TIME_LORD.getPredicate());
    }

    public TimeLordRegeneration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Until end of turn, target Time Lord you control gains "When this creature dies, reveal cards from the top of your library until you reveal a Time Lord creature card. Put that card onto the battlefield and the rest on the bottom of your library in a random order."
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(new DiesSourceTriggeredAbility(
                new RevealCardsFromLibraryUntilEffect(filter2, PutCards.BATTLEFIELD, PutCards.BOTTOM_RANDOM)
        ).setTriggerPhrase("When this creature dies, "), Duration.EndOfTurn, "until end of turn, " +
                "target Time Lord you control gains \"When this creature dies, reveal cards from the " +
                "top of your library until you reveal a Time Lord creature card. Put that card " +
                "onto the battlefield and the rest on the bottom of your library in a random order.\""));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private TimeLordRegeneration(final TimeLordRegeneration card) {
        super(card);
    }

    @Override
    public TimeLordRegeneration copy() {
        return new TimeLordRegeneration(this);
    }
}
