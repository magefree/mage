
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author cbt33
 */
public final class InfectedVermin extends CardImpl {

    public InfectedVermin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.RAT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{B}: Infected Vermin deals 1 damage to each creature and each player.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageEverythingEffect(1), new ManaCostsImpl<>("{2}{B}")));
        // Threshold - {3}{B}: Infected Vermin deals 3 damage to each creature and each player. Activate this ability only if seven or more cards are in your graveyard.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
                new DamageEverythingEffect(3),
                new ManaCostsImpl<>("{3}{B}"),
                new CardsInControllerGraveyardCondition(7));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    private InfectedVermin(final InfectedVermin card) {
        super(card);
    }

    @Override
    public InfectedVermin copy() {
        return new InfectedVermin(this);
    }
}
