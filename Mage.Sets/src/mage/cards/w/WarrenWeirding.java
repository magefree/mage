package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GoblinRogueToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

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
        staticText = "Target player sacrifices a creature. If a Goblin is sacrificed this way, that player " +
                "creates two 1/1 black Goblin Rogue creature tokens, and those tokens gain haste until end of turn";
    }

    WarrenWeirdingEffect(WarrenWeirdingEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null || game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_CREATURE, player.getId(), source, game
        ) < 1) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE);
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null || !permanent.sacrifice(source, game)) {
            return false;
        }
        if (permanent.hasSubtype(SubType.GOBLIN, game)) {
            Token token = new GoblinRogueToken();
            token.putOntoBattlefield(2, game, source, player.getId());
            game.addEffect(new GainAbilityTargetEffect(
                    HasteAbility.getInstance(), Duration.EndOfTurn
            ).setTargetPointer(new FixedTargets(token, game)), source);
        }
        return true;
    }

    @Override
    public WarrenWeirdingEffect copy() {
        return new WarrenWeirdingEffect(this);
    }
}
