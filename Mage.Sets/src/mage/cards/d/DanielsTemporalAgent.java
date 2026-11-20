package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author muz
 */
public final class DanielsTemporalAgent extends CardImpl {

    public DanielsTemporalAgent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B/G}{B/G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Daniels enters, return target creature card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE));
        this.addAbility(ability);

        // {2}, Put this card from your graveyard on the bottom of your library: You gain 2 life.
        ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new GainLifeEffect(2), new GenericManaCost(2));
        ability.addCost(new DanielsTemporalAgentCost());
        this.addAbility(ability);
    }

    private DanielsTemporalAgent(final DanielsTemporalAgent card) {
        super(card);
    }

    @Override
    public DanielsTemporalAgent copy() {
        return new DanielsTemporalAgent(this);
    }
}

class DanielsTemporalAgentCost extends CostImpl {

    DanielsTemporalAgentCost() {
        text = "Put this card from your graveyard on the bottom of your library";
    }

    private DanielsTemporalAgentCost(final DanielsTemporalAgentCost cost) {
        super(cost);
    }

    @Override
    public DanielsTemporalAgentCost copy() {
        return new DanielsTemporalAgentCost(this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        return player != null && player.getGraveyard().contains(source.getSourceId());
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        Card card = game.getCard(source.getSourceId());
        paid = player != null && card != null && canPay(ability, source, controllerId, game)
            && player.putCardsOnBottomOfLibrary(card, game, ability);
        return paid;
    }
}
