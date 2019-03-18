
package mage.cards.h;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author pcasaretto_at_gmail.com
 */
public final class Heartstone extends CardImpl {

    public Heartstone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // Activated abilities of creatures cost {1} less to activate. This effect can't reduce the amount of mana an ability costs to activate to less than one mana.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HeartstoneEffect()));
    }

    public Heartstone(final Heartstone card) {
        super(card);
    }

    @Override
    public Heartstone copy() {
        return new Heartstone(this);
    }
}

class HeartstoneEffect extends CostModificationEffectImpl {

    private static final String effectText = "Activated abilities of creatures cost {1} less to activate. This effect can't reduce the amount of mana an ability costs to activate to less than one mana.";
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public HeartstoneEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = effectText;
    }

    public HeartstoneEffect(final HeartstoneEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(abilityToModify.getControllerId());
        if (controller != null) {
            Mana mana = abilityToModify.getManaCostsToPay().getMana();
            if (mana.count() > 1 && mana.getGeneric() > 0) {
                CardUtil.reduceCost(abilityToModify, 1);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify.getAbilityType() == AbilityType.ACTIVATED
                || (abilityToModify.getAbilityType() == AbilityType.MANA && (abilityToModify instanceof ActivatedAbility))) {
            // Activated abilities of creatures
            Permanent permanent = game.getPermanent(abilityToModify.getSourceId());
            if (permanent != null && filter.match(permanent, source.getSourceId(), source.getControllerId(), game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public HeartstoneEffect copy() {
        return new HeartstoneEffect(this);
    }
}
