package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author jonubuu
 */
public final class KikiJikiMirrorBreaker extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("nonlegendary creature you control");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public KikiJikiMirrorBreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {T}: Create a token that's a copy of target nonlegendary creature you control, except it has haste. Sacrifice it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(new KikiJikiMirrorBreakerEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private KikiJikiMirrorBreaker(final KikiJikiMirrorBreaker card) {
        super(card);
    }

    @Override
    public KikiJikiMirrorBreaker copy() {
        return new KikiJikiMirrorBreaker(this);
    }
}

class KikiJikiMirrorBreakerEffect extends OneShotEffect {

    KikiJikiMirrorBreakerEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a token that's a copy of target nonlegendary creature you control, except it has haste. Sacrifice it at the beginning of the next end step";
    }

    private KikiJikiMirrorBreakerEffect(final KikiJikiMirrorBreakerEffect effect) {
        super(effect);
    }

    @Override
    public KikiJikiMirrorBreakerEffect copy() {
        return new KikiJikiMirrorBreakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent == null) {
            return false;
        }

        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId(), null, true);
        effect.setTargetPointer(new FixedTarget(permanent, game));
        effect.apply(game, source);
        effect.sacrificeTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}
