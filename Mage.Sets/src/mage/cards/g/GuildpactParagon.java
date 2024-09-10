package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GuildpactParagon extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell that's exactly two colors");
    private static final FilterCard filter2 = new FilterCard("a card that's exactly two colors");

    static {
        filter.add(GuildpactParagonPredicate.instance);
        filter2.add(GuildpactParagonPredicate.instance);
    }

    public GuildpactParagon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.nightCard = true;

        // Whenever you cast a spell that's exactly two colors, look at the top six cards of your library. You may reveal a card that's exactly two colors from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new SpellCastControllerTriggeredAbility(new LookLibraryAndPickControllerEffect(
                6, 1, filter2, PutCards.HAND, PutCards.BOTTOM_RANDOM
        ), filter, false));
    }

    private GuildpactParagon(final GuildpactParagon card) {
        super(card);
    }

    @Override
    public GuildpactParagon copy() {
        return new GuildpactParagon(this);
    }
}

enum GuildpactParagonPredicate implements Predicate<MageObject> {
    instance;

    @Override
    public boolean apply(MageObject input, Game game) {
        return input.getColor(game).getColorCount() == 2;
    }
}
