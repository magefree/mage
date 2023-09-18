

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class GoblinBalloonBrigade extends CardImpl {

    public GoblinBalloonBrigade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {R}: Goblin Balloon Brigade gains flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), 
                        Duration.EndOfTurn), new ManaCostsImpl<>("{R}")));
    }

    private GoblinBalloonBrigade(final GoblinBalloonBrigade card) {
        super(card);
    }

    @Override
    public GoblinBalloonBrigade copy() {
        return new GoblinBalloonBrigade(this);
    }

}
