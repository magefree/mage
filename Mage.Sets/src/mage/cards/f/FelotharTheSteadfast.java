package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderAllEffect;
import mage.abilities.effects.common.ruleModifying.CombatDamageByToughnessControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FelotharTheSteadfast extends CardImpl {

    public FelotharTheSteadfast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Each creature you control assigns combat damage equal to its toughness rather than its power.
        this.addAbility(new SimpleStaticAbility(new CombatDamageByToughnessControlledEffect()));

        // Creatures you control can attack as though they didn't have defender.
        this.addAbility(new SimpleStaticAbility(new CanAttackAsThoughItDidntHaveDefenderAllEffect(
                Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_CREATURES
        )));

        // {3}, {T}, Sacrifice another creature: Draw cards equal to the sacrificed creature's toughness, then discard cards equal to its power.
        Ability ability = new SimpleActivatedAbility(new FelotharTheSteadfastEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_ANOTHER_CREATURE));
        this.addAbility(ability);
    }

    private FelotharTheSteadfast(final FelotharTheSteadfast card) {
        super(card);
    }

    @Override
    public FelotharTheSteadfast copy() {
        return new FelotharTheSteadfast(this);
    }
}

class FelotharTheSteadfastEffect extends OneShotEffect {

    FelotharTheSteadfastEffect() {
        super(Outcome.Benefit);
        staticText = "draw cards equal to the sacrificed creature's toughness, then discard cards equal to its power";
    }

    private FelotharTheSteadfastEffect(final FelotharTheSteadfastEffect effect) {
        super(effect);
    }

    @Override
    public FelotharTheSteadfastEffect copy() {
        return new FelotharTheSteadfastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = CardUtil
                .castStream(source.getCosts(), SacrificeTargetCost.class)
                .filter(Objects::nonNull)
                .map(SacrificeTargetCost::getPermanents)
                .flatMap(Collection::stream)
                .findFirst()
                .orElse(null);
        if (player == null || permanent == null) {
            return false;
        }
        player.drawCards(permanent.getToughness().getValue(), source, game);
        game.processAction();
        player.discard(permanent.getPower().getValue(), false, false, source, game);
        return true;
    }
}
