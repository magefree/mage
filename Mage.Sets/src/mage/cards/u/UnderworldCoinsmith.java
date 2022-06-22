
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class UnderworldCoinsmith extends CardImpl {

    public UnderworldCoinsmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{W}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Constellation - Whenever Underworld Coinsmith or an enchantment enters the battlefield under your control, you gain 1 life.
        this.addAbility(new ConstellationAbility(new GainLifeEffect(1)));
        // {W}{B}, Pay 1 life: Each opponent loses 1 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeOpponentsEffect(1), new ManaCostsImpl<>("{W}{B}"));
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
    }

    private UnderworldCoinsmith(final UnderworldCoinsmith card) {
        super(card);
    }

    @Override
    public UnderworldCoinsmith copy() {
        return new UnderworldCoinsmith(this);
    }
}
