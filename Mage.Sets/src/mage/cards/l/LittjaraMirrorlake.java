package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LittjaraMirrorlake extends CardImpl {

    public LittjaraMirrorlake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Littjara Mirrorlake enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // {2}{G}{G}{U}, {T}, Sacrifice Littjara Mirrorlake: Create a token that's a copy of target creature you control, except it enters the battlefield with an additional +1/+1 counter on it. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new LittjaraMirrorlakeEffect(), new ManaCostsImpl<>("{2}{G}{G}{U}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private LittjaraMirrorlake(final LittjaraMirrorlake card) {
        super(card);
    }

    @Override
    public LittjaraMirrorlake copy() {
        return new LittjaraMirrorlake(this);
    }
}

class LittjaraMirrorlakeEffect extends OneShotEffect {

    LittjaraMirrorlakeEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of target creature you control, " +
                "except it enters the battlefield with an additional +1/+1 counter on it";
    }

    private LittjaraMirrorlakeEffect(final LittjaraMirrorlakeEffect effect) {
        super(effect);
    }

    @Override
    public LittjaraMirrorlakeEffect copy() {
        return new LittjaraMirrorlakeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
        effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
        effect.apply(game, source);
        for (Permanent permanent : effect.getAddedPermanents()) {
            if (permanent == null) {
                continue;
            }
            permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
        }
        return true;
    }
}
