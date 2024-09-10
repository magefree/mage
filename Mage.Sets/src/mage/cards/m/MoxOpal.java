package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.hint.common.MetalcraftHint;
import mage.abilities.mana.ActivateIfConditionManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com, Loki
 */
public final class MoxOpal extends CardImpl {

    public MoxOpal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");
        this.supertype.add(SuperType.LEGENDARY);

        Ability ability = new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD,
                new AddManaOfAnyColorEffect(),
                new TapSourceCost(),
                MetalcraftCondition.instance);
        ability.setAbilityWord(AbilityWord.METALCRAFT);
        ability.addHint(MetalcraftHint.instance);
        this.addAbility(ability);
    }

    private MoxOpal(final MoxOpal card) {
        super(card);
    }

    @Override
    public MoxOpal copy() {
        return new MoxOpal(this);
    }

}
