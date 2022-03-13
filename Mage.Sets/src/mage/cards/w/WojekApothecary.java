
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class WojekApothecary extends CardImpl {

    public WojekApothecary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Radiance - {T}: Prevent the next 1 damage that would be dealt to target creature and each other creature that shares a color with it this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new WojekApothecaryEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.setAbilityWord(AbilityWord.RADIANCE);
        this.addAbility(ability);

    }

    private WojekApothecary(final WojekApothecary card) {
        super(card);
    }

    @Override
    public WojekApothecary copy() {
        return new WojekApothecary(this);
    }
}

class WojekApothecaryEffect extends OneShotEffect {

    public WojekApothecaryEffect() {
        super(Outcome.Benefit);
        this.staticText = "Prevent the next 1 damage that would be dealt to target creature and each other creature that shares a color with it this turn";
    }

    public WojekApothecaryEffect(final WojekApothecaryEffect effect) {
        super(effect);
    }

    @Override
    public WojekApothecaryEffect copy() {
        return new WojekApothecaryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (target != null) {
                ObjectColor color = target.getColor(game);
                for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game)) {
                    if (permanent.getColor(game).shares(color)) {
                        ContinuousEffect effect = new PreventDamageToTargetEffect(Duration.EndOfTurn, 1);
                        effect.setTargetPointer(new FixedTarget(permanent, game));
                        game.addEffect(effect, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
