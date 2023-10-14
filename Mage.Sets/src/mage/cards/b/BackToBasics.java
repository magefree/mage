
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class BackToBasics extends CardImpl {

    public BackToBasics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");


        // Nonbasic lands don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, FilterLandPermanent.nonbasicLands())));
    }

    private BackToBasics(final BackToBasics card) {
        super(card);
    }

    @Override
    public BackToBasics copy() {
        return new BackToBasics(this);
    }
}
