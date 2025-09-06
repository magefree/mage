package mage.cards.b;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.abilities.keyword.ModularAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

/**
 *
 * @author anonymous
 */
public final class BlasterMoraleBooster extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("another target artifact");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public BlasterMoraleBooster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.color.setRed(true);
        this.color.setGreen(true);
        this.nightCard = true;

        // Modular 3
        this.addAbility(new ModularAbility(this, 3));

        // {X}, {T}: Move X +1/+1 counters from Blaster onto another target artifact. That artifact gains haste until end of turn. If Blaster has no +1/+1 counters on it, convert it. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new BlasterMoraleBoosterEffect(), new ManaCostsImpl<>("{X}"));
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setText(""));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private BlasterMoraleBooster(final BlasterMoraleBooster card) {
        super(card);
    }

    @Override
    public BlasterMoraleBooster copy() {
        return new BlasterMoraleBooster(this);
    }
}

class BlasterMoraleBoosterEffect extends OneShotEffect {

    BlasterMoraleBoosterEffect() {
        super(Outcome.Benefit);
        staticText = "Move X +1/+1 counters from {this} onto another target artifact. " +
                "That artifact gains haste until end of turn. " +
                "If Blaster has no +1/+1 counters on it, convert it.";
    }

    private BlasterMoraleBoosterEffect(final BlasterMoraleBoosterEffect effect) {
        super(effect);
    }

    @Override
    public BlasterMoraleBoosterEffect copy() {
        return new BlasterMoraleBoosterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent toPermanent = game.getPermanent(source.getFirstTarget());
        Permanent fromPermanent = game.getPermanent(source.getSourceId());
        if (fromPermanent == null
                || toPermanent == null
                || controller == null
                || !fromPermanent.getCounters(game).containsKey(CounterType.P1P1)) {
            return false;
        }

        // Remove X +1/+1 counters, or all of them.
        int countersToMove = Math.min(
            CardUtil.getSourceCostsTag(game, source, "X", 0),
            fromPermanent.getCounters(game).getCount(CounterType.P1P1.getName())
        );
        fromPermanent.removeCounters(CounterType.P1P1.getName(), countersToMove, source, game);
        //put the same number of counters on the other artifact.
        toPermanent.addCounters(CounterType.P1P1.createInstance(countersToMove), source.getControllerId(), source, game);
        if (fromPermanent.getCounters(game).getCount(CounterType.P1P1.getName()) == 0) {
            return fromPermanent.transform(source, game);
        }
        return true;
    }

}
