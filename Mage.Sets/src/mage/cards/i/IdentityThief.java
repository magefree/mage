package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.util.CardUtil;

/**
 * @author spjspj
 */
public final class IdentityThief extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("target nontoken creature");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public IdentityThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Whenever Identity Thief attacks, you may exile another target nontoken creature.
        //   If you do, Identity Thief becomes a copy of that creature until end of turn.
        //   Return the exiled card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new IdentityThiefAbility();
        ability.addTarget(new TargetCreaturePermanent(0, 1, filter, false));
        this.addAbility(ability);
    }

    private IdentityThief(final IdentityThief card) {
        super(card);
    }

    @Override
    public IdentityThief copy() {
        return new IdentityThief(this);
    }
}

class IdentityThiefAbility extends TriggeredAbilityImpl {

    public IdentityThiefAbility() {
        super(Zone.BATTLEFIELD, null, true);
        this.addEffect(new IdentityThiefEffect());
        setTriggerPhrase("Whenever {this} attacks, ");
    }

    public IdentityThiefAbility(final IdentityThiefAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public IdentityThiefAbility copy() {
        return new IdentityThiefAbility(this);
    }
}

class IdentityThiefEffect extends OneShotEffect {

    public IdentityThiefEffect() {
        super(Outcome.Detriment);
        staticText = "you may exile another target nontoken creature. If you do, {this} becomes a copy of that creature until end of turn. "
                + "Return the exiled card to the battlefield under its owner's control at the beginning of the next end step";
    }

    public IdentityThiefEffect(final IdentityThiefEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetPermanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null
                && targetPermanent != null
                && sourcePermanent != null) {
            ContinuousEffect copyEffect = new CopyEffect(Duration.EndOfTurn, targetPermanent, source.getSourceId());
            copyEffect.setTargetPointer(new FixedTarget(sourcePermanent.getId(), game));
            game.addEffect(copyEffect, source);
            UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
            if (controller.moveCardsToExile(targetPermanent, source, game, true, exileZoneId, sourcePermanent.getName())) {
                Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, true);
                effect.setText("Return the exiled card to the battlefield under its owner's control at the beginning of the next end step");
                effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
            }
            return true;
        }
        return false;
    }

    @Override
    public IdentityThiefEffect copy() {
        return new IdentityThiefEffect(this);
    }
}
