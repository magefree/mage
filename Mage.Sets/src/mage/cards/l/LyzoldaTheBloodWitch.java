
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Quercitron
 */
public final class LyzoldaTheBloodWitch extends CardImpl {

    private static final FilterPermanent redFilter = new FilterPermanent();
    private static final FilterPermanent blackFilter = new FilterPermanent();

    static {
        redFilter.add(new ColorPredicate(ObjectColor.RED));
        blackFilter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public LyzoldaTheBloodWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {2}, Sacrifice a creature: Lyzolda, the Blood Witch deals 2 damage to any target if the sacrificed creature was red. Draw a card if the sacrificed creature was black.
        Effect effect = new ConditionalOneShotEffect(
                new DamageTargetEffect(2),
                new SacrificedWasCondition(redFilter),
                "{this} deals 2 damage to any target if the sacrificed creature was red");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{2}"));
        effect = new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1),
                new SacrificedWasCondition(blackFilter),
                "Draw a card if the sacrificed creature was black");
        ability.addEffect(effect);
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private LyzoldaTheBloodWitch(final LyzoldaTheBloodWitch card) {
        super(card);
    }

    @Override
    public LyzoldaTheBloodWitch copy() {
        return new LyzoldaTheBloodWitch(this);
    }
}

class SacrificedWasCondition implements Condition {

    private final FilterPermanent filter;

    public SacrificedWasCondition(final FilterPermanent filter) {
        this.filter = filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                UUID targetId = cost.getTargets().getFirstTarget();
                Permanent permanent = game.getPermanentOrLKIBattlefield(targetId);
                if (filter.match(permanent, game)) {
                    return true;
                }
            }
        }
        return false;
    }

}
