package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class OraclesVault extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.BRICK, 3);

    public OraclesVault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {2}, {T}: Exile the top card of your library. Until end of turn, you may play that card. Put a brick counter on Oracle's Vault.
        Ability ability = new SimpleActivatedAbility(new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn)
                .withTextOptions("that card", false), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new AddCountersSourceEffect(CounterType.BRICK.createInstance()));
        this.addAbility(ability);

        // {T}: Exile the top card of your library. Until end of turn, you may play that card without paying its mana cost.
        // Activate this ability only if there are three or more brick counters on Oracle's Vault.
        this.addAbility(new ActivateIfConditionActivatedAbility(new OraclesVaultFreeEffect(), new TapSourceCost(), condition));
    }

    private OraclesVault(final OraclesVault card) {
        super(card);
    }

    @Override
    public OraclesVault copy() {
        return new OraclesVault(this);
    }
}

class OraclesVaultFreeEffect extends OneShotEffect {

    OraclesVaultFreeEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of your library. Until end of turn, you may play that card without paying its mana cost";
    }

    private OraclesVaultFreeEffect(final OraclesVaultFreeEffect effect) {
        super(effect);
    }

    @Override
    public OraclesVaultFreeEffect copy() {
        return new OraclesVaultFreeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return PlayFromNotOwnHandZoneTargetEffect.exileAndPlayFromExile(game, source, controller.getLibrary().getFromTop(game),
                    TargetController.YOU, Duration.EndOfTurn, true, false, false);
        }
        return false;
    }
}
