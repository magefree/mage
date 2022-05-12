package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.keyword.AscendEffect;
import mage.abilities.hint.common.CitysBlessingHint;
import mage.abilities.hint.common.PermanentsYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class VonasHunger extends CardImpl {

    public VonasHunger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Ascend (If you control ten or more permanents, you get the city's blessing for the rest of the game.)
        this.getSpellAbility().addEffect(new AscendEffect());
        this.getSpellAbility().addHint(CitysBlessingHint.instance);
        this.getSpellAbility().addHint(PermanentsYouControlHint.instance);

        // Each opponent sacrifices a creature.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_A_CREATURE),
                new InvertCondition(CitysBlessingCondition.instance),
                "Each opponent sacrifices a creature"));
        // If you have the city's blessing, instead each opponent sacrifices half the creatures they control rounded up.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new VonasHungerEffect(),
                CitysBlessingCondition.instance,
                "If you have the city's blessing, instead each opponent sacrifices half the creatures they control rounded up"));
    }

    private VonasHunger(final VonasHunger card) {
        super(card);
    }

    @Override
    public VonasHunger copy() {
        return new VonasHunger(this);
    }
}

class VonasHungerEffect extends OneShotEffect {

    public VonasHungerEffect() {
        super(Outcome.Sacrifice);
    }

    public VonasHungerEffect(final VonasHungerEffect effect) {
        super(effect);
    }

    @Override
    public VonasHungerEffect copy() {
        return new VonasHungerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> perms = new ArrayList<>();
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                int numTargets = (game.getBattlefield().countAll(StaticFilters.FILTER_CONTROLLED_CREATURE, player.getId(), game) + 1) / 2;
                if (numTargets > 0) {
                    TargetPermanent target = new TargetPermanent(numTargets, numTargets, StaticFilters.FILTER_CONTROLLED_CREATURE, true);
                    if (target.canChoose(player.getId(), source, game)) {
                        player.chooseTarget(Outcome.Sacrifice, target, source, game);
                        perms.addAll(target.getTargets());
                    }
                }
            }
        }
        for (UUID permID : perms) {
            Permanent permanent = game.getPermanent(permID);
            if (permanent != null) {
                permanent.sacrifice(source, game);
            }
        }
        return true;
    }
}
