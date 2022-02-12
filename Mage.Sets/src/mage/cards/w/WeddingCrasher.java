package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WeddingCrasher extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("Wolf or Werewolf you control");

    static {
        filter.add(Predicates.or(
                SubType.WOLF.getPredicate(),
                SubType.WEREWOLF.getPredicate()
        ));
    }

    public WeddingCrasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);
        this.color.setGreen(true);
        this.nightCard = true;

        // Whenever Wedding Crasher or another Wolf or Werewolf you control dies, draw a card.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false, filter
        ));

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private WeddingCrasher(final WeddingCrasher card) {
        super(card);
    }

    @Override
    public WeddingCrasher copy() {
        return new WeddingCrasher(this);
    }
}
