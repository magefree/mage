package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;

/**
 *
 * @author LevelX2
 */
public final class Choke extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Islands");
    
    static {
        filter.add(SubType.ISLAND.getPredicate());
    }    
            
    public Choke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");

        // Islands don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, filter)));
    }

    private Choke(final Choke card) {
        super(card);
    }

    @Override
    public Choke copy() {
        return new Choke(this);
    }
}
