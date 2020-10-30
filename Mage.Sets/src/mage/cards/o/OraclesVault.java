package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class OraclesVault extends CardImpl {

    public OraclesVault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {2}, {T}: Exile the top card of your library. Until end of turn, you may play that card. Put a brick counter on Oracle's Vault.
        Effect effect = new OraclesVaultEffect();
        effect.setText("Exile the top card of your library. Until end of turn, you may play that card");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        Effect effect2 = new AddCountersSourceEffect(CounterType.BRICK.createInstance());
        effect2.setText("Put a brick counter on {this}");
        ability.addEffect(effect2);
        this.addAbility(ability);

        // {T}: Exile the top card of your library. Until end of turn, you may play that card without paying its mana cost.
        // Activate this ability only if there are three or more brick counters on Oracle's Vault.
        this.addAbility(new ConditionalActivatedAbility(Zone.BATTLEFIELD,
                new OraclesVaultFreeEffect(), new TapSourceCost(), new SourceHasCounterCondition(CounterType.BRICK, 3, Integer.MAX_VALUE),
                "{T}: Exile the top card of your library. Until end of turn, you may play that card without paying its mana cost. "
                + "Activate this ability only if there are three or more brick counters on {this}"));
    }

    public OraclesVault(final OraclesVault card) {
        super(card);
    }

    @Override
    public OraclesVault copy() {
        return new OraclesVault(this);
    }
}

class OraclesVaultEffect extends OneShotEffect {

    public OraclesVaultEffect() {
        super(Outcome.Benefit);
    }

    public OraclesVaultEffect(final OraclesVaultEffect effect) {
        super(effect);
    }

    @Override
    public OraclesVaultEffect copy() {
        return new OraclesVaultEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return PlayFromNotOwnHandZoneTargetEffect.exileAndPlayFromExile(game, source, controller.getLibrary().getFromTop(game),
                    TargetController.YOU, Duration.EndOfTurn, false);
        }
        return false;
    }
}

class OraclesVaultFreeEffect extends OneShotEffect {

    public OraclesVaultFreeEffect() {
        super(Outcome.Benefit);
    }

    public OraclesVaultFreeEffect(final OraclesVaultFreeEffect effect) {
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
                    TargetController.YOU, Duration.EndOfTurn, true);            
        }
        return false;
    }
}
