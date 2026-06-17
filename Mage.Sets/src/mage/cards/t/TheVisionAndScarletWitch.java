package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TheVisionAndScarletWitch extends CardImpl {

    public TheVisionAndScarletWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a spell, add {R} and put a +1/+1 counter on The Vision and Scarlet Witch.
        Ability ability = new SpellCastControllerTriggeredAbility(
            new AddManaToManaPoolSourceControllerEffect(Mana.RedMana(1)), false
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).concatBy("and"));
        this.addAbility(ability);
    }

    private TheVisionAndScarletWitch(final TheVisionAndScarletWitch card) {
        super(card);
    }

    @Override
    public TheVisionAndScarletWitch copy() {
        return new TheVisionAndScarletWitch(this);
    }
}
