package mage.cards.t;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheFireCrystal extends CardImpl {

    private static final FilterCard filter = new FilterCard("red spells");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public TheFireCrystal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);

        // Red spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // Creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES
        )));

        // {4}{R}{R}, {T}: Create a token that's a copy of target creature you control. Sacrifice it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(new TheFireCrystalEffect(), new ManaCostsImpl<>("{4}{R}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private TheFireCrystal(final TheFireCrystal card) {
        super(card);
    }

    @Override
    public TheFireCrystal copy() {
        return new TheFireCrystal(this);
    }
}

class TheFireCrystalEffect extends OneShotEffect {

    TheFireCrystalEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of target creature you control. " +
                "Sacrifice it at the beginning of the next end step";
    }

    private TheFireCrystalEffect(final TheFireCrystalEffect effect) {
        super(effect);
    }

    @Override
    public TheFireCrystalEffect copy() {
        return new TheFireCrystalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable(getTargetPointer().getFirst(game, source))
                .map(game::getPermanent)
                .map(new CreateTokenCopyTargetEffect()::setSavedPermanent)
                .filter(effect -> effect.apply(game, source))
                .ifPresent(effect -> effect.sacrificeTokensCreatedAtNextEndStep(game, source));
        return true;
    }
}
