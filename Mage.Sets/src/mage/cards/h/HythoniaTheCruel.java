
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author LevelX2
 */
public final class HythoniaTheCruel extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Gorgon creatures");
    static {
        filter.add(Predicates.not(SubType.GORGON.getPredicate()));
    }

    public HythoniaTheCruel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GORGON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // {6}{B}{B}: Monstrosity 3.
        this.addAbility(new MonstrosityAbility("{6}{B}{B}", 3));
        // When Hythonia the Cruel becomes monstrous, destroy all non-Gorgon creatures.
        this.addAbility(new BecomesMonstrousSourceTriggeredAbility(new DestroyAllEffect(filter)));
        
    }

    private HythoniaTheCruel(final HythoniaTheCruel card) {
        super(card);
    }

    @Override
    public HythoniaTheCruel copy() {
        return new HythoniaTheCruel(this);
    }
}
