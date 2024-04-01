package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.SpreeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FinalShowdown extends CardImpl {

    public FinalShowdown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Spree
        this.addAbility(new SpreeAbility(this));

        // + {1} -- All creatures lose all abilities until end of turn.
        this.getSpellAbility().addEffect(new LoseAllAbilitiesAllEffect(
                StaticFilters.FILTER_PERMANENT_CREATURES, Duration.EndOfTurn
        ).setText("all creatures lose all abilities until end of turn"));
        this.getSpellAbility().withFirstModeCost(new GenericManaCost(1));

        // + {1} -- Choose a creature you control. It gains indestructible until end of turn.
        this.getSpellAbility().addMode(new Mode(new FinalShowdownEffect()).withCost(new GenericManaCost(1)));

        // + {3}{W}{W} -- Destroy all creatures.
        this.getSpellAbility().addMode(new Mode(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES))
                .withCost(new ManaCostsImpl<>("{3}{W}{W}")));
    }

    private FinalShowdown(final FinalShowdown card) {
        super(card);
    }

    @Override
    public FinalShowdown copy() {
        return new FinalShowdown(this);
    }
}

class FinalShowdownEffect extends OneShotEffect {

    FinalShowdownEffect() {
        super(Outcome.Benefit);
        staticText = "choose a creature you control. It gains indestructible until end of turn";
    }

    private FinalShowdownEffect(final FinalShowdownEffect effect) {
        super(effect);
    }

    @Override
    public FinalShowdownEffect copy() {
        return new FinalShowdownEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !game.getBattlefield().contains(
                StaticFilters.FILTER_CONTROLLED_CREATURE, source, game, 1
        )) {
            return false;
        }
        TargetPermanent target = new TargetControlledCreaturePermanent();
        target.withNotTarget(true);
        player.choose(outcome, target, source, game);
        game.addEffect(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ).setTargetPointer(new FixedTarget(target.getFirstTarget(), game)), source);
        return true;
    }
}
