
package mage.cards.d;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class DireUndercurrents extends CardImpl {

    private static final String rule1 = "Whenever a blue creature enters the battlefield under your control, you may have target player draw a card.";
    private static final String rule2 = "Whenever a black creature enters the battlefield under your control, you may have target player discard a card.";

    private static final FilterControlledPermanent filterBlue = new FilterControlledCreaturePermanent();
    private static final FilterControlledPermanent filterBlack = new FilterControlledCreaturePermanent();

    static {
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public DireUndercurrents(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U/B}{U/B}");


        // Whenever a blue creature enters the battlefield under your control, you may have target player draw a card.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new DrawCardTargetEffect(1), filterBlue, true, rule1);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Whenever a black creature enters the battlefield under your control, you may have target player discard a card.
        Ability ability2 = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new DiscardTargetEffect(1), filterBlack, true, rule2);
        ability2.addTarget(new TargetPlayer());
        this.addAbility(ability2);

    }

    private DireUndercurrents(final DireUndercurrents card) {
        super(card);
    }

    @Override
    public DireUndercurrents copy() {
        return new DireUndercurrents(this);
    }
}
