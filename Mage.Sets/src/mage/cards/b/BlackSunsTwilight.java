package mage.cards.b;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public final class BlackSunsTwilight extends CardImpl {

    public BlackSunsTwilight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{B}");

        // Up to one target creature gets -X/-X until end of turn. If X is 5 or more, return a creature card with mana value X or less from your graveyard to the battlefield tapped.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                ManacostVariableValue.REGULAR, ManacostVariableValue.REGULAR, Duration.EndOfTurn
        ));
        this.getSpellAbility().addEffect(new BlackSunsTwilightEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
    }

    private BlackSunsTwilight(final BlackSunsTwilight card) {
        super(card);
    }

    @Override
    public BlackSunsTwilight copy() {
        return new BlackSunsTwilight(this);
    }
}

class BlackSunsTwilightEffect extends OneShotEffect {

    BlackSunsTwilightEffect() {
        super(Outcome.Benefit);
        staticText = "If X is 5 or more, return a creature card with mana value " +
                "X or less from your graveyard to the battlefield tapped";
    }

    private BlackSunsTwilightEffect(final BlackSunsTwilightEffect effect) {
        super(effect);
    }

    @Override
    public BlackSunsTwilightEffect copy() {
        return new BlackSunsTwilightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int xValue = source.getManaCostsToPay().getX();
        if (player == null || xValue < 5) {
            return false;
        }
        FilterCard filter = new FilterCreatureCard(
                "creature card with mana value " + xValue + " or less from your graveyard"
        );
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue + 1));
        if (player.getGraveyard().count(filter, game) < 1) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(filter);
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && player.moveCards(
                card, Zone.BATTLEFIELD, source, game,
                true, false, false, null
        );
    }
}
