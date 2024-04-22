package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class NotDeadAfterAll extends CardImpl {

    public NotDeadAfterAll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Until end of turn, target creature you control gains "When this creature dies, return it to the battlefield tapped under its owner's control, then create a Wicked Role token attached to it."
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                new DiesSourceTriggeredAbility(
                        new NotDeadAfterAllEffect()
                ).setTriggerPhrase("When this creature dies, "),
                Duration.EndOfTurn
        ).setText("until end of turn, target creature you control gains \"When this creature dies, "
                + "return it to the battlefield tapped under its owner's control, then create a Wicked "
                + "Role token attached to it.\""));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private NotDeadAfterAll(final NotDeadAfterAll card) {
        super(card);
    }

    @Override
    public NotDeadAfterAll copy() {
        return new NotDeadAfterAll(this);
    }
}

class NotDeadAfterAllEffect extends OneShotEffect {

    NotDeadAfterAllEffect() {
        super(Outcome.Benefit);
        staticText = "return it to the battlefield tapped under its owner's control, "
                + "then create a Wicked Role token attached to it.";
    }

    private NotDeadAfterAllEffect(final NotDeadAfterAllEffect effect) {
        super(effect);
    }

    @Override
    public NotDeadAfterAllEffect copy() {
        return new NotDeadAfterAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (player == null || card == null) {
            return false;
        }
        if (game.getState().getZone(source.getSourceId()) != Zone.GRAVEYARD) {
            return false;
        }

        player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return false;
        }

        return new CreateRoleAttachedTargetEffect(RoleType.WICKED)
                .setTargetPointer(new FixedTarget(permanent, game))
                .apply(game, source);
    }

}