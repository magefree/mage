package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.abilities.keyword.WarpAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AnticausalVestige extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard(
            "a permanent card with mana value less than or equal to the number of lands you control"
    );

    static {
        filter.add(AnticausalVestigePredicate.instance);
    }

    public AnticausalVestige(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // When this creature leaves the battlefield, draw a card, then you may put a permanent card with mana value less than or equal to the number of lands you control from your hand onto the battlefield tapped.
        Ability ability = new LeavesBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1));
        ability.addEffect(new PutCardFromHandOntoBattlefieldEffect(filter, false, true).concatBy(", then"));
        this.addAbility(ability.addHint(LandsYouControlHint.instance));

        // Warp {4}
        this.addAbility(new WarpAbility(this, "{4}"));
    }

    private AnticausalVestige(final AnticausalVestige card) {
        super(card);
    }

    @Override
    public AnticausalVestige copy() {
        return new AnticausalVestige(this);
    }
}

enum AnticausalVestigePredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return input.getObject().getManaValue()
                <= LandsYouControlCount.instance.calculate(game, input.getSource(), null);
    }
}
