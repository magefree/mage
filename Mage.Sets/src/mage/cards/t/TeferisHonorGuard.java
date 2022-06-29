
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PhaseOutSourceEffect;
import mage.abilities.keyword.FlankingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class TeferisHonorGuard extends CardImpl {

    public TeferisHonorGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flanking
        this.addAbility(new FlankingAbility());
        
        // {U}{U}: Teferi's Honor Guard phases out.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PhaseOutSourceEffect(), new ManaCostsImpl<>("{U}{U}")));
    }

    private TeferisHonorGuard(final TeferisHonorGuard card) {
        super(card);
    }

    @Override
    public TeferisHonorGuard copy() {
        return new TeferisHonorGuard(this);
    }
}
