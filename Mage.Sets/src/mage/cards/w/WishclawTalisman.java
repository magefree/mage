package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WishclawTalisman extends CardImpl {

    public WishclawTalisman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}");

        // Wishclaw Talisman enters the battlefield with three wish counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.WISH.createInstance(3)),
                "with three wish counters on it"
        ));

        // {1}, {T}, Remove a wish counter from Wishclaw Talisman: Search your library for a card, put it into your hand, then shuffle your library. An opponent gains control of Wishclaw Talisman. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new WishclawTalismanEffect(), new GenericManaCost(1), MyTurnCondition.instance
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.WISH.createInstance()));
        ability.addHint(MyTurnHint.instance);
        this.addAbility(ability);
    }

    private WishclawTalisman(final WishclawTalisman card) {
        super(card);
    }

    @Override
    public WishclawTalisman copy() {
        return new WishclawTalisman(this);
    }
}

class WishclawTalismanEffect extends OneShotEffect {

    private static final Effect effect = new SearchLibraryPutInHandEffect(new TargetCardInLibrary(), false);

    WishclawTalismanEffect() {
        super(Outcome.Benefit);
        staticText = "Search your library for a card, put it into your hand, then shuffle. " +
                "An opponent gains control of {this}";
    }

    private WishclawTalismanEffect(final WishclawTalismanEffect effect) {
        super(effect);
    }

    @Override
    public WishclawTalismanEffect copy() {
        return new WishclawTalismanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        effect.apply(game, source);
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPlayer target = new TargetOpponent();
        target.setNotTarget(true);
        if (!player.choose(outcome, target, source, game)) {
            return false;
        }
        ContinuousEffect continuousEffect
                = new GainControlTargetEffect(Duration.Custom, true, target.getFirstTarget());
        continuousEffect.setTargetPointer(new FixedTarget(source.getSourceId(), game));
        game.addEffect(continuousEffect, source);
        return true;
    }
}
