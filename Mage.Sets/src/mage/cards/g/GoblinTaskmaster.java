
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class GoblinTaskmaster extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Goblin creature");

    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public GoblinTaskmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{R}: Target Goblin creature gets +1/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // Morph {R}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{R}")));
    }

    private GoblinTaskmaster(final GoblinTaskmaster card) {
        super(card);
    }

    @Override
    public GoblinTaskmaster copy() {
        return new GoblinTaskmaster(this);
    }
}
