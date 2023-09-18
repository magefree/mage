package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.functions.EmptyCopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShamelessCharlatan extends CardImpl {

    public ShamelessCharlatan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have "{2}{U}: This creature becomes a copy of another target creature."
        Ability ability = new SimpleActivatedAbility(new ShamelessCharlatanEffect(), new ManaCostsImpl<>("{2}{U}"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                ability, Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        )));
    }

    private ShamelessCharlatan(final ShamelessCharlatan card) {
        super(card);
    }

    @Override
    public ShamelessCharlatan copy() {
        return new ShamelessCharlatan(this);
    }
}

class ShamelessCharlatanEffect extends OneShotEffect {

    ShamelessCharlatanEffect() {
        super(Outcome.Benefit);
        staticText = "this creature becomes a copy of another target creature";
    }

    private ShamelessCharlatanEffect(final ShamelessCharlatanEffect effect) {
        super(effect);
    }

    @Override
    public ShamelessCharlatanEffect copy() {
        return new ShamelessCharlatanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        Permanent copyFromPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (sourcePermanent == null || copyFromPermanent == null) {
            return false;
        }
        game.copyPermanent(Duration.WhileOnBattlefield, copyFromPermanent, sourcePermanent.getId(), source, new EmptyCopyApplier());
        return true;
    }
}
