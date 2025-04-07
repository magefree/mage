package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NagaFleshcrafter extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("nonlegendary creature you control");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public NagaFleshcrafter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have this creature enter as a copy of any creature on the battlefield.
        this.addAbility(new EntersBattlefieldAbility(new CopyPermanentEffect(), true));

        // Renew -- {2}{U}, Exile this card from your graveyard: Put a +1/+1 counter on target nonlegendary creature you control. Each other creature you control becomes a copy of that creature until end of turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.GRAVEYARD,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                new ManaCostsImpl<>("{2}{U}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        ability.addEffect(new NagaFleshcrafterEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability.setAbilityWord(AbilityWord.RENEW));
    }

    private NagaFleshcrafter(final NagaFleshcrafter card) {
        super(card);
    }

    @Override
    public NagaFleshcrafter copy() {
        return new NagaFleshcrafter(this);
    }
}

class NagaFleshcrafterEffect extends OneShotEffect {

    NagaFleshcrafterEffect() {
        super(Outcome.Benefit);
        staticText = "each other creature you control becomes a copy of that creature until end of turn";
    }

    private NagaFleshcrafterEffect(final NagaFleshcrafterEffect effect) {
        super(effect);
    }

    @Override
    public NagaFleshcrafterEffect copy() {
        return new NagaFleshcrafterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        for (Permanent creature : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                source.getControllerId(), source, game
        )) {
            if (!permanent.getId().equals(creature.getId())) {
                game.copyPermanent(Duration.EndOfTurn, permanent, creature.getId(), source, null);
            }
        }
        return true;
    }
}
