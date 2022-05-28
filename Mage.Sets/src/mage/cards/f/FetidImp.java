
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LoneFox

 */
public final class FetidImp extends CardImpl {

    public FetidImp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.IMP);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {B}: Fetid Imp gains deathtouch until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{B}")));
    }

    private FetidImp(final FetidImp card) {
        super(card);
    }

    @Override
    public FetidImp copy() {
        return new FetidImp(this);
    }
}
