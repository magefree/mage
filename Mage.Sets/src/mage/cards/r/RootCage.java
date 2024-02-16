package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;

/**
 *
 * @author fireshoes
 */
public final class RootCage extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent(SubType.MERCENARY, "Mercenaries");

    public RootCage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}");

        // Mercenaries don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, filter)));
    }

    private RootCage(final RootCage card) {
        super(card);
    }

    @Override
    public RootCage copy() {
        return new RootCage(this);
    }
}
