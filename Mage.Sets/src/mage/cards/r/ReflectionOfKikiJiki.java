package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReflectionOfKikiJiki extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another nonlegendary creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public ReflectionOfKikiJiki(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setRed(true);
        this.nightCard = true;

        // {1}, {T}: Create a token that's a copy of another target nonlegendary creature you control, except it has haste. Sacrifice it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(new ReflectionOfKikiJikiEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ReflectionOfKikiJiki(final ReflectionOfKikiJiki card) {
        super(card);
    }

    @Override
    public ReflectionOfKikiJiki copy() {
        return new ReflectionOfKikiJiki(this);
    }
}

class ReflectionOfKikiJikiEffect extends OneShotEffect {

    ReflectionOfKikiJikiEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of another target nonlegendary creature you control, " +
                "except it has haste. Sacrifice it at the beginning of the next end step";
    }

    private ReflectionOfKikiJikiEffect(final ReflectionOfKikiJikiEffect effect) {
        super(effect);
    }

    @Override
    public ReflectionOfKikiJikiEffect copy() {
        return new ReflectionOfKikiJikiEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(null, null, true);
        effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
        effect.apply(game, source);
        effect.sacrificeTokensCreatedAtEndOfCombat(game, source);
        return true;
    }
}
