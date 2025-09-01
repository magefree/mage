package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScoutForSurvivors extends CardImpl {

    public ScoutForSurvivors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // Return up to three target creature cards with total mana value 3 or less from your graveyard to the battlefield. Put a +1/+1 counter on each of them.
        this.getSpellAbility().addEffect(new ScoutForSurvivorsEffect());
        this.getSpellAbility().addTarget(new ScoutForSurvivorsTarget());
    }

    private ScoutForSurvivors(final ScoutForSurvivors card) {
        super(card);
    }

    @Override
    public ScoutForSurvivors copy() {
        return new ScoutForSurvivors(this);
    }
}

class ScoutForSurvivorsEffect extends OneShotEffect {

    ScoutForSurvivorsEffect() {
        super(Outcome.Benefit);
        staticText = "return up to three target creature cards with total mana value 3 or less " +
                "from your graveyard to the battlefield. Put a +1/+1 counter on each of them";
    }

    private ScoutForSurvivorsEffect(final ScoutForSurvivorsEffect effect) {
        super(effect);
    }

    @Override
    public ScoutForSurvivorsEffect copy() {
        return new ScoutForSurvivorsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        cards.retainZone(Zone.GRAVEYARD, game);
        if (player == null || cards.isEmpty()) {
            return false;
        }
        player.moveCards(cards, Zone.BATTLEFIELD, source, game);
        for (Card card : cards.getCards(game)) {
            Optional.ofNullable(CardUtil.getPermanentFromCardPutToBattlefield(card, game))
                    .ifPresent(permanent -> permanent.addCounters(CounterType.P1P1.createInstance(), source, game));
        }
        return true;
    }
}

class ScoutForSurvivorsTarget extends TargetCardInYourGraveyard {

    private static final FilterCard filterStatic
            = new FilterCreatureCard("creature cards with total mana value 3 or less from your graveyard");

    ScoutForSurvivorsTarget() {
        super(0, 3, filterStatic, false);
    }

    private ScoutForSurvivorsTarget(final ScoutForSurvivorsTarget target) {
        super(target);
    }

    @Override
    public ScoutForSurvivorsTarget copy() {
        return new ScoutForSurvivorsTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return super.canTarget(playerId, id, source, game)
                && CardUtil.checkCanTargetTotalValueLimit(
                this.getTargets(), id, MageObject::getManaValue, 3, game
        );
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        return CardUtil.checkPossibleTargetsTotalValueLimit(
                this.getTargets(),
                super.possibleTargets(sourceControllerId, source, game),
                MageObject::getManaValue, 3, game
        );
    }

    @Override
    public String getMessage(Game game) {
        // shows selected total
        int selectedValue = this
                .getTargets()
                .stream()
                .map(game::getObject)
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum();
        return super.getMessage(game) + " (selected total mana value " + selectedValue + ")";
    }
}
