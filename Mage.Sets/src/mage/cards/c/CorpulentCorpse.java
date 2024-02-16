
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FearAbility;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class CorpulentCorpse extends CardImpl {

    public CorpulentCorpse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Fear
        this.addAbility(FearAbility.getInstance());
        // Suspend 5-{B}
        this.addAbility(new SuspendAbility(5, new ManaCostsImpl<>("{B}"), this));
    }

    private CorpulentCorpse(final CorpulentCorpse card) {
        super(card);
    }

    @Override
    public CorpulentCorpse copy() {
        return new CorpulentCorpse(this);
    }
}
