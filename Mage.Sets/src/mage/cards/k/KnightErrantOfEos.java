package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ConvokedSourceCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KnightErrantOfEos extends CardImpl {

    public KnightErrantOfEos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // When Knight-Errant of Eos enters the battlefield, look at the top six cards of your library. You may reveal up to two creature cards with mana value X or less from among them, where X is the number of creatures that convoked Knight-Errant of Eos. Put the revealed cards into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new KnightErrantOfEosEffect()));
    }

    private KnightErrantOfEos(final KnightErrantOfEos card) {
        super(card);
    }

    @Override
    public KnightErrantOfEos copy() {
        return new KnightErrantOfEos(this);
    }
}

class KnightErrantOfEosEffect extends OneShotEffect {

    enum KnightErrantOfEosPredicate implements ObjectSourcePlayerPredicate<Card> {
        instance;

        @Override
        public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
            return input
                    .getObject()
                    .getManaValue()
                    <= ConvokedSourceCount
                    .PERMANENT
                    .calculate(game, input.getSource(), null);
        }
    }

    private static final FilterCard filter = new FilterCreatureCard(
            "creature card with mana value less than or equal to the number of creatures that convoked this"
    );

    static {
        filter.add(KnightErrantOfEosPredicate.instance);
    }

    KnightErrantOfEosEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top six cards of your library. You may reveal up to two creature cards " +
                "with mana value X or less from among them, where X is the number of creatures that convoked {this}. " +
                "Put the revealed cards into your hand, then shuffle";
    }

    private KnightErrantOfEosEffect(final KnightErrantOfEosEffect effect) {
        super(effect);
    }

    @Override
    public KnightErrantOfEosEffect copy() {
        return new KnightErrantOfEosEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 6));
        TargetCard target = new TargetCardInLibrary(0, 2, filter);
        player.choose(Outcome.DrawCard, cards, target, source, game);
        Cards toHand = new CardsImpl(target.getTargets());
        player.revealCards(source, toHand, game);
        player.moveCards(toHand, Zone.HAND, source, game);
        player.shuffleLibrary(source, game);
        return true;
    }
}
