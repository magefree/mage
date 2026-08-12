package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class RaveningWarg extends CardImpl {

    public RaveningWarg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Ferocious -- Whenever this creature attacks while you control a creature with power 4 or greater, you gain 2 life.
        this.addAbility(new AttacksTriggeredAbility(
            new GainLifeEffect(2)).withTriggerCondition(FerociousCondition.instance)
            .setAbilityWord(AbilityWord.FEROCIOUS)
            .addHint(FerociousHint.instance)
        );
    }

    private RaveningWarg(final RaveningWarg card) {
        super(card);
    }

    @Override
    public RaveningWarg copy() {
        return new RaveningWarg(this);
    }
}
