
package mage.cards.m;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class MalachiteTalisman extends CardImpl {
    
    private final static FilterSpell filter = new FilterSpell("a green spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public MalachiteTalisman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Whenever a player casts a green spell, you may pay {3}. If you do, untap target permanent.
        Ability ability = new SpellCastAllTriggeredAbility(new DoIfCostPaid(new UntapTargetEffect(), new ManaCostsImpl("{3}")), filter, true);
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    public MalachiteTalisman(final MalachiteTalisman card) {
        super(card);
    }

    @Override
    public MalachiteTalisman copy() {
        return new MalachiteTalisman(this);
    }
}
