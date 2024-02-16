
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
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
public final class DragonScarredBear extends CardImpl {

    public DragonScarredBear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.BEAR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // <i>Formidable</i> &mdash; {1}{G}: Regenerate Dragon-Scarred Bear. Activate this only if creatures you control have total power 8 or greater.
        Ability ability = new ActivateIfConditionActivatedAbility(
            Zone.BATTLEFIELD,
            new RegenerateSourceEffect(),
            new ManaCostsImpl<>("{1}{G}"),
            FormidableCondition.instance);
        ability.setAbilityWord(AbilityWord.FORMIDABLE);
        this.addAbility(ability);
    }

    private DragonScarredBear(final DragonScarredBear card) {
        super(card);
    }

    @Override
    public DragonScarredBear copy() {
        return new DragonScarredBear(this);
    }
}
