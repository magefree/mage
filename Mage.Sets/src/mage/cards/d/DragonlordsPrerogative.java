
package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffect;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class DragonlordsPrerogative extends CardImpl {

    public DragonlordsPrerogative(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");

        // As an additional cost to cast Dragonlord's Prerogative, you may reveal a Dragon card from your hand.
        this.getSpellAbility().addEffect(new InfoEffect("as an additional cost to cast this spell, you may reveal a Dragon card from your hand"));
        this.getSpellAbility().setCostAdjuster(DragonlordsPrerogativeAdjuster.instance);

        // If you revealed a Dragon card or controlled a Dragon as you cast Dragonlord's Prerogative, Dragonlord's Prerogative can't be countered.        
        Condition condition = new DragonlordsPrerogativeCondition();
        ContinuousRuleModifyingEffect cantBeCountered = new CantBeCounteredSourceEffect();
        ConditionalContinuousRuleModifyingEffect conditionalCantBeCountered = new ConditionalContinuousRuleModifyingEffect(cantBeCountered, condition);
        conditionalCantBeCountered.setText("<br/>If you revealed a Dragon card or controlled a Dragon as you cast {this}, this spell can't be countered");
        Ability ability = new SimpleStaticAbility(Zone.STACK, conditionalCantBeCountered);
        this.addAbility(ability);

        // Draw four cards.        
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(4));

    }

    public DragonlordsPrerogative(final DragonlordsPrerogative card) {
        super(card);
    }

    @Override
    public DragonlordsPrerogative copy() {
        return new DragonlordsPrerogative(this);
    }
}

enum DragonlordsPrerogativeAdjuster implements CostAdjuster {
    instance;
    private static final FilterCard filter = new FilterCard("a Dragon card from your hand");

    static {
        filter.add(new SubtypePredicate(SubType.DRAGON));
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller != null) {
            if (controller.getHand().count(filter, game) > 0) {
                ability.addCost(new RevealTargetFromHandCost(new TargetCardInHand(0, 1, filter)));
            }
        }
    }
}

class DragonlordsPrerogativeCondition implements Condition {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Dragon");

    static {
        filter.add(new SubtypePredicate(SubType.DRAGON));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applies = false;
        Spell spell = game.getStack().getSpell(source.getSourceId());
        if (spell != null && spell.getSpellAbility() != null) {
            for (Cost cost : spell.getSpellAbility().getCosts()) {
                if (cost instanceof RevealTargetFromHandCost) {
                    applies = !cost.getTargets().isEmpty();
                    break;
                }
            }
        }
        if (!applies) {
            applies = game.getBattlefield().countAll(filter, source.getControllerId(), game) > 0;
        }
        return applies;
    }
}
