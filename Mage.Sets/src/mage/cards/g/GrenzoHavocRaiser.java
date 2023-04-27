package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801, LevelX2
 */
public final class GrenzoHavocRaiser extends CardImpl {

    static final String goadEffectName = "goad target creature that player controls";

    public GrenzoHavocRaiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{R}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a creature you control deals combat damage to a player, choose one &mdash;
        //Goad target creature that player controls;
        Effect effect = new GoadTargetEffect();
        effect.setText(goadEffectName);
        Ability ability = new GrenzoHavocRaiserTriggeredAbility(effect);
        //or Exile the top card of that player's library. Until end of turn, you may cast that card and you may spend mana as though it were mana of any color to cast it.
        Mode mode = new Mode(new GrenzoHavocRaiserEffect());
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private GrenzoHavocRaiser(final GrenzoHavocRaiser card) {
        super(card);
    }

    @Override
    public GrenzoHavocRaiser copy() {
        return new GrenzoHavocRaiser(this);
    }
}

class GrenzoHavocRaiserTriggeredAbility extends TriggeredAbilityImpl {

    String damagedPlayerName = null;

    public GrenzoHavocRaiserTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever a creature you control deals combat damage to a player, ");
    }

    public GrenzoHavocRaiserTriggeredAbility(final GrenzoHavocRaiserTriggeredAbility ability) {
        super(ability);
        this.damagedPlayerName = ability.damagedPlayerName;
    }

    @Override
    public GrenzoHavocRaiserTriggeredAbility copy() {
        return new GrenzoHavocRaiserTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        this.damagedPlayerName = null;
        this.getEffects().get(0).setText(GrenzoHavocRaiser.goadEffectName);

        Player damagedPlayer = game.getPlayer(event.getPlayerId());
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        Permanent abilitySourcePermanent = this.getSourcePermanentIfItStillExists(game);
        if (damagedPlayer == null || permanent == null || abilitySourcePermanent == null) {
            return false;
        }

        if (((DamagedEvent) event).isCombatDamage() && isControlledBy(permanent.getControllerId())) {

            this.damagedPlayerName = damagedPlayer.getLogName();
            this.getEffects().get(0).setText(GrenzoHavocRaiser.goadEffectName + " (" + this.damagedPlayerName + ")");
            game.informPlayers(abilitySourcePermanent.getLogName() + " triggered for damaged " + this.damagedPlayerName);

            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature " + damagedPlayer.getLogName() + " controls");
            filter.add(new ControllerIdPredicate(damagedPlayer.getId()));
            this.getTargets().clear();
            this.addTarget(new TargetCreaturePermanent(filter));
            for (Effect effect : this.getAllEffects()) {
                if (effect instanceof GrenzoHavocRaiserEffect) {
                    effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                    effect.setValue("damage", event.getAmount());
                }
            }
            return true;
        }
        return false;
    }
}

class GrenzoHavocRaiserEffect extends OneShotEffect {

    public GrenzoHavocRaiserEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "exile the top card of that player's library. Until end of turn, you may cast that card and you may spend mana as though it were mana of any color to cast it";
    }

    public GrenzoHavocRaiserEffect(final GrenzoHavocRaiserEffect effect) {
        super(effect);
    }

    @Override
    public GrenzoHavocRaiserEffect copy() {
        return new GrenzoHavocRaiserEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player damagedPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
            if (damagedPlayer != null) {
                MageObject sourceObject = game.getObject(source);
                UUID exileId = CardUtil.getCardExileZoneId(game, source);
                Card card = damagedPlayer.getLibrary().getFromTop(game);
                if (card != null && sourceObject != null) {
                    // move card to exile
                    controller.moveCardToExileWithInfo(card, exileId, sourceObject.getIdName(), source, game, Zone.LIBRARY, true);
                    // Add effects only if the card has a spellAbility (e.g. not for lands).
                    if (card.getSpellAbility() != null) {
                        // allow to cast the card
                        // and you may spend mana as though it were mana of any color to cast it
                        CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn, true);
                    }
                }
                return true;
            }
        }
        return false;
    }
}