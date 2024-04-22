package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.CraftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BraidedNet extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("another target nonland permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public BraidedNet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");
        this.secondSideCardClazz = mage.cards.b.BraidedQuipu.class;

        // Braided Net enters the battlefield with three net counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.NET.createInstance(3)),
                "with three net counters on it"
        ));

        // {T}, Remove a net counter from Braided Net: Tap another target nonland permanent. Its activated abilities can't be activated for as long as it remains tapped.
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.NET.createInstance()));
        ability.addEffect(new BraidedNetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Craft with artifact {1}{U}
        this.addAbility(new CraftAbility("{1}{U}"));
    }

    private BraidedNet(final BraidedNet card) {
        super(card);
    }

    @Override
    public BraidedNet copy() {
        return new BraidedNet(this);
    }
}

class BraidedNetEffect extends RestrictionEffect {

    BraidedNetEffect() {
        super(Duration.Custom);
        staticText = "its activated abilities can't be activated for as long as it remains tapped";
    }

    private BraidedNetEffect(final BraidedNetEffect effect) {
        super(effect);
    }

    @Override
    public BraidedNetEffect copy() {
        return new BraidedNetEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(getTargetPointer().getFirst(game, source));
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (super.isInactive(source, game)) {
            return true;
        }
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        return permanent == null || !permanent.isTapped();
    }
}
