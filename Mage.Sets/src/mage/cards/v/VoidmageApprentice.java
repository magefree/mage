
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class VoidmageApprentice extends CardImpl {

    public VoidmageApprentice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Morph {2}{U}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{2}{U}{U}")));
        
        // When Voidmage Apprentice is turned face up, counter target spell.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new CounterTargetEffect(), false);
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
    }

    private VoidmageApprentice(final VoidmageApprentice card) {
        super(card);
    }

    @Override
    public VoidmageApprentice copy() {
        return new VoidmageApprentice(this);
    }
}
