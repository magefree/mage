package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffect;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author markort147
 */
public final class NowhereToRun extends CardImpl {

    public NowhereToRun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Nowhere to Run enters, target creature an opponent controls gets -3/-3 until end of turn.
        Ability etbAbility = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(-3, -3, Duration.EndOfTurn));
        etbAbility.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(etbAbility);

        // Creatures your opponents control can be the targets of spells and abilities as though they didn't have hexproof. Ward abilities of those creatures don't trigger.
        Ability staticAbility = new SimpleStaticAbility(new NowhereToRunHexproofEffect());
        staticAbility.addEffect(new NowhereToRunWardEffect());
        this.addAbility(staticAbility);
    }

    private NowhereToRun(final NowhereToRun card) {
        super(card);
    }

    @Override
    public NowhereToRun copy() {
        return new NowhereToRun(this);
    }
}

class NowhereToRunHexproofEffect extends AsThoughEffectImpl {

    NowhereToRunHexproofEffect() {
        super(AsThoughEffectType.HEXPROOF, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Creatures your opponents control "
                + "can be the targets of spells and "
                + "abilities as though they didn't "
                + "have hexproof.";
    }

    private NowhereToRunHexproofEffect(final NowhereToRunHexproofEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            Permanent creature = game.getPermanent(sourceId);
            return creature != null
                    && creature.isCreature(game)
                    && game.getOpponents(source.getControllerId()).contains(creature.getControllerId());
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public AsThoughEffect copy() {
        return new NowhereToRunHexproofEffect(this);
    }
}

class NowhereToRunWardEffect extends ContinuousRuleModifyingEffectImpl {


    NowhereToRunWardEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Ward abilities of those creatures don't trigger.";
    }

    private NowhereToRunWardEffect(final NowhereToRunWardEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType().equals(GameEvent.EventType.NUMBER_OF_TRIGGERS);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent == null || !permanent.isCreature(game)) {
            return false;
        }
        if (!game.getOpponents(source.getControllerId()).contains(permanent.getControllerId())) {
            return false;
        }

        return getValue("targetAbility") instanceof WardAbility;
    }

    @Override
    public ContinuousEffect copy() {
        return new NowhereToRunWardEffect(this);
    }
}
