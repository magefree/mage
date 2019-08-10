package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElshaOfTheInfinite extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard();

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
        filter.add(ElshaOfTheInfinitePredicate.instance);
    }

    public ElshaOfTheInfinite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Prowess
        this.addAbility(new ProwessAbility());

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may cast the top card of your library if it's a noncreature, nonland card, and you may cast it as though it had flash.
        Ability ability = new SimpleStaticAbility(
                new PlayTheTopCardEffect(filter).setText(
                        "you may cast the top card of your library if it's a noncreature, nonland card,"
                )
        );
        ability.addEffect(new CastAsThoughItHadFlashAllEffect(
                Duration.WhileOnBattlefield, filter
        ).setText("and you may cast it as though it had flash"));
        this.addAbility(ability);
    }

    private ElshaOfTheInfinite(final ElshaOfTheInfinite card) {
        super(card);
    }

    @Override
    public ElshaOfTheInfinite copy() {
        return new ElshaOfTheInfinite(this);
    }
}

enum ElshaOfTheInfinitePredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        Player player = game.getPlayer(input.getOwnerId());
        return player != null && player.getLibrary().getFromTop(game).equals(input);
    }
}