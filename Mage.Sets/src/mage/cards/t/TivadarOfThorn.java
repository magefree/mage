
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class TivadarOfThorn extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("Goblin");
    
    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public TivadarOfThorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        
        // protection from red
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));
        
        // When Tivadar of Thorn enters the battlefield, destroy target Goblin.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(),false);
        Target target = new TargetPermanent(filter);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private TivadarOfThorn(final TivadarOfThorn card) {
        super(card);
    }

    @Override
    public TivadarOfThorn copy() {
        return new TivadarOfThorn(this);
    }
}
