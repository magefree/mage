package mage.cards.k;

import mage.MageInt;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KrosanAvenger extends CardImpl {

    public KrosanAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Threshold - {1}{G}: Regenerate Krosan Avenger. Activate this ability only if seven or more cards are in your graveyard.
        this.addAbility(new ConditionalActivatedAbility(
                new RegenerateSourceEffect(), new ManaCostsImpl<>("{1}{G}"), ThresholdCondition.instance
        ).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private KrosanAvenger(final KrosanAvenger card) {
        super(card);
    }

    @Override
    public KrosanAvenger copy() {
        return new KrosanAvenger(this);
    }
}
