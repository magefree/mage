package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class ArdentDustspeaker extends CardImpl {

    public ArdentDustspeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Ardent Dustspeaker attacks, you may put an instant or sorcery card from your graveyard on the bottom of your library.
        // If you do, exile the top two cards of your library. You may play those cards this turn.
        this.addAbility(new AttacksTriggeredAbility(
                new DoIfCostPaid(
                        new ExileTopXMayPlayUntilEndOfTurnEffect(2)
                                .setText("exile the top two cards of your library. You may play those cards this turn"),
                        new ArdentDustspeakerCost()
                ),
                false
        ));
    }

    private ArdentDustspeaker(final ArdentDustspeaker card) {
        super(card);
    }

    @Override
    public ArdentDustspeaker copy() {
        return new ArdentDustspeaker(this);
    }
}

class ArdentDustspeakerCost extends CostImpl {

    private static final FilterCard filter
            = new FilterInstantOrSorceryCard("instant or sorcery card from your graveyard");

    public ArdentDustspeakerCost() {
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(filter);
        target.setNotTarget(true);
        this.addTarget(target);
        this.text = "put an instant or sorcery card from your graveyard on the bottom of your library";
    }

    private ArdentDustspeakerCost(final ArdentDustspeakerCost cost) {
        super(cost);
    }

    @Override
    public ArdentDustspeakerCost copy() {
        return new ArdentDustspeakerCost(this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, source, game);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            if (controller.chooseTarget(Outcome.Benefit, targets.get(0), source, game)) {
                Card card = game.getCard(targets.get(0).getFirstTarget());
                if (card != null) {
                    controller.putCardsOnBottomOfLibrary(card, game, source, true);
                    paid = true;
                }
            }
        }
        return paid;
    }
}
