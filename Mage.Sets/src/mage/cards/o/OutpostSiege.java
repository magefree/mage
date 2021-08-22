
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.ZoneChangeAllTriggeredAbility;
import mage.abilities.condition.common.ModeChoiceSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class OutpostSiege extends CardImpl {

    private static final String ruleTrigger1 = "&bull  Khans &mdash; At the beginning of your upkeep, exile the top card of your library. Until end of turn, you may play that card.";
    private static final String ruleTrigger2 = "&bull  Dragons &mdash; Whenever a creature you control leaves the battlefield, {this} deals 1 damage to any target.";

    public OutpostSiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}");

        // As Outpost Siege enters the battlefield, choose Khans or Dragons.
        this.addAbility(new EntersBattlefieldAbility(new ChooseModeEffect("Khans or Dragons?", "Khans", "Dragons"), null,
                "As {this} enters the battlefield, choose Khans or Dragons.", ""));

        // * Khans - At the beginning of your upkeep, exile the top card of your library. Until end of turn, you may play that card.
        this.addAbility(new ConditionalTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new OutpostSiegeExileEffect(), TargetController.YOU, false),
                new ModeChoiceSourceCondition("Khans"),
                ruleTrigger1));

        // * Dragons - Whenever a creature you control leaves the battlefield, Outpost Siege deals 1 damage to any target.
        Ability ability2 = new ConditionalTriggeredAbility(
                new ZoneChangeAllTriggeredAbility(Zone.BATTLEFIELD, Zone.BATTLEFIELD, null, new DamageTargetEffect(1),
                        new FilterControlledCreaturePermanent(), "", false),
                new ModeChoiceSourceCondition("Dragons"),
                ruleTrigger2);
        ability2.addTarget(new TargetAnyTarget());
        this.addAbility(ability2);

    }

    private OutpostSiege(final OutpostSiege card) {
        super(card);
    }

    @Override
    public OutpostSiege copy() {
        return new OutpostSiege(this);
    }
}

class OutpostSiegeExileEffect extends OneShotEffect {

    public OutpostSiegeExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile the top card of your library. Until end of turn, you may play that card";
    }

    public OutpostSiegeExileEffect(final OutpostSiegeExileEffect effect) {
        super(effect);
    }

    @Override
    public OutpostSiegeExileEffect copy() {
        return new OutpostSiegeExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                String exileName = sourcePermanent.getIdName() + " <this card may be played the turn it was exiled";
                controller.moveCardsToExile(card, source, game, true, source.getSourceId(), exileName);
                if (game.getState().getZone(card.getId()) == Zone.EXILED) {
                    ContinuousEffect effect = new CastFromNonHandZoneTargetEffect(Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTarget(card, game));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}

class CastFromNonHandZoneTargetEffect extends AsThoughEffectImpl {

    public CastFromNonHandZoneTargetEffect(Duration duration) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, duration, Outcome.Benefit);
        staticText = "until end of turn, you may play that card";
    }

    public CastFromNonHandZoneTargetEffect(final CastFromNonHandZoneTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CastFromNonHandZoneTargetEffect copy() {
        return new CastFromNonHandZoneTargetEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (getTargetPointer().getTargets(game, source).contains(objectId)
                && source.isControlledBy(affectedControllerId)) {
            Card card = game.getCard(objectId);
            if (card != null) {
                return true;
            }
        }
        return false;
    }
}
