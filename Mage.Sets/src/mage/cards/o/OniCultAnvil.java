package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.ConstructToken;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class OniCultAnvil extends CardImpl {

    public OniCultAnvil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{B}{R}");

        // Whenever one or more artifacts you control leave the battlefield during your turn, create a 1/1 colorless Construct artifact creature token. This ability triggers only once each turn.
        this.addAbility(new OniCultAnvilTriggeredAbility());

        // {T}, Sacrifice an artifact: Oni-Cult Anvil deals 1 damage to each opponent. You gain 1 life.
        Ability ability = new SimpleActivatedAbility(new DamagePlayersEffect(1, TargetController.OPPONENT), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN));
        ability.addEffect(new GainLifeEffect(1));
        this.addAbility(ability);
    }

    private OniCultAnvil(final OniCultAnvil card) {
        super(card);
    }

    @Override
    public OniCultAnvil copy() {
        return new OniCultAnvil(this);
    }
}

class OniCultAnvilTriggeredAbility extends LeavesBattlefieldAllTriggeredAbility {

    public OniCultAnvilTriggeredAbility() {
        super(new CreateTokenEffect(new ConstructToken()), StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACTS);
        this.setTriggersLimitEachTurn(1);
        setTriggerPhrase("Whenever one or more artifacts you control leave the battlefield during your turn, ");
    }

    private OniCultAnvilTriggeredAbility(final OniCultAnvilTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OniCultAnvilTriggeredAbility copy() {
        return new OniCultAnvilTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }

        // during your turn
        return game.isActivePlayer(controllerId);
    }
}
