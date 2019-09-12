package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Library;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SyrCarahTheBold extends CardImpl {

    public SyrCarahTheBold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Syr Carah, the Bold or an instant or sorcery spell you control deals damage to a player, exile the top card of your library. You may play that card this turn.
        this.addAbility(new SyrCarahTheBoldTriggeredAbility());

        // {T}: Syr Carah deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private SyrCarahTheBold(final SyrCarahTheBold card) {
        super(card);
    }

    @Override
    public SyrCarahTheBold copy() {
        return new SyrCarahTheBold(this);
    }
}

class SyrCarahTheBoldTriggeredAbility extends TriggeredAbilityImpl {

    SyrCarahTheBoldTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SyrCarahTheBoldExileEffect(), false);
    }

    private SyrCarahTheBoldTriggeredAbility(final SyrCarahTheBoldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SyrCarahTheBoldTriggeredAbility copy() {
        return new SyrCarahTheBoldTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            return true;
        }
        Spell spell = game.getSpellOrLKIStack(event.getSourceId());
        return spell != null && spell.isInstantOrSorcery()
                && spell.isControlledBy(this.getControllerId());
    }

    @Override
    public String getRule() {
        return "Whenever {this} or an instant or sorcery spell you control deals damage to a player, " +
                "exile the top card of your library. You may play that card this turn.";
    }

}

class SyrCarahTheBoldExileEffect extends OneShotEffect {

    SyrCarahTheBoldExileEffect() {
        super(Outcome.Detriment);
    }

    private SyrCarahTheBoldExileEffect(final SyrCarahTheBoldExileEffect effect) {
        super(effect);
    }

    @Override
    public SyrCarahTheBoldExileEffect copy() {
        return new SyrCarahTheBoldExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent == null || controller == null || !controller.getLibrary().hasCards()) {
            return false;
        }
        Library library = controller.getLibrary();
        Card card = library.getFromTop(game);
        if (card == null) {
            return true;
        }
        String exileName = sourcePermanent.getIdName() + " <this card may be played the turn it was exiled>";
        controller.moveCardsToExile(card, source, game, true, source.getSourceId(), exileName);
        ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect(Duration.EndOfTurn);
        effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game)));
        game.addEffect(effect, source);
        return true;
    }
}

class SyrCarahTheBoldCastFromExileEffect extends AsThoughEffectImpl {

    SyrCarahTheBoldCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
    }

    private SyrCarahTheBoldCastFromExileEffect(final SyrCarahTheBoldCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SyrCarahTheBoldCastFromExileEffect copy() {
        return new SyrCarahTheBoldCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId)
                && objectId.equals(getTargetPointer().getFirst(game, source));
    }
}
