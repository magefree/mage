package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;

/**
 *
 * @author TheElk801
 */
public final class WhiptongueHydra extends CardImpl {

    public WhiptongueHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Whiptongue Hydra enters the battlefield, destroy all creatures with flying. Put a +1/+1 counter on Whiptongue Hydra for each creature destroyed this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new WhiptongueHydraEffect(), false));
    }

    private WhiptongueHydra(final WhiptongueHydra card) {
        super(card);
    }

    @Override
    public WhiptongueHydra copy() {
        return new WhiptongueHydra(this);
    }
}

class WhiptongueHydraEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public WhiptongueHydraEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy all creatures with flying. "
                + "Put a +1/+1 counter on {this} for each creature destroyed this way";
    }

    public WhiptongueHydraEffect(final WhiptongueHydraEffect effect) {
        super(effect);
    }

    @Override
    public WhiptongueHydraEffect copy() {
        return new WhiptongueHydraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int destroyedPermanents = 0;
        destroyedPermanents = game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        ).stream().filter(
                (permanent) -> (permanent.destroy(source, game, false))
        ).map((_item) -> 1).reduce(destroyedPermanents, Integer::sum);
        if (destroyedPermanents > 0) {
            game.getState().processAction(game);
            new AddCountersSourceEffect(
                    CounterType.P1P1.createInstance(destroyedPermanents), true
            ).apply(game, source);
        }
        return true;
    }
}
