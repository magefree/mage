
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnBeforeAttackersDeclaredCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponentsChoicePermanent;

/**
 *
 * @author LevelX2
 */
public final class DiaochanArtfulBeauty extends CardImpl {

    public DiaochanArtfulBeauty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Destroy target creature of your choice, then destroy target creature of an opponent's choice. Activate this ability only during your turn, before attackers are declared.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new DiaochanArtfulBeautyDestroyEffect(), new TapSourceCost(), MyTurnBeforeAttackersDeclaredCondition.instance);
        ability.addTarget(new TargetCreaturePermanent());
        ability.addTarget(new TargetOpponentsChoicePermanent(1, 1, StaticFilters.FILTER_PERMANENT_CREATURE, false, true));
        this.addAbility(ability);
    }

    public DiaochanArtfulBeauty(final DiaochanArtfulBeauty card) {
        super(card);
    }

    @Override
    public DiaochanArtfulBeauty copy() {
        return new DiaochanArtfulBeauty(this);
    }
}

class DiaochanArtfulBeautyDestroyEffect extends OneShotEffect {

    DiaochanArtfulBeautyDestroyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature of your choice, then destroy target creature of an opponent's choice";
    }

    DiaochanArtfulBeautyDestroyEffect(final DiaochanArtfulBeautyDestroyEffect effect) {
        super(effect);
    }

    @Override
    public DiaochanArtfulBeautyDestroyEffect copy() {
        return new DiaochanArtfulBeautyDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent firstTarget = game.getPermanent(source.getFirstTarget());
            if (firstTarget != null) {
                firstTarget.destroy(source.getSourceId(), game, false);

            }
            Permanent secondTarget = game.getPermanent(source.getTargets().get(1).getFirstTarget());
            if (secondTarget != null) {
                secondTarget.destroy(source.getSourceId(), game, false);
            }
            return true;
        }
        return false;
    }
}
