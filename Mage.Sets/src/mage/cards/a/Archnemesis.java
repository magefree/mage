package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

public final class Archnemesis extends CardImpl {

    public Archnemesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{B}");

        this.subtype.add(SubType.AURA);

        TargetPlayer auraTarget = new TargetOpponent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        Ability attackAbility = new ArchnemesisAttackTriggeredAbility();
        attackAbility.addEffect(new LoseLifeTargetEffect(2));
        attackAbility.addEffect(new DrawCardSourceControllerEffect(1));
        attackAbility.addEffect(new GainLifeEffect(2));
        this.addAbility(attackAbility);

        this.addAbility(new ArchnemesisReattachTriggeredAbility());
    }

    private Archnemesis(final Archnemesis card) {
        super(card);
    }

    @Override
    public Archnemesis copy() {
        return new Archnemesis(this);
    }
}

class ArchnemesisAttackTriggeredAbility extends TriggeredAbilityImpl {

    ArchnemesisAttackTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
        setTriggerPhrase("Whenever you attack enchanted player, that player loses 2 life. You draw a card and gain 2 life.");
    }

    private ArchnemesisAttackTriggeredAbility(final ArchnemesisAttackTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent aura = game.getPermanentOrLKIBattlefield(getSourceId());
        if (aura == null
                || !getControllerId().equals(game.getCombat().getAttackingPlayerId())
                || aura.getAttachedTo() == null
                || !game.getCombat().getPlayerDefenders(game, false).contains(aura.getAttachedTo())) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(aura.getAttachedTo()));
        return true;
    }

    @Override
    public ArchnemesisAttackTriggeredAbility copy() {
        return new ArchnemesisAttackTriggeredAbility(this);
    }
}

class ArchnemesisReattachTriggeredAbility extends TriggeredAbilityImpl {

    ArchnemesisReattachTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ArchnemesisReattachEffect(), false);
        setTriggerPhrase("Whenever a player attacks you, you may attach {this} to that player.");
    }

    private ArchnemesisReattachTriggeredAbility(final ArchnemesisReattachTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID attackingPlayerId = game.getCombat().getAttackingPlayerId();
        if (attackingPlayerId == null
                || !game.getCombat().getPlayerDefenders(game, false).contains(getControllerId())) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(attackingPlayerId));
        return true;
    }

    @Override
    public ArchnemesisReattachTriggeredAbility copy() {
        return new ArchnemesisReattachTriggeredAbility(this);
    }
}

class ArchnemesisReattachEffect extends OneShotEffect {

    ArchnemesisReattachEffect() {
        super(Outcome.Benefit);
        staticText = "you may attach {this} to that player";
    }

    private ArchnemesisReattachEffect(final ArchnemesisReattachEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player attackingPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        Permanent aura = game.getPermanent(source.getSourceId());
        if (player == null || attackingPlayer == null || aura == null
                || !player.chooseUse(outcome, "Attach Archnemesis to that player?", source, game)) {
            return false;
        }
        return aura.addAttachment(attackingPlayer.getId(), source, game);
    }

    @Override
    public ArchnemesisReattachEffect copy() {
        return new ArchnemesisReattachEffect(this);
    }
}
