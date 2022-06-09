
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class RakshasaDeathdealer extends CardImpl {

    public RakshasaDeathdealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{G}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {B}{G}: Rakshasa Deathdealer gets +2/+2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2,2,Duration.EndOfTurn), new ManaCostsImpl<>("{B}{G}")));
        // {B}{G}: Regenerate Rakshasa Deathdealer.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{B}{G}")));
    }

    private RakshasaDeathdealer(final RakshasaDeathdealer card) {
        super(card);
    }

    @Override
    public RakshasaDeathdealer copy() {
        return new RakshasaDeathdealer(this);
    }
}
