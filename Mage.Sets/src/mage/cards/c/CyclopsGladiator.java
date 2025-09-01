

package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class CyclopsGladiator extends CardImpl {

    public CyclopsGladiator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}{R}");
        this.subtype.add(SubType.CYCLOPS);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Cyclops Gladiator attacks, you may have it deal damage equal to its power to target creature defending player controls. 
        // If you do, that creature deals damage equal to its power to Cyclops Gladiator.
        Ability ability = new AttacksTriggeredAbility(new CyclopsGladiatorEffect(), true);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE));
        ability.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster());
        this.addAbility(ability);
    }

    private CyclopsGladiator(final CyclopsGladiator card) {
        super(card);
    }

    @Override
    public CyclopsGladiator copy() {
        return new CyclopsGladiator(this);
    }

}

class CyclopsGladiatorEffect extends OneShotEffect {

    CyclopsGladiatorEffect() {
        super(Outcome.Damage);
        staticText = "you may have it deal damage equal to its power to target creature defending player controls. If you do, that creature deals damage equal to its power to {this}";
    }

    private CyclopsGladiatorEffect(final CyclopsGladiatorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent cyclops = game.getPermanent(source.getSourceId());
        if (permanent != null && cyclops != null) {
            permanent.damage(cyclops.getPower().getValue(), cyclops.getId(), source, game, false, true);
            cyclops.damage(permanent.getPower().getValue(), permanent.getId(), source, game, false, true);
            return true;
        }
        return false;
    }

    @Override
    public CyclopsGladiatorEffect copy() {
        return new CyclopsGladiatorEffect(this);
    }

}
