
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class RazormaneMasticore extends CardImpl {

    public RazormaneMasticore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.MASTICORE);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // At the beginning of your upkeep, sacrifice Razormane Masticore unless you discard a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new DiscardTargetCost(new TargetCardInHand()))));

        // At the beginning of your draw step, you may have Razormane Masticore deal 3 damage to target creature.
        Ability ability = new BeginningOfDrawTriggeredAbility(new DamageTargetEffect(3), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private RazormaneMasticore(final RazormaneMasticore card) {
        super(card);
    }

    @Override
    public RazormaneMasticore copy() {
        return new RazormaneMasticore(this);
    }
}
