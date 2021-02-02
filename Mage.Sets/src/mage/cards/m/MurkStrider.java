
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ExileOpponentsCardFromExileToGraveyardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class MurkStrider extends CardImpl {

    public MurkStrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.PROCESSOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // When Murk Strider enters the battlefield, you may put a card an opponent owns from exile into that player's graveyard. If you do, return target creature to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DoIfCostPaid(new ReturnToHandTargetEffect(), new ExileOpponentsCardFromExileToGraveyardCost(true)), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private MurkStrider(final MurkStrider card) {
        super(card);
    }

    @Override
    public MurkStrider copy() {
        return new MurkStrider(this);
    }
}
