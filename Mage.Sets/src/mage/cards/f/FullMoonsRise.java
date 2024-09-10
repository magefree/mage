
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffect;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author nantuko
 */
public final class FullMoonsRise extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Werewolf creatures");

    static {
        filter.add(SubType.WEREWOLF.getPredicate());
    }

    public FullMoonsRise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}");


        // Werewolf creatures you control get +1/+0 and have trample.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield, filter));
        Effect effect = new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter);
        effect.setText("and have trample");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Sacrifice Full Moon's Rise: Regenerate all Werewolf creatures you control.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new FullMoonsRiseEffect(filter), new SacrificeSourceCost()));
    }

    private FullMoonsRise(final FullMoonsRise card) {
        super(card);
    }

    @Override
    public FullMoonsRise copy() {
        return new FullMoonsRise(this);
    }
}

class FullMoonsRiseEffect extends OneShotEffect {

    private final FilterPermanent filter;

    public FullMoonsRiseEffect(FilterPermanent filter) {
        super(Outcome.Regenerate);
        this.filter = filter;
        staticText = "Regenerate all Werewolf creatures you control";
    }

    private FullMoonsRiseEffect(final FullMoonsRiseEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public FullMoonsRiseEffect copy() {
        return new FullMoonsRiseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
            ReplacementEffect effect = new RegenerateTargetEffect();
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
        }
        return true;
    }

}
