package mage.cards.s;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class SurgeOfSalvation extends CardImpl {

    public SurgeOfSalvation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // You and permanents you control gain hexproof until end of turn. Prevent all damage that black and/or red sources would deal to creatures you control this turn.
        this.getSpellAbility().addEffect(new GainAbilityControllerEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn
        ).setText("you"));this.getSpellAbility().addEffect(new GainAbilityControlledEffect(HexproofAbility.getInstance(),Duration.EndOfTurn).concatBy("and"));this.getSpellAbility().addEffect(new SurgeOfSalvationEffect());
    }

    private SurgeOfSalvation(final SurgeOfSalvation card) {
        super(card);
    }

    @Override
    public SurgeOfSalvation copy() {
        return new SurgeOfSalvation(this);
    }
}

class SurgeOfSalvationEffect extends PreventionEffectImpl {

    SurgeOfSalvationEffect() {
        super(Duration.EndOfTurn,Integer.MAX_VALUE,false);
        staticText = "Prevent all damage that black and/or red sources would deal to creatures you control this turn";
    }

    private SurgeOfSalvationEffect(final SurgeOfSalvationEffect effect) {
        super(effect);
    }

    @Override
    public SurgeOfSalvationEffect copy() {
        return new SurgeOfSalvationEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType()== GameEvent.EventType.DAMAGE_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if(! super.applies(event, source, game)){
            return false;
        }
        Permanent permanent=game.getPermanent(event.getTargetId());
        MageObject sourceObject=game.getObject(event.getSourceId());
        return permanent != null
                && sourceObject != null
                && permanent.isCreature(game)
                && permanent.isControlledBy(source.getControllerId())
                && (sourceObject.getColor(game).isBlack() || sourceObject.getColor(game).isRed());
    }
}
