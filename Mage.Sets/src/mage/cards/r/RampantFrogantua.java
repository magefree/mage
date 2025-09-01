package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.watchers.common.PlayerLostGameWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RampantFrogantua extends CardImpl {

    public RampantFrogantua(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.FROG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Rampant Frogantua gets +10/+10 for each player who has lost the game.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                RampantFrogantuaValue.instance, RampantFrogantuaValue.instance, Duration.WhileOnBattlefield
        )), new PlayerLostGameWatcher());

        // Whenever Rampant Frogantua deals combat damage to a player, you may mill that many cards. Put any number of land cards from among them onto the battlefield tapped.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new RampantFrogantuaEffect(), true));
    }

    private RampantFrogantua(final RampantFrogantua card) {
        super(card);
    }

    @Override
    public RampantFrogantua copy() {
        return new RampantFrogantua(this);
    }
}

class RampantFrogantuaEffect extends OneShotEffect {

    RampantFrogantuaEffect() {
        super(Outcome.Benefit);
        staticText = "mill that many cards. Put any number of land cards from among them onto the battlefield tapped";
    }

    private RampantFrogantuaEffect(final RampantFrogantuaEffect effect) {
        super(effect);
    }

    @Override
    public RampantFrogantuaEffect copy() {
        return new RampantFrogantuaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int amount = SavedDamageValue.MANY.calculate(game, source, this);
        if (player == null || amount < 1) {
            return false;
        }
        Cards cards = player.millCards(amount, source, game);
        if (cards.isEmpty()) {
            return true;
        }
        TargetCard target = new TargetCard(0, Integer.MAX_VALUE, Zone.ALL, StaticFilters.FILTER_CARD_LANDS);
        target.withNotTarget(true);
        player.choose(Outcome.DrawCard, cards, target, source, game);
        player.moveCards(
                new CardsImpl(target.getTargets()).getCards(game),
                Zone.BATTLEFIELD, source, game, true,
                false, false, null
        );
        return true;
    }
}

enum RampantFrogantuaValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return 10 * PlayerLostGameWatcher.getCount(game);
    }

    @Override
    public RampantFrogantuaValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "player who has lost the game";
    }

    @Override
    public String toString() {
        return "10";
    }
}
