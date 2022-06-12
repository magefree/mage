
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;

/**
 *
 * @author LevelX2
 */
public final class ZephyrScribe extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell("a noncreature spell");
    
    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }
    
    public ZephyrScribe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN, SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {U}, {T}: Draw a card, then discard a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawDiscardControllerEffect(1,1), new ManaCostsImpl<>("{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        
        // Whenever you cast a noncreature spell, untap Zephyr Scribe.
        this.addAbility(new SpellCastControllerTriggeredAbility(new UntapSourceEffect(), filter, false));        
    }

    private ZephyrScribe(final ZephyrScribe card) {
        super(card);
    }

    @Override
    public ZephyrScribe copy() {
        return new ZephyrScribe(this);
    }
}
