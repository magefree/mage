package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnquenchableFury extends CardImpl {

    public UnquenchableFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature has "Whenever this creature attacks, it deals X damage to defending player, where X is the number of cards in their hand."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(new AttacksTriggeredAbility(
                new UnquenchableFuryEffect(), false, null, SetTargetPointer.PLAYER
        ), AttachmentType.AURA)));

        // When Unquenchable Fury is put into your graveyard from the battlfield, return it to your hand.
        this.addAbility(new UnquenchableFuryTriggeredAbility());
    }

    private UnquenchableFury(final UnquenchableFury card) {
        super(card);
    }

    @Override
    public UnquenchableFury copy() {
        return new UnquenchableFury(this);
    }
}

class UnquenchableFuryEffect extends OneShotEffect {

    UnquenchableFuryEffect() {
        super(Outcome.Benefit);
        staticText = "it deals X damage to defending player, where X is the number of cards in their hand";
    }

    private UnquenchableFuryEffect(final UnquenchableFuryEffect effect) {
        super(effect);
    }

    @Override
    public UnquenchableFuryEffect copy() {
        return new UnquenchableFuryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        return player != null
                && !player.getHand().isEmpty()
                && player.damage(player.getHand().size(), source, game) > 0;
    }
}

class UnquenchableFuryTriggeredAbility extends TriggeredAbilityImpl {

    UnquenchableFuryTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnSourceFromGraveyardToHandEffect());
    }

    private UnquenchableFuryTriggeredAbility(final UnquenchableFuryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public UnquenchableFuryTriggeredAbility copy() {
        return new UnquenchableFuryTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        Permanent permanent = zEvent.getTarget();
        return permanent != null && zEvent.isDiesEvent()
                && permanent.getId().equals(this.getSourceId())
                && permanent.isOwnedBy(permanent.getControllerId());
    }

    @Override
    public String getRule() {
        return "When {this} is put into your graveyard from the battlfield, return it to your hand.";
    }
}
