package mage.cards.n;

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
public final class NacreTalisman extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a white spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public NacreTalisman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Whenever a player casts a white spell, you may pay {3}. If you do, untap target permanent.
        Ability ability = new SpellCastAllTriggeredAbility(new DoIfCostPaid(new UntapTargetEffect(), new ManaCostsImpl<>("{3}")), filter, false);
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private NacreTalisman(final NacreTalisman card) {
        super(card);
    }

    @Override
    public NacreTalisman copy() {
        return new NacreTalisman(this);
    }
}
