package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class TheChiefWarg extends CardImpl {

    public TheChiefWarg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Ferocious -- Whenever you attack while you control a creature with power 4 or greater, you draw a card and lose 1 life.
        Ability ability = new AttacksTriggeredAbility(new DrawCardSourceControllerEffect(1, true))
            .withInterveningIf(FerociousCondition.instance)
            .addHint(FerociousHint.instance)
            .setAbilityWord(AbilityWord.FEROCIOUS);
        ability.addEffect(new LoseLifeSourceControllerEffect(1, false).concatBy("and"));
        this.addAbility(ability);
    }

    private TheChiefWarg(final TheChiefWarg card) {
        super(card);
    }

    @Override
    public TheChiefWarg copy() {
        return new TheChiefWarg(this);
    }
}
