
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.LoseAbilitySourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class TidewaterMinion extends CardImpl {

    public TidewaterMinion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        
        // {4}: Tidewater Minion loses defender until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD, 
                new LoseAbilitySourceEffect(DefenderAbility.getInstance(), Duration.EndOfTurn), 
                new ManaCostsImpl<>("{4}")));
        
        // {tap}: Untap target permanent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private TidewaterMinion(final TidewaterMinion card) {
        super(card);
    }

    @Override
    public TidewaterMinion copy() {
        return new TidewaterMinion(this);
    }
}
