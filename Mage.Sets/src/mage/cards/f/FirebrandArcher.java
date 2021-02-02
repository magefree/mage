
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;

/**
 *
 * @author ciaccona007
 */
public final class FirebrandArcher extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell("a noncreature spell");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }
    
    public FirebrandArcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast a noncreature spell, Firebrand Archer deals 1 damage to each opponent.
        addAbility(new SpellCastControllerTriggeredAbility(new DamagePlayersEffect(1, TargetController.OPPONENT), filter, false));
    }

    private FirebrandArcher(final FirebrandArcher card) {
        super(card);
    }

    @Override
    public FirebrandArcher copy() {
        return new FirebrandArcher(this);
    }
}
