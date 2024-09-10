
package mage.cards.e;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class Execute extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("white creature");
    static{
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }
    
    public Execute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}");


        // Destroy target white creature. It can't be regenerated.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Execute(final Execute card) {
        super(card);
    }

    @Override
    public Execute copy() {
        return new Execute(this);
    }
}
