
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.MerfolkHexproofToken;
import mage.target.TargetPermanent;

/**
 *
 * @author L_J
 */
public final class AquaticIncursion extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("Merfolk");

    static {
        filter.add(SubType.MERFOLK.getPredicate());
    }

    public AquaticIncursion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");
        
        // When Aquatic Incursion enters the battlefield, create two 1/1 blue Merfolk creature tokens with hexproof.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MerfolkHexproofToken(), 2), false));
        
        // {3}{U}: Target Merfolk can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBeBlockedTargetEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{3}{U}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private AquaticIncursion(final AquaticIncursion card) {
        super(card);
    }

    @Override
    public AquaticIncursion copy() {
        return new AquaticIncursion(this);
    }
}
