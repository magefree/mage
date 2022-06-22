package mage.cards.l;

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

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class LapisLazuliTalisman extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a blue spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public LapisLazuliTalisman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Whenever a player casts a blue spell, you may pay {3}. If you do, untap target permanent.
        Ability ability = new SpellCastAllTriggeredAbility(new DoIfCostPaid(new UntapTargetEffect(), new ManaCostsImpl<>("{3}")), filter, false);
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private LapisLazuliTalisman(final LapisLazuliTalisman card) {
        super(card);
    }

    @Override
    public LapisLazuliTalisman copy() {
        return new LapisLazuliTalisman(this);
    }
}
