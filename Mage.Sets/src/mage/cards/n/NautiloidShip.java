package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NautiloidShip extends CardImpl {

    public NautiloidShip(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Nautiloid Ship enters the battlefield, exile target player's graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileGraveyardAllTargetPlayerEffect(true));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Whenever Nautiloid Ship deals combat damage to a player, you may put a creature card exiled with Nautiloid Ship onto the battlefield under your control.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new NautiloidShipEffect(), false));

        // Crew 3
        this.addAbility(new CrewAbility(3));
    }

    private NautiloidShip(final NautiloidShip card) {
        super(card);
    }

    @Override
    public NautiloidShip copy() {
        return new NautiloidShip(this);
    }
}

class NautiloidShipEffect extends OneShotEffect {

    NautiloidShipEffect() {
        super(Outcome.Benefit);
        staticText = "you may put a creature card exiled with {this} onto the battlefield under your control";
    }

    private NautiloidShipEffect(final NautiloidShipEffect effect) {
        super(effect);
    }

    @Override
    public NautiloidShipEffect copy() {
        return new NautiloidShipEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInExile(
                0, 1,
                StaticFilters.FILTER_CARD_CREATURE,
                CardUtil.getExileZoneId(game, source)
        );
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
