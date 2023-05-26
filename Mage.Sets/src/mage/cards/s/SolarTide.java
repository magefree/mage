
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class SolarTide extends CardImpl {
    
    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("creatures with power 2 or less");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creatures with power 3 or greater");
    
    static {
        filter1.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
        filter2.add(new PowerPredicate(ComparisonType.MORE_THAN, 2));
    }

    public SolarTide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{W}{W}");

        // Choose one - Destroy all creatures with power 2 or less;
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter1));
        
        // or destroy all creatures with power 3 or greater.
        Mode mode = new Mode(new DestroyAllEffect(filter2));
        this.getSpellAbility().getModes().addMode(mode);
        
        // Entwine-Sacrifice two lands.
        this.addAbility(new EntwineAbility(new SacrificeTargetCost(new TargetControlledPermanent(2, 2, new FilterControlledLandPermanent("lands"), true))));
    }

    private SolarTide(final SolarTide card) {
        super(card);
    }

    @Override
    public SolarTide copy() {
        return new SolarTide(this);
    }
}
