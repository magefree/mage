package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MarisiBreakerOfTheCoil extends CardImpl {

    public MarisiBreakerOfTheCoil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Your opponents can't cast spells during combat.
        this.addAbility(new SimpleStaticAbility(new MarisiBreakerOfTheCoilSpellEffect()));

        // Whenever a creature you control deals combat damage to a player, goad each creature that player controls
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new MarisiBreakerOfTheCoilEffect(), StaticFilters.FILTER_CONTROLLED_A_CREATURE,
                false, null, true, true
        ));
    }

    private MarisiBreakerOfTheCoil(final MarisiBreakerOfTheCoil card) {
        super(card);
    }

    @Override
    public MarisiBreakerOfTheCoil copy() {
        return new MarisiBreakerOfTheCoil(this);
    }
}

class MarisiBreakerOfTheCoilSpellEffect extends ContinuousRuleModifyingEffectImpl {

    MarisiBreakerOfTheCoilSpellEffect() {
        super(Duration.WhileOnBattlefield, Outcome.PreventCast);
        staticText = "your opponents can't cast spells during combat";
    }

    private MarisiBreakerOfTheCoilSpellEffect(final MarisiBreakerOfTheCoilSpellEffect effect) {
        super(effect);
    }

    @Override
    public MarisiBreakerOfTheCoilSpellEffect copy() {
        return new MarisiBreakerOfTheCoilSpellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.getPhase().getType() == TurnPhase.COMBAT
                && game.getOpponents(source.getControllerId()).contains(event.getPlayerId());
    }
}

class MarisiBreakerOfTheCoilEffect extends OneShotEffect {

    MarisiBreakerOfTheCoilEffect() {
        super(Outcome.Benefit);
        staticText = "goad each creature that player controls "
                + "<i>(Until your next turn, those creatures attack each combat if able and attack a player other than you if able.)</i>";
    }

    private MarisiBreakerOfTheCoilEffect(final MarisiBreakerOfTheCoilEffect effect) {
        super(effect);
    }

    @Override
    public MarisiBreakerOfTheCoilEffect copy() {
        return new MarisiBreakerOfTheCoilEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID playerId = getTargetPointer().getFirst(game, source);
        if (playerId == null) {
            return false;
        }
        game.addEffect(new GoadTargetEffect().setTargetPointer(new FixedTargets(
                game.getBattlefield().getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE, playerId, source, game
                ), game
        )), source);
        return true;
    }
}
