package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class KnightsCharge extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent(SubType.KNIGHT);

    public KnightsCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{B}");

        // Whenever a Knight you control attacks, each opponent loses 1 life and you gain 1 life.
        Ability ability = new AttacksCreatureYouControlTriggeredAbility(
                new LoseLifeOpponentsEffect(1), false, filter
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);

        // {6}{W}{B}, Sacrifice Knights' Charge: Return all Knight creature cards from your graveyard to the battlefield.
        ability = new SimpleActivatedAbility(new KnightsChargeEffect(), new ManaCostsImpl<>("{6}{W}{B}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private KnightsCharge(final KnightsCharge card) {
        super(card);
    }

    @Override
    public KnightsCharge copy() {
        return new KnightsCharge(this);
    }
}

class KnightsChargeEffect extends OneShotEffect {

    KnightsChargeEffect() {
        super(Outcome.Benefit);
        staticText = "return all Knight creature cards from your graveyard to the battlefield";
    }

    private KnightsChargeEffect(final KnightsChargeEffect effect) {
        super(effect);
    }

    @Override
    public KnightsChargeEffect copy() {
        return new KnightsChargeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.moveCards(new CardsImpl(
                player.getGraveyard()
                        .getCards(game)
                        .stream()
                        .filter(card1 -> card1.isCreature(game))
                        .filter(card -> card.hasSubtype(SubType.KNIGHT, game))
                        .collect(Collectors.toSet())
        ), Zone.BATTLEFIELD, source, game);
    }
}
