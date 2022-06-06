
package mage.cards.l;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author TheElk801 & L_J
 */
public final class LeshracsSigil extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a green spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public LeshracsSigil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}");

        // Whenever an opponent casts a green spell, you may pay {B}{B}. If you do, look at that player's hand and choose a card from it. The player discards that card.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                Zone.BATTLEFIELD, new DoIfCostPaid(new DiscardCardYouChooseTargetEffect(), new ManaCostsImpl<>("{B}{B}")), filter, false, SetTargetPointer.PLAYER));

        // {B}{B}: Return Leshrac's Sigil to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(new ReturnToHandSourceEffect(true), new ManaCostsImpl<>("{B}{B}")));
    }

    private LeshracsSigil(final LeshracsSigil card) {
        super(card);
    }

    @Override
    public LeshracsSigil copy() {
        return new LeshracsSigil(this);
    }
}
