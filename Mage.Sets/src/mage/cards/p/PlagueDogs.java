
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
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
public final class PlagueDogs extends CardImpl {

    public PlagueDogs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Plague Dogs dies, all creatures get -1/-1 until end of turn.
        this.addAbility(new DiesSourceTriggeredAbility(new BoostAllEffect(-1, -1, Duration.EndOfTurn), false));
        // {2}, Sacrifice Plague Dogs: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{2}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private PlagueDogs(final PlagueDogs card) {
        super(card);
    }

    @Override
    public PlagueDogs copy() {
        return new PlagueDogs(this);
    }
}
