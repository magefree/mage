
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MostCommonColorCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class TsabosAssassin extends CardImpl {

    public TsabosAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Destroy target creature if it shares a color with the most common color among all permanents or a color tied for most common. A creature destroyed this way can't be regenerated.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,new TsabosAssasinEffect(),new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private TsabosAssassin(final TsabosAssassin card) {
        super(card);
    }

    @Override
    public TsabosAssassin copy() {
        return new TsabosAssassin(this);
    }
}
class TsabosAssasinEffect extends OneShotEffect {

    public TsabosAssasinEffect() {
        super(Outcome.Detriment);
        this.staticText = "Destroy target creature if it shares a color with the most common color among all permanents or a color tied for most common. A creature destroyed this way can't be regenerated.";
    }

    private TsabosAssasinEffect(final TsabosAssasinEffect effect) {
        super(effect);
    }

    @Override
    public TsabosAssasinEffect copy() {
        return new TsabosAssasinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            Condition condition = new MostCommonColorCondition(permanent.getColor(game));
            if (condition.apply(game, source)) {
                Effect effect = new DestroyTargetEffect();
                effect.setTargetPointer(new FixedTarget(permanent, game));
                return effect.apply(game, source);
            }
        }
        return false;
    }
}
