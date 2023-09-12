
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ThousandYearElixir extends CardImpl {

    public ThousandYearElixir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // You may activate abilities of creatures you control as though those creatures had haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ThousandYearElixirEffect()));

        // {1}, {tap}: Untap target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapTargetEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    private ThousandYearElixir(final ThousandYearElixir card) {
        super(card);
    }

    @Override
    public ThousandYearElixir copy() {
        return new ThousandYearElixir(this);
    }
}

class ThousandYearElixirEffect extends AsThoughEffectImpl {

    public ThousandYearElixirEffect() {
        super(AsThoughEffectType.ACTIVATE_HASTE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may activate abilities of creatures you control as though those creatures had haste";
    }

    private ThousandYearElixirEffect(final ThousandYearElixirEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ThousandYearElixirEffect copy() {
        return new ThousandYearElixirEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Permanent permanent = game.getPermanent(sourceId);
        return permanent != null
                && permanent.isCreature(game)
                && permanent.isControlledBy(source.getControllerId());
    }
}
