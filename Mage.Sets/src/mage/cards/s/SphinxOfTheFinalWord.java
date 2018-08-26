
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBeCounteredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantBeCounteredControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author fireshoes
 */
public final class SphinxOfTheFinalWord extends CardImpl {
    
    private static final FilterSpell filterTarget = new FilterSpell("Instant and sorcery spells you control");

    static {
        filterTarget.add(Predicates.or(new CardTypePredicate(CardType.INSTANT),
                (new CardTypePredicate(CardType.SORCERY))));
    }
    
    public SphinxOfTheFinalWord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{U}");
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Sphinx of the Final Word can't be countered.
        this.addAbility(new CantBeCounteredAbility());
        
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
        
        // Instant and sorcery spells you control can't be countered.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeCounteredControlledEffect(filterTarget, null, Duration.WhileOnBattlefield)));
    }

    public SphinxOfTheFinalWord(final SphinxOfTheFinalWord card) {
        super(card);
    }

    @Override
    public SphinxOfTheFinalWord copy() {
        return new SphinxOfTheFinalWord(this);
    }
}
