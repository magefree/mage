package mage.cards.w;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author Plopman
 */
public final class WrathOfMaritLage extends CardImpl {

    static final FilterCreaturePermanent filter = new FilterCreaturePermanent("red creatures");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }
    
    public WrathOfMaritLage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}{U}");


        // When Wrath of Marit Lage enters the battlefield, tap all red creatures.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TapAllEffect(filter)));
        // Red creatures don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, filter)));
    }

    private WrathOfMaritLage(final WrathOfMaritLage card) {
        super(card);
    }

    @Override
    public WrathOfMaritLage copy() {
        return new WrathOfMaritLage(this);
    }
}
