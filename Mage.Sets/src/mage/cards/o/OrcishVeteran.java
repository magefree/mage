
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBlockCreaturesSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.PowerPredicate;

/**
 *
 * @author fireshoes
 */
public final class OrcishVeteran extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("white creatures with power 2 or greater");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 1));
    }

    public OrcishVeteran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.ORC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Orcish Veteran can't block white creatures with power 2 or greater.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBlockCreaturesSourceEffect(filter)));
        
        // {R}: Orcish Veteran gains first strike until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{R}")));
    }

    private OrcishVeteran(final OrcishVeteran card) {
        super(card);
    }

    @Override
    public OrcishVeteran copy() {
        return new OrcishVeteran(this);
    }
}
