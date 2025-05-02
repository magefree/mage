package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author nickmyers
 */
public final class ArenaOfTheAncients extends CardImpl {
    
    private static final FilterCreaturePermanent legendaryFilter = new FilterCreaturePermanent("legendary creatures");
    static {
        legendaryFilter.add(SuperType.LEGENDARY.getPredicate());
    }
    
    public ArenaOfTheAncients(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        
        // Legendary creatures don't untap during their controllers' untap steps
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, legendaryFilter)));

        // When Arena of the Ancients enters the battlefield, tap all Legendary creatures
        Ability tapAllLegendsAbility = new EntersBattlefieldTriggeredAbility(new TapAllEffect(legendaryFilter));
        this.addAbility(tapAllLegendsAbility);
    }
    
    private ArenaOfTheAncients(final ArenaOfTheAncients card) {
        super(card);
    }

    @Override
    public ArenaOfTheAncients copy() {
        return new ArenaOfTheAncients(this);
    }
    
}
