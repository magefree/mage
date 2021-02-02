
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author fireshoes
 */
public final class FledglingDjinn extends CardImpl {

    public FledglingDjinn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // At the beginning of your upkeep, Fledgling Djinn deals 1 damage to you.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DamageControllerEffect(1), TargetController.YOU, false));
    }

    private FledglingDjinn(final FledglingDjinn card) {
        super(card);
    }

    @Override
    public FledglingDjinn copy() {
        return new FledglingDjinn(this);
    }
}
