
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author fireshoes
 */
public final class DauthiWarlord extends CardImpl {
    
    private static final FilterPermanent filter = new FilterCreaturePermanent("creatures with shadow");

    static{
        filter.add(new AbilityPredicate(ShadowAbility.class));
    }

    public DauthiWarlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.DAUTHI);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());
        
        // Dauthi Warlord's power is equal to the number of creatures with shadow on the battlefield.
        Effect effect = new SetBasePowerSourceEffect(new PermanentsOnBattlefieldCount(filter));
        this.addAbility(new SimpleStaticAbility(Zone.ALL, effect));
    }

    private DauthiWarlord(final DauthiWarlord card) {
        super(card);
    }

    @Override
    public DauthiWarlord copy() {
        return new DauthiWarlord(this);
    }
}
