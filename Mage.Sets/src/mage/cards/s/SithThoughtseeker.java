
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.HateCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Styxo
 */
public final class SithThoughtseeker extends CardImpl {

    public SithThoughtseeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SITH);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // <i>Hate</i> &mdash; {2}{U}: Draw a card. Activate this ability only if an opponent lost life from source other than combat damage this turn.
        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(1),
                new ManaCostsImpl<>("{2}{U}"),
                HateCondition.instance);
        ability.setAbilityWord(AbilityWord.HATE);
        this.addAbility(ability);
    }

    private SithThoughtseeker(final SithThoughtseeker card) {
        super(card);
    }

    @Override
    public SithThoughtseeker copy() {
        return new SithThoughtseeker(this);
    }
}
