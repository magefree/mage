
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public final class DebtorsKnell extends CardImpl {

    public DebtorsKnell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{W/B}{W/B}{W/B}");


        // <i>({WB} can be paid with either {W} or {B}.)</i>
        // At the beginning of your upkeep, put target creature card from a graveyard onto the battlefield under your control.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
        this.addAbility(ability);
        
    }

    private DebtorsKnell(final DebtorsKnell card) {
        super(card);
    }

    @Override
    public DebtorsKnell copy() {
        return new DebtorsKnell(this);
    }
}
