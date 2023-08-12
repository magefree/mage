package mage.cards.m;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MountDoom extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledArtifactPermanent("a legendary artifact");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public MountDoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);

        // {T}, Pay 1 life: Add {B} or {R}.
        Ability ability = new BlackManaAbility();
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
        ability = new RedManaAbility();
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);

        // {1}{B}{R}, {T}: Mount Doom deals 1 damage to each opponent.
        ability = new SimpleActivatedAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT), new ManaCostsImpl<>("{1}{B}{R}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {5}{B}{R}, {T}, Sacrifice Mount Doom and a legendary artifact: Choose up to two creatures, then destroy the rest. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(new MountDoomEffect(), new ManaCostsImpl<>("{5}{B}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new CompositeCost(
                new SacrificeSourceCost(),
                new SacrificeTargetCost(filter),
                "sacrifice {this} and a legendary artifact"
        ));
        this.addAbility(ability);
    }

    private MountDoom(final MountDoom card) {
        super(card);
    }

    @Override
    public MountDoom copy() {
        return new MountDoom(this);
    }
}

class MountDoomEffect extends OneShotEffect {

    MountDoomEffect() {
        super(Outcome.Benefit);
        staticText = "choose up to two creatures, then destroy the rest";
    }

    private MountDoomEffect(final MountDoomEffect effect) {
        super(effect);
    }

    @Override
    public MountDoomEffect copy() {
        return new MountDoomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetCreaturePermanent(0, 2);
        target.setNotTarget(true);
        target.withChooseHint("the rest will be destroyed");
        player.choose(outcome, target, source, game);
        FilterPermanent filter = new FilterCreaturePermanent();
        target.getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(MageItem::getId)
                .map(PermanentIdPredicate::new)
                .map(Predicates::not)
                .forEach(filter::add);
        return new DestroyAllEffect(filter).apply(game, source);
    }
}
