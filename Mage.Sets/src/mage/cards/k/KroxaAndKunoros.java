package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KroxaAndKunoros extends CardImpl {

    public KroxaAndKunoros(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever Kroxa and Kunoros enters the battlefield or attacks, you may exile five cards from your graveyard. When you do, return target creature card from your graveyard to the battlefield.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), false
        );
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DoWhenCostPaid(
                ability, new ExileFromGraveCost(new TargetCardInYourGraveyard(5)),
                "Exile five cards from your graveyard?"
        )));
    }

    private KroxaAndKunoros(final KroxaAndKunoros card) {
        super(card);
    }

    @Override
    public KroxaAndKunoros copy() {
        return new KroxaAndKunoros(this);
    }
}
