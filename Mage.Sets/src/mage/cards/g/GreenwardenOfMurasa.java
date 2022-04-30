package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class GreenwardenOfMurasa extends CardImpl {

    public GreenwardenOfMurasa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // When Greenwarden of Murasa enters the battlefield, you may return target card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard());
        this.addAbility(ability);

        // When Greenwarden of  Murasa dies, you may exile it. If you do, return target card from your graveyard to your hand.
        ability = new DiesSourceTriggeredAbility(new DoIfCostPaid(
                new ReturnFromGraveyardToHandTargetEffect(), new ExileSourceFromGraveCost().setText("exile it")
        ), false);
        ability.addTarget(new TargetCardInYourGraveyard());
        this.addAbility(ability);
    }

    private GreenwardenOfMurasa(final GreenwardenOfMurasa card) {
        super(card);
    }

    @Override
    public GreenwardenOfMurasa copy() {
        return new GreenwardenOfMurasa(this);
    }
}
