package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CanBeSacrificedPredicate;
import mage.filter.predicate.permanent.GreatestPowerControlledPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PapalymoTotolymo extends CardImpl {

    public PapalymoTotolymo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever you cast a noncreature spell, Papalymo Totolymo deals 1 damage to each opponent and you gain 1 life.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);

        // {4}, {T}, Sacrifice Papalymo Totolymo: Each opponent who lost life this turn sacrifices a creature with the greatest power among creatures they control.
        ability = new SimpleActivatedAbility(new PapalymoTotolymoEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private PapalymoTotolymo(final PapalymoTotolymo card) {
        super(card);
    }

    @Override
    public PapalymoTotolymo copy() {
        return new PapalymoTotolymo(this);
    }
}

class PapalymoTotolymoEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a creature you control with the greatest power");

    static {
        filter.add(GreatestPowerControlledPredicate.instance);
        filter.add(CanBeSacrificedPredicate.instance);
    }

    PapalymoTotolymoEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent who lost life this turn sacrifices a creature " +
                "with the greatest power among creatures they control";
    }

    private PapalymoTotolymoEffect(final PapalymoTotolymoEffect effect) {
        super(effect);
    }

    @Override
    public PapalymoTotolymoEffect copy() {
        return new PapalymoTotolymoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = new ArrayList<>();
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            if (watcher.getLifeLost(opponentId) < 1) {
                continue;
            }
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null || !game.getBattlefield().contains(filter, opponentId, source, game, 1)) {
                continue;
            }
            TargetPermanent target = new TargetPermanent(filter);
            target.withNotTarget(true);
            target.withChooseHint("to sacrifice");
            opponent.choose(outcome, target, source, game);
            permanents.add(game.getPermanent(target.getFirstTarget()));
        }
        permanents.removeIf(Objects::isNull);
        for (Permanent permanent : permanents) {
            permanent.sacrifice(source, game);
        }
        return true;
    }
}
