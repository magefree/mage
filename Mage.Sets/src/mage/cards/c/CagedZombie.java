package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.watchers.common.MorbidWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CagedZombie extends CardImpl {

    public CagedZombie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {1}{B}, {T}: Each opponent loses 2 life. Activate this ability only if a creature died this turn.
        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD, new LoseLifeOpponentsEffect(2),
                new ManaCostsImpl("{1}{B}"), MorbidCondition.instance
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability, new MorbidWatcher());
    }

    private CagedZombie(final CagedZombie card) {
        super(card);
    }

    @Override
    public CagedZombie copy() {
        return new CagedZombie(this);
    }
}
