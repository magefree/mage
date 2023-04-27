
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

/**
 *
 * @author jeffwadsworth
 */
public final class StratusDancer extends CardImpl {

    public StratusDancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Megamorph {1}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{1}{U}"), true));
        
        // When Stratus Dancer is turned face up, counter target instant or sorcery spell
        Effect effect = new CounterTargetEffect();
        effect.setText("counter target instant or sorcery spell");
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(effect, false);
        ability.addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
        this.addAbility(ability);
        
    }

    private StratusDancer(final StratusDancer card) {
        super(card);
    }

    @Override
    public StratusDancer copy() {
        return new StratusDancer(this);
    }
}
