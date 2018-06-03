
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.ActivateIfConditionManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com, Loki
 */
public final class MoxOpal extends CardImpl {

    public MoxOpal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{0}");
        addSuperType(SuperType.LEGENDARY);

        Ability ability = new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD,
                new AddManaOfAnyColorEffect(),
                new TapSourceCost(),
                MetalcraftCondition.instance);
        ability.setAbilityWord(AbilityWord.METALCRAFT);
        this.addAbility(ability);
    }

    public MoxOpal(final MoxOpal card) {
        super(card);
    }

    @Override
    public MoxOpal copy() {
        return new MoxOpal(this);
    }

}
