package mage.cards.f;

import mage.MageInt;
import mage.Mana;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.abilities.keyword.EternalizeAbility;
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
 * @author Susucr
 */
public final class FanaticOfRhonas extends CardImpl {

    public FanaticOfRhonas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // Ferocious -- {T}: Add {G}{G}{G}{G}. Activate only if you control a creature with power 4 or greater.
        this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD,
                new BasicManaEffect(Mana.GreenMana(4)),
                new TapSourceCost(),
                FerociousCondition.instance
        ).setAbilityWord(AbilityWord.FEROCIOUS).addHint(FerociousHint.instance));

        // Eternalize {2}{G}{G}
        this.addAbility(new EternalizeAbility(new ManaCostsImpl<>("{2}{G}{G}"), this));
    }

    private FanaticOfRhonas(final FanaticOfRhonas card) {
        super(card);
    }

    @Override
    public FanaticOfRhonas copy() {
        return new FanaticOfRhonas(this);
    }
}
