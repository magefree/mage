package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.PowerstoneToken;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetAnyTarget;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class TheBrothersWar extends CardImpl {

    public TheBrothersWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Create two tapped Powerstone tokens.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, new CreateTokenEffect(new PowerstoneToken(), 2, true)
        );

        // II -- Choose two target players. Until your next turn, each creature they control attacks the other chosen player each combat if able.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new TheBrothersWarEffect(), new TargetPlayer(2)
        );

        // III -- The Brothers' War deals X damage to any target and X damage to any other target, where X is the number of artifacts you control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                new DamageTargetEffect(ArtifactYouControlCount.instance)
                        .setText("{this} deals X damage to any target and X damage to any other target, " +
                                "where X is the number of artifacts you control"),
                new TargetAnyTarget(2)
        );
        this.addAbility(sagaAbility.addHint(ArtifactYouControlHint.instance));
    }

    private TheBrothersWar(final TheBrothersWar card) {
        super(card);
    }

    @Override
    public TheBrothersWar copy() {
        return new TheBrothersWar(this);
    }
}

class TheBrothersWarEffect extends OneShotEffect {

    TheBrothersWarEffect() {
        super(Outcome.Benefit);
        staticText = "choose two target players. Until your next turn, each creature " +
                "they control attacks the other chosen player each combat if able";
    }

    private TheBrothersWarEffect(final TheBrothersWarEffect effect) {
        super(effect);
    }

    @Override
    public TheBrothersWarEffect copy() {
        return new TheBrothersWarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Player> players = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (players.size() != 2) {
            return false;
        }
        game.addEffect(new TheBrothersWarRequirementEffect(players.get(0), players.get(1)), source);
        game.addEffect(new TheBrothersWarRequirementEffect(players.get(1), players.get(0)), source);
        return true;
    }
}

class TheBrothersWarRequirementEffect extends RequirementEffect {

    private final UUID attackingPlayerId;
    private final UUID defendingPlayerId;

    public TheBrothersWarRequirementEffect(Player attacker, Player defender) {
        super(Duration.UntilYourNextTurn);
        this.attackingPlayerId = attacker.getId();
        this.defendingPlayerId = defender.getId();
    }

    public TheBrothersWarRequirementEffect(final TheBrothersWarRequirementEffect effect) {
        super(effect);
        this.attackingPlayerId = effect.attackingPlayerId;
        this.defendingPlayerId = effect.defendingPlayerId;
    }

    @Override
    public TheBrothersWarRequirementEffect copy() {
        return new TheBrothersWarRequirementEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.isControlledBy(attackingPlayerId);
    }

    @Override
    public UUID mustAttackDefender(Ability source, Game game) {
        return defendingPlayerId;
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }
}