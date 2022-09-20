package mage.cards.a;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Backfir3
 */
public final class Attrition extends CardImpl {

    public Attrition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");

        //{B}, Sacrifice a creature: Destroy target nonblack creature.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new ColoredManaCost(ColoredManaSymbol.B));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK).withChooseHint("to destroy"));
        this.addAbility(ability);
    }

    private Attrition(final Attrition card) {
        super(card);
    }

    @Override
    public Attrition copy() {
        return new Attrition(this);
    }
}
