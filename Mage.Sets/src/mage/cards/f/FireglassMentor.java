package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfSecondMainTriggeredAbility;
import mage.abilities.condition.common.OpponentsLostLifeCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class FireglassMentor extends CardImpl {

    public FireglassMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // At the beginning of your second main phase, if an opponent lost life this turn, exile the top two cards of your library. Choose one of them. Until end of turn, you may play that card.
        this.addAbility(new BeginningOfSecondMainTriggeredAbility(new FireglassMentorEffect(), false)
                .withInterveningIf(OpponentsLostLifeCondition.instance));
    }

    private FireglassMentor(final FireglassMentor card) {
        super(card);
    }

    @Override
    public FireglassMentor copy() {
        return new FireglassMentor(this);
    }
}

//Based on CaseOfTheBurningMasksEffect
class FireglassMentorEffect extends OneShotEffect {

    FireglassMentorEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top two cards of your library. Choose one of them. Until end of turn, you may play that card";
    }

    private FireglassMentorEffect(final FireglassMentorEffect effect) {
        super(effect);
    }

    @Override
    public FireglassMentorEffect copy() {
        return new FireglassMentorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 2));
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.retainZone(Zone.EXILED, game);

        Card card;
        switch (cards.size()) {
            case 0:
                return false;
            case 1:
                card = cards.getRandom(game);
                break;
            default:
                TargetCard target = new TargetCardInExile(StaticFilters.FILTER_CARD);
                target.withNotTarget(true);
                player.choose(outcome, cards, target, source, game);
                card = game.getCard(target.getFirstTarget());
        }
        if (card != null) {
            CardUtil.makeCardPlayable(game, source, card, false, Duration.EndOfTurn, false);
        }
        return true;
    }
}
