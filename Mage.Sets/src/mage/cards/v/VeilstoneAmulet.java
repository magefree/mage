
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author duncant
 */
public final class VeilstoneAmulet extends CardImpl {

    public VeilstoneAmulet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // Whenever you cast a spell, creatures you control can't be the targets of spells or abilities your opponents control this turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new VeilstoneAmuletEffect(), StaticFilters.FILTER_SPELL_A, false));
    }

    private VeilstoneAmulet(final VeilstoneAmulet card) {
        super(card);
    }

    @Override
    public VeilstoneAmulet copy() {
        return new VeilstoneAmulet(this);
    }
}

// Veilstone Amulet's effect is strange. It effects all creatures you control,
// even if they entered the battlefield after the ability resolved. It modifies
// the rules of the game until end of turn.
class VeilstoneAmuletEffect extends ContinuousRuleModifyingEffectImpl {

    public VeilstoneAmuletEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "creatures you control can't be the targets of spells or abilities your opponents control this turn";
    }

    private VeilstoneAmuletEffect(final VeilstoneAmuletEffect effect) {
        super(effect);
    }

    @Override
    public VeilstoneAmuletEffect copy() {
        return new VeilstoneAmuletEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGET;
    }

    @Override
    public boolean applies(GameEvent event, Ability ability, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            if (permanent.isCreature(game) &&
                permanent.isControlledBy(ability.getControllerId()) &&
                game.getPlayer(ability.getControllerId()).hasOpponent(event.getPlayerId(), game)) {
                return true;
            }
        }
        return false;
    }
}
