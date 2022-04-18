
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class SilumgarSpellEater extends CardImpl {

    public SilumgarSpellEater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Megamorph {4}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{4}{U}"), true));
        
        // When Silumgar Spell-Eater is turned face up, counter target spell unless its controller pays {3}.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new CounterUnlessPaysEffect(new GenericManaCost(3)), false, false);
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
    }

    private SilumgarSpellEater(final SilumgarSpellEater card) {
        super(card);
    }

    @Override
    public SilumgarSpellEater copy() {
        return new SilumgarSpellEater(this);
    }
}
