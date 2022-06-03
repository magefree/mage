package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SivrissNightmareSpeaker extends CardImpl {

    public SivrissNightmareSpeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {T}, Sacrifice another creature or an artifact: For each opponent, you mill a card, then return that card from your graveyard to your hand unless that player pays 3 life.
        Ability ability = new SimpleActivatedAbility(new SivrissNightmareSpeakerEffect(), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ARTIFACT_OR_OTHER_CREATURE));
        this.addAbility(ability);

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private SivrissNightmareSpeaker(final SivrissNightmareSpeaker card) {
        super(card);
    }

    @Override
    public SivrissNightmareSpeaker copy() {
        return new SivrissNightmareSpeaker(this);
    }
}

class SivrissNightmareSpeakerEffect extends OneShotEffect {

    SivrissNightmareSpeakerEffect() {
        super(Outcome.Benefit);
        staticText = "for each opponent, you mill a card, then return that card " +
                "from your graveyard to your hand unless that player pays 3 life";
    }

    private SivrissNightmareSpeakerEffect(final SivrissNightmareSpeakerEffect effect) {
        super(effect);
    }

    @Override
    public SivrissNightmareSpeakerEffect copy() {
        return new SivrissNightmareSpeakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(playerId);
            if (opponent == null) {
                continue;
            }
            Cards cards = player.millCards(1, source, game);
            Cost cost = new PayLifeCost(3);
            if (!cost.canPay(source, source, playerId, game)
                    || !opponent.chooseUse(outcome, "Pay 3 life?", source, game)
                    || !cost.pay(source, game, source, playerId, true)) {
                cards.retainZone(Zone.GRAVEYARD, game);
                player.moveCards(cards, Zone.HAND, source, game);
            }
        }
        return true;
    }
}
