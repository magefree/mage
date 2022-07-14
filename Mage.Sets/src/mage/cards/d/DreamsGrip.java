
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author michael.napoleon@gmail.com
 */
public final class DreamsGrip extends CardImpl {

    public DreamsGrip(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Choose one - 
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);
        //Tap target permanent; 
        TargetPermanent target1 = new TargetPermanent(new FilterPermanent("Permanent to tap"));
        Effect tapEffect = new TapTargetEffect();
        tapEffect.setText("Tap target permanent");
        this.getSpellAbility().addTarget(target1);
        this.getSpellAbility().addEffect(tapEffect);
        //or untap target permanent.
        Effect untapEffect = new UntapTargetEffect();
        untapEffect.setText("Untap target permanent");
        Mode mode = new Mode(untapEffect);
        TargetPermanent target2 = new TargetPermanent(new FilterPermanent("Permanent to untap"));
        mode.addTarget(target2);
        this.getSpellAbility().addMode(mode);
        
        // Entwine {1}
        this.addAbility(new EntwineAbility("{1}"));
    }

    private DreamsGrip(final DreamsGrip card) {
        super(card);
    }

    @Override
    public DreamsGrip copy() {
        return new DreamsGrip(this);
    }
}
