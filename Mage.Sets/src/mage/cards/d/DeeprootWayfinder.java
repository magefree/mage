package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
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
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeeprootWayfinder extends CardImpl {

    public DeeprootWayfinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Deeproot Wayfinder deals combat damage to a player or battle, surveil 1, then you may return a land card from your graveyard to the battlefield tapped.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DeeprootWayfinderEffect(), false).setOrBattle(true));
    }

    private DeeprootWayfinder(final DeeprootWayfinder card) {
        super(card);
    }

    @Override
    public DeeprootWayfinder copy() {
        return new DeeprootWayfinder(this);
    }
}

class DeeprootWayfinderEffect extends OneShotEffect {

    DeeprootWayfinderEffect() {
        super(Outcome.Benefit);
        staticText = "surveil 1, then you may return a land card from your graveyard to the battlefield tapped";
    }

    private DeeprootWayfinderEffect(final DeeprootWayfinderEffect effect) {
        super(effect);
    }

    @Override
    public DeeprootWayfinderEffect copy() {
        return new DeeprootWayfinderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.surveil(1, source, game);
        TargetCard target = new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_LAND_A);
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return player.moveCards(
                card, Zone.BATTLEFIELD, source, game, true,
                false, false, null
        );
    }
}
