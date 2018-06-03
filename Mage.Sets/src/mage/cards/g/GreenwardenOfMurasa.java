
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
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
        ability = new DiesTriggeredAbility(new DoIfCostPaid(new ReturnFromGraveyardToHandTargetEffect(), new ExileSourceFromGraveCost(),
                "Exile {this} and return target card from your graveyard to your hand?", true), false);
        ability.addTarget(new TargetCardInYourGraveyard());
        this.addAbility(ability);
    }

    public GreenwardenOfMurasa(final GreenwardenOfMurasa card) {
        super(card);
    }

    @Override
    public GreenwardenOfMurasa copy() {
        return new GreenwardenOfMurasa(this);
    }
}

//class GreenwardenOfMurasaEffect extends OneShotEffect {
//
//    public GreenwardenOfMurasaEffect() {
//        super(Outcome.Benefit);
//        this.staticText = "you may exile it. If you do, return target card from your graveyard to your hand";
//    }
//
//    public GreenwardenOfMurasaEffect(final GreenwardenOfMurasaEffect effect) {
//        super(effect);
//    }
//
//    @Override
//    public GreenwardenOfMurasaEffect copy() {
//        return new GreenwardenOfMurasaEffect(this);
//    }
//
//    @Override
//    public boolean apply(Game game, Ability source) {
//        Player controller = game.getPlayer(source.getControllerId());
//        MageObject sourceObject = game.getObject(source.getSourceId());
//        Card targetCard = game.getCard(getTargetPointer().getFirst(game, source));
//        if (controller != null && sourceObject != null && targetCard != null) {
//            if (controller.chooseUse(outcome, "Exile " + sourceObject.getLogName() + " to return card from your graveyard to your hand?", source, game)) {
//                // Setting the fixed target prevents to return Greenwarden of Murasa itself (becuase it's exiled meanwhile),
//                // but of course you can target it as the ability triggers I guess
//                Effect effect = new ReturnToHandTargetEffect();
//                effect.setTargetPointer(new FixedTarget(targetCard.getId(), targetCard.getZoneChangeCounter(game)));
//                new ExileSourceEffect().apply(game, source);
//                return effect.apply(game, source);
//            }
//            return true;
//        }
//        return false;
//    }
//}
