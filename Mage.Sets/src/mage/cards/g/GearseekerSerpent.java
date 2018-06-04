
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class GearseekerSerpent extends CardImpl {

    public GearseekerSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{U}");
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Gearseeker Serpent costs {1} less to cast for each artifact you control
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new GearseekerSerpentCostReductionEffect()));

        // 5U: Gearseeker Serpent can't be blocked this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CantBeBlockedSourceEffect(Duration.EndOfTurn),
                new ManaCostsImpl<>("{5}{U}")));
    }

    public GearseekerSerpent(final GearseekerSerpent card) {
        super(card);
    }

    @Override
    public GearseekerSerpent copy() {
        return new GearseekerSerpent(this);
    }
}

class GearseekerSerpentCostReductionEffect extends CostModificationEffectImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public GearseekerSerpentCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "{this} costs {1} less to cast for each artifact you control";
    }

    protected GearseekerSerpentCostReductionEffect(final GearseekerSerpentCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int count = game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game).size();
        if (count > 0) {
            CardUtil.reduceCost(abilityToModify, count);
        }

        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.getSourceId().equals(source.getSourceId());
    }

    @Override
    public GearseekerSerpentCostReductionEffect copy() {
        return new GearseekerSerpentCostReductionEffect(this);
    }
}
