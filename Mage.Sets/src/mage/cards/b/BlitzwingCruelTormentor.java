package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LivingMetalAbility;
import mage.abilities.keyword.MoreThanMeetsTheEyeAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.RandomUtil;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlitzwingCruelTormentor extends TransformingDoubleFacedCard {

    public BlitzwingCruelTormentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.ROBOT}, "{5}{B}",
                "Blitzwing Adaptive Assailant",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.VEHICLE}, "B");


        // Blitzwing, Cruel Tormentor
        this.getLeftHalfCard().setPT(6, 5);

        // More Than Meets the Eye {3}{B}
        this.getLeftHalfCard().addAbility(new MoreThanMeetsTheEyeAbility(this, "{3}{B}"));

        // At the beginning of your end step, target opponent loses life equal to the life that player lost this turn. If no life is lost this way, convert Blitzwing.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new BlitzwingCruelTormentorEffect()
        );
        ability.addTarget(new TargetOpponent());
        this.getLeftHalfCard().addAbility(ability);

        // Blitzwing Adaptive Assailant
        this.getRightHalfCard().setPT(3, 5);

        // Living metal
        this.getRightHalfCard().addAbility(new LivingMetalAbility());

        // At the beginning of combat on your turn, choose flying or indestructible at random. Blitzwing gains that ability until end of turn.
        this.getRightHalfCard().addAbility(new BeginningOfCombatTriggeredAbility(
                new BlitzwingAdaptiveAssailantEffect()
        ));

        // Whenever Blitzwing deals combat damage to a player, convert it.
        this.getRightHalfCard().addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new TransformSourceEffect().setText("convert it"), false
        ));
    }

    private BlitzwingCruelTormentor(final BlitzwingCruelTormentor card) {
        super(card);
    }

    @Override
    public BlitzwingCruelTormentor copy() {
        return new BlitzwingCruelTormentor(this);
    }
}

class BlitzwingCruelTormentorEffect extends OneShotEffect {

    BlitzwingCruelTormentorEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent loses life equal to the life that player " +
                "lost this turn. If no life is lost this way, convert {this}";
    }

    private BlitzwingCruelTormentorEffect(final BlitzwingCruelTormentorEffect effect) {
        super(effect);
    }

    @Override
    public BlitzwingCruelTormentorEffect copy() {
        return new BlitzwingCruelTormentorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null) {
            int lifeLost = game.getState().getWatcher(PlayerLostLifeWatcher.class).getLifeLost(player.getId());
            if (lifeLost > 0 && player.loseLife(lifeLost, game, source, false) > 0) {
                return true;
            }
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return permanent != null && permanent.transform(source, game);
    }
}

class BlitzwingAdaptiveAssailantEffect extends OneShotEffect {

    BlitzwingAdaptiveAssailantEffect() {
        super(Outcome.Benefit);
        staticText = "choose flying or indestructible at random. {this} gains that ability until end of turn";
    }

    private BlitzwingAdaptiveAssailantEffect(final BlitzwingAdaptiveAssailantEffect effect) {
        super(effect);
    }

    @Override
    public BlitzwingAdaptiveAssailantEffect copy() {
        return new BlitzwingAdaptiveAssailantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Ability ability = RandomUtil.nextBoolean() ? FlyingAbility.getInstance() : IndestructibleAbility.getInstance();
        game.informPlayers(ability.getRule() + " has been chosen");
        game.addEffect(new GainAbilitySourceEffect(ability, Duration.EndOfTurn), source);
        return true;
    }
}
