
package mage.cards.w;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GoblinRogueToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX2
 */
public final class WarrenWeirding extends CardImpl {

    public WarrenWeirding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.SORCERY}, "{1}{B}");
        this.subtype.add(SubType.GOBLIN);


        // Target player sacrifices a creature. If a Goblin is sacrificed this way, that player creates two 1/1 black Goblin Rogue creature tokens, and those tokens gain haste until end of turn.
        this.getSpellAbility().addEffect(new WarrenWeirdingEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private WarrenWeirding(final WarrenWeirding card) {
        super(card);
    }

    @Override
    public WarrenWeirding copy() {
        return new WarrenWeirding(this);
    }
}

class WarrenWeirdingEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filterGoblin = new FilterCreaturePermanent();

    static {
        filterGoblin.add(SubType.GOBLIN.getPredicate());
    }

    WarrenWeirdingEffect() {
        super(Outcome.Sacrifice);
        staticText = "Target player sacrifices a creature. If a Goblin is sacrificed this way, that player creates two 1/1 black Goblin Rogue creature tokens, and those tokens gain haste until end of turn";
    }

    WarrenWeirdingEffect(WarrenWeirdingEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        FilterControlledPermanent filter = new FilterControlledPermanent("creature");
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(new ControllerIdPredicate(player.getId()));
        TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, true);

        //A spell or ability could have removed the only legal target this player
        //had, if thats the case this ability should fizzle.
        if (target.canChoose(player.getId(), source, game)) {
            player.choose(Outcome.Sacrifice, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                permanent.sacrifice(source, game);
                if (filterGoblin.match(permanent, game)) {
                    for (int i = 0; i < 2; i++) {
                        Token token = new GoblinRogueToken();
                        Effect effect = new CreateTokenTargetEffect(token);
                        effect.setTargetPointer(new FixedTarget(player.getId()));
                        if (effect.apply(game, source)) {
                            Permanent tokenPermanent = game.getPermanent(token.getLastAddedToken());
                            if (tokenPermanent != null) {
                                ContinuousEffect hasteEffect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
                                hasteEffect.setTargetPointer(new FixedTarget(tokenPermanent.getId()));
                                game.addEffect(hasteEffect, source);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public WarrenWeirdingEffect copy() {
        return new WarrenWeirdingEffect(this);
    }

}
