package mage.cards.w;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.abilities.mana.ActivateIfConditionManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WhispererOfTheWilds extends CardImpl {

    public WhispererOfTheWilds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // Ferocious - {T}: Add {G}{G}. Activate this ability only if you control a creature with power 4 or greater.
        Ability ability = new ActivateIfConditionManaAbility(Zone.BATTLEFIELD, new BasicManaEffect(Mana.GreenMana(2)), new TapSourceCost(), FerociousCondition.instance);
        ability.setAbilityWord(AbilityWord.FEROCIOUS);
        ability.addHint(FerociousHint.instance);
        this.addAbility(ability);
    }

    private WhispererOfTheWilds(final WhispererOfTheWilds card) {
        super(card);
    }

    @Override
    public WhispererOfTheWilds copy() {
        return new WhispererOfTheWilds(this);
    }
}
