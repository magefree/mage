
package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;

import java.util.UUID;

/**
 *
 * @author lopho
 */
public final class PlagueRats extends CardImpl {

    private static final FilterCreaturePermanent plagueRatsFilter = new FilterCreaturePermanent("creatures named Plague Rats on the battlefield");
    static {
        plagueRatsFilter.add(new NamePredicate("Plague Rats"));
    }
    
    public PlagueRats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.RAT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Plague Rats's power and toughness are each equal to the number of creatures named Plague Rats on the battlefield.
        DynamicValue amount = new PermanentsOnBattlefieldCount(plagueRatsFilter);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(amount, Duration.EndOfGame)));
    }

    private PlagueRats(final PlagueRats card) {
        super(card);
    }

    @Override
    public PlagueRats copy() {
        return new PlagueRats(this);
    }
}
