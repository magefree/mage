package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class ForgeOfHeroes extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("commander that entered the battlefield this turn");

    static {
        filter.add(CommanderPredicate.instance);
        filter.add(EnteredThisTurnPredicate.instance);
    }

    public ForgeOfHeroes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Choose target commander that entered the battlefield this turn. Put a +1/+1 counter on it if it's a creature and a loyalty counter on it if it's a planeswalker.
        Ability ability = new SimpleActivatedAbility(
                new ForgeOfHeroesEffect(),
                new TapSourceCost()
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ForgeOfHeroes(final ForgeOfHeroes card) {
        super(card);
    }

    @Override
    public ForgeOfHeroes copy() {
        return new ForgeOfHeroes(this);
    }
}

class ForgeOfHeroesEffect extends OneShotEffect {

    public ForgeOfHeroesEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose target commander that entered the battlefield this turn. "
                + "Put a +1/+1 counter on it if it's a creature "
                + "and a loyalty counter on it if it's a planeswalker";
    }

    private ForgeOfHeroesEffect(final ForgeOfHeroesEffect effect) {
        super(effect);
    }

    @Override
    public ForgeOfHeroesEffect copy() {
        return new ForgeOfHeroesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        if (permanent.isCreature(game)) {
            new AddCountersTargetEffect(
                    CounterType.P1P1.createInstance()
            ).apply(game, source);
        } else if (permanent.isPlaneswalker(game)) {
            new AddCountersTargetEffect(
                    CounterType.LOYALTY.createInstance()
            ).apply(game, source);
        } else {
            return false;
        }
        return true;
    }
}
