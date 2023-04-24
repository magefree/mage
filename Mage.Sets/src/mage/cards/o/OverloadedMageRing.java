package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OverloadedMageRing extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public OverloadedMageRing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.color.setBlue(true);
        this.nightCard = true;

        // {1}, {T}, Sacrifice Overloaded Mage-Ring: Copy target spell you control.
        Ability ability = new SimpleActivatedAbility(
                new CopyTargetSpellEffect(false, false, false), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);
    }

    private OverloadedMageRing(final OverloadedMageRing card) {
        super(card);
    }

    @Override
    public OverloadedMageRing copy() {
        return new OverloadedMageRing(this);
    }
}
