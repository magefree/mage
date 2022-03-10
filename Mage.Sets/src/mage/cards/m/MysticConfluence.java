
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class MysticConfluence extends CardImpl {

    public MysticConfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}{U}");

        // Choose three. You may choose the same mode more than once. 
        this.getSpellAbility().getModes().setMinModes(3);
        this.getSpellAbility().getModes().setMaxModes(3);
        this.getSpellAbility().getModes().setEachModeMoreThanOnce(true);
        
        // - Counter target spell unless its controller pays {3};
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(3)));
        this.getSpellAbility().addTarget(new TargetSpell());
        
        //  Return target creature to its owner's hand;
        Mode mode = new Mode(new ReturnToHandTargetEffect());
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().getModes().addMode(mode);
        
         // Draw a card.
        mode = new Mode(new DrawCardSourceControllerEffect(1));
        this.getSpellAbility().getModes().addMode(mode);
    }

    private MysticConfluence(final MysticConfluence card) {
        super(card);
    }

    @Override
    public MysticConfluence copy() {
        return new MysticConfluence(this);
    }
}
