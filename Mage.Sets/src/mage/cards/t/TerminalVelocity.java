package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TerminalVelocity extends CardImpl {

    public TerminalVelocity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        // You may put an artifact or creature card from your hand onto the battlefield. That permanent gains haste, "When this permanent leaves the battlefield, it deals damage equal to its mana value to each creature," and "At the beginning of your end step, sacrifice this permanent."
        this.getSpellAbility().addEffect(new TerminalVelocityEffect());
    }

    private TerminalVelocity(final TerminalVelocity card) {
        super(card);
    }

    @Override
    public TerminalVelocity copy() {
        return new TerminalVelocity(this);
    }
}

class TerminalVelocityEffect extends OneShotEffect {

    TerminalVelocityEffect() {
        super(Outcome.Benefit);
        staticText = "you may put an artifact or creature card from your hand onto the battlefield. " +
                "That permanent gains haste, \"When this permanent leaves the battlefield, " +
                "it deals damage equal to its mana value to each creature,\" and " +
                "\"At the beginning of your end step, sacrifice this permanent.\"";
    }

    private TerminalVelocityEffect(final TerminalVelocityEffect effect) {
        super(effect);
    }

    @Override
    public TerminalVelocityEffect copy() {
        return new TerminalVelocityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInHand(0, 1, StaticFilters.FILTER_CARD_ARTIFACT_OR_CREATURE);
        player.choose(Outcome.PutCardInPlay, player.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
        if (permanent == null) {
            return true;
        }
        game.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.Custom
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        game.addEffect(new GainAbilityTargetEffect(
                new LeavesBattlefieldTriggeredAbility(new TerminalVelocityDamageEffect())
                        .setTriggerPhrase("When this permanent leaves the battlefield, "), Duration.Custom
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        game.addEffect(new GainAbilityTargetEffect(
                new BeginningOfEndStepTriggeredAbility(new SacrificeSourceEffect()
                        .setText("sacrifice this permanent")), Duration.Custom
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}

class TerminalVelocityDamageEffect extends OneShotEffect {

    TerminalVelocityDamageEffect() {
        super(Outcome.Benefit);
        staticText = "it deals damage equal to its mana value to each creature";
    }

    private TerminalVelocityDamageEffect(final TerminalVelocityDamageEffect effect) {
        super(effect);
    }

    @Override
    public TerminalVelocityDamageEffect copy() {
        return new TerminalVelocityDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent == null || permanent.getManaValue() < 1) {
            return false;
        }
        for (Permanent creature : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game
        )) {
            creature.damage(permanent.getManaValue(), permanent.getId(), source, game);
        }
        return true;
    }
}
