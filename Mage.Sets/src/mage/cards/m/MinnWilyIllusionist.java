package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.MinnWilyIllusionistToken;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MinnWilyIllusionist extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent(SubType.ILLUSION, "an Illusion you control");

    public MinnWilyIllusionist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GNOME);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you draw your second card each turn, create a 1/1 blue Illusion creature token with "This creature gets +1/+0 for each other Illusion you control."
        this.addAbility(new DrawNthCardTriggeredAbility(
                new CreateTokenEffect(new MinnWilyIllusionistToken()), false, 2
        ));

        // Whenever an Illusion you control dies, you may put a permanent card with mana value less than or equal to that creature's power from your hand onto the battlefield.
        this.addAbility(new DiesCreatureTriggeredAbility(new MinnWilyIllusionistEffect(), false, filter));
    }

    private MinnWilyIllusionist(final MinnWilyIllusionist card) {
        super(card);
    }

    @Override
    public MinnWilyIllusionist copy() {
        return new MinnWilyIllusionist(this);
    }
}

class MinnWilyIllusionistEffect extends OneShotEffect {

    MinnWilyIllusionistEffect() {
        super(Outcome.Benefit);
        staticText = "you may put a permanent card with mana value less than or equal " +
                "to that creature's power from your hand onto the battlefield";
    }

    private MinnWilyIllusionistEffect(final MinnWilyIllusionistEffect effect) {
        super(effect);
    }

    @Override
    public MinnWilyIllusionistEffect copy() {
        return new MinnWilyIllusionistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = (Permanent) getValue("creatureDied");
        if (player == null || permanent == null) {
            return false;
        }
        FilterCard filterCard = new FilterPermanentCard(
                "permanent card with mana value "
                        + permanent.getPower().getValue() + " or less"
        );
        filterCard.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, permanent.getPower().getValue() + 1));
        TargetCardInHand target = new TargetCardInHand(0, 1, filterCard);
        player.choose(Outcome.PutCardInPlay, player.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        return player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
