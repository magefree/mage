
package mage.cards.s;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class SamiteCenserBearer extends CardImpl {

    public SamiteCenserBearer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {W}, Sacrifice Samite Censer-Bearer: Prevent the next 1 damage that would be dealt to each creature you control this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SamiteCenserBearerEffect(), new ManaCostsImpl<>("{W}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private SamiteCenserBearer(final SamiteCenserBearer card) {
        super(card);
    }

    @Override
    public SamiteCenserBearer copy() {
        return new SamiteCenserBearer(this);
    }
}

class SamiteCenserBearerEffect extends OneShotEffect {

    public SamiteCenserBearerEffect() {
        super(Outcome.PreventDamage);
        this.staticText = "Prevent the next 1 damage that would be dealt to each creature you control this turn";
    }

    public SamiteCenserBearerEffect(final SamiteCenserBearerEffect effect) {
        super(effect);
    }

    @Override
    public SamiteCenserBearerEffect copy() {
        return new SamiteCenserBearerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            PreventDamageToTargetEffect effect = new PreventDamageToTargetEffect(Duration.EndOfTurn, 1);
            List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, player.getId(), game);
            for (Permanent permanent : permanents) {
                effect.setTargetPointer(new FixedTarget(permanent, game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}
