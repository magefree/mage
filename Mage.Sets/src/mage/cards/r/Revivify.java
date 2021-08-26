package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.PutIntoGraveFromBattlefieldThisTurnPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Revivify extends CardImpl {

    static final FilterCard filter = new FilterCreatureCard();

    static {
        filter.add(PutIntoGraveFromBattlefieldThisTurnPredicate.instance);
    }

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(filter);
    private static final Hint hint = new ValueHint("Creature cards in your graveyard that died this turn", xValue);

    public Revivify(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Roll a d20 and add the number of creature cards in your graveyard that were put there from the battlefield this turn.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect(
                20, "roll a d20 and add the number of creature cards " +
                "in your graveyard that were put there from the battlefield this turn", xValue, 0
        );
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addWatcher(new CardsPutIntoGraveyardWatcher());
        this.getSpellAbility().addHint(hint);

        // 1-14 | Return all creature cards in your graveyard that were put there from the battlefield this turn to your hand.
        effect.addTableEntry(1, 14, new RevivifyEffect(true));

        // 15+ | Return those cards from your graveyard to the battlefield.
        effect.addTableEntry(15, Integer.MAX_VALUE, new RevivifyEffect(false));
    }

    private Revivify(final Revivify card) {
        super(card);
    }

    @Override
    public Revivify copy() {
        return new Revivify(this);
    }
}

class RevivifyEffect extends OneShotEffect {

    private final boolean toHand;

    RevivifyEffect(boolean toHand) {
        super(Outcome.Benefit);
        this.toHand = toHand;
        staticText = toHand ? "return all creature cards in your graveyard " +
                "that were put there from the battlefield this turn to your hand"
                : "return those cards from your graveyard to the battlefield";
    }

    private RevivifyEffect(final RevivifyEffect effect) {
        super(effect);
        this.toHand = effect.toHand;
    }

    @Override
    public RevivifyEffect copy() {
        return new RevivifyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        return player.moveCards(player.getGraveyard().getCards(
                Revivify.filter, source.getSourceId(), source.getControllerId(), game
        ), toHand ? Zone.HAND : Zone.BATTLEFIELD, source, game);
    }
}
