
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetPowerSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class RiptideMangler extends CardImpl {

    public RiptideMangler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // {1}{U}: Change Riptide Mangler's base power to target creature's power.
        Ability ability = new SimpleActivatedAbility(new RiptideManglerEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private RiptideMangler(final RiptideMangler card) {
        super(card);
    }

    @Override
    public RiptideMangler copy() {
        return new RiptideMangler(this);
    }
}

class RiptideManglerEffect extends OneShotEffect {

    RiptideManglerEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Change {this}'s base power to target creature's power. <i>(This effect lasts indefinitely.)</i>";
    }

    RiptideManglerEffect(final RiptideManglerEffect effect) {
        super(effect);
    }

    @Override
    public RiptideManglerEffect copy() {
        return new RiptideManglerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        game.addEffect(new SetPowerSourceEffect(StaticValue.get(permanent.getPower().getValue()), Duration.WhileOnBattlefield, SubLayer.SetPT_7b), source);
        return true;
    }
}
