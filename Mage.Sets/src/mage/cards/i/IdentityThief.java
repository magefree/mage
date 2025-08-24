package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class IdentityThief extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("target nontoken creature");

    static {
        filter.add(AnotherPredicate.instance);
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
        Ability ability = new AttacksTriggeredAbility(new IdentityThiefEffect(), true);
        ability.addTarget(new TargetPermanent(0, 1, filter));
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

    private IdentityThiefAbility(final IdentityThiefAbility ability) {
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

    IdentityThiefEffect() {
        super(Outcome.Detriment);
        staticText = "you may exile another target nontoken creature. If you do, {this} becomes a copy of that creature until end of turn. "
                + "Return the exiled card to the battlefield under its owner's control at the beginning of the next end step";
    }

    private IdentityThiefEffect(final IdentityThiefEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller == null || targetPermanent == null) {
            return false;
        }
        controller.moveCardsToExile(
                targetPermanent, source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, true)
                        .setText("Return the exiled card to the battlefield under its owner's control at the beginning of the next end step")
                        .setTargetPointer(new FixedTarget(source.getFirstTarget(), game))
        ), source);
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (sourcePermanent != null) {
            game.addEffect(new CopyEffect(
                    Duration.EndOfTurn, targetPermanent, source.getSourceId()
            ).setTargetPointer(new FixedTarget(sourcePermanent.getId(), game)), source);
        }
        return true;
    }

    @Override
    public IdentityThiefEffect copy() {
        return new IdentityThiefEffect(this);
    }
}
