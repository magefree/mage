package mage.cards.d;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class DireUndercurrents extends CardImpl {

    private static final FilterCreaturePermanent filterBlue = new FilterCreaturePermanent("a blue creature");
    private static final FilterCreaturePermanent filterBlack = new FilterCreaturePermanent("a black creature");

    static {
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public DireUndercurrents(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U/B}{U/B}");

        // Whenever a blue creature enters the battlefield under your control, you may have target player draw a card.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new DrawCardTargetEffect(1)
                .setText("you may have target player draw a card"), filterBlue, true);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Whenever a black creature enters the battlefield under your control, you may have target player discard a card.
        Ability ability2 = new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new DiscardTargetEffect(1), filterBlack, true);
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
