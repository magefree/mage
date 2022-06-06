
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class KrosanAvenger extends CardImpl {

    public KrosanAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Threshold - {1}{G}: Regenerate Krosan Avenger. Activate this ability only if seven or more cards are in your graveyard.
        Ability thresholdAbility = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
            new RegenerateSourceEffect(),
            new ManaCostsImpl<>("{1}{G}"),
            new CardsInControllerGraveyardCondition(7));
        thresholdAbility.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(thresholdAbility);
    }

    private KrosanAvenger(final KrosanAvenger card) {
        super(card);
    }

    @Override
    public KrosanAvenger copy() {
        return new KrosanAvenger(this);
    }
}
