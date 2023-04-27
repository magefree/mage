
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author MarcoMarin
 */
public final class SingingTree extends CardImpl {

    public SingingTree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.PLANT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // {tap}: Target attacking creature's power becomes 0 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SingingTreeEffect(), new TapSourceCost());
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private SingingTree(final SingingTree card) {
        super(card);
    }

    @Override
    public SingingTree copy() {
        return new SingingTree(this);
    }
}

class SingingTreeEffect extends OneShotEffect {

    public SingingTreeEffect() {
        super(Outcome.Detriment);
        staticText = "Target attacking creature has base power 0 until end of turn.";
    }

    public SingingTreeEffect(final SingingTreeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature != null) {
            int toughness = targetCreature.getToughness().getModifiedBaseValue();
            game.addEffect(new SetBasePowerToughnessTargetEffect(StaticValue.get(0), null, Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }

    @Override
    public Effect copy() {
        return new SingingTreeEffect(this);
    }
}
