package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PlanetariumOfWanShiTong extends CardImpl {

    public PlanetariumOfWanShiTong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        this.supertype.add(SuperType.LEGENDARY);

        // {1}, {T}: Scry 2.
        Ability ability = new SimpleActivatedAbility(new ScryEffect(2), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Whenever you scry or surveil, look at the top card of your library. You may cast that card without paying its mana cost. Do this only once each turn.
        this.addAbility(new PlanetariumOfWanShiTongTriggeredAbility());
    }

    private PlanetariumOfWanShiTong(final PlanetariumOfWanShiTong card) {
        super(card);
    }

    @Override
    public PlanetariumOfWanShiTong copy() {
        return new PlanetariumOfWanShiTong(this);
    }
}

class PlanetariumOfWanShiTongTriggeredAbility extends TriggeredAbilityImpl {

    PlanetariumOfWanShiTongTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PlanetariumOfWanShiTongEffect());
        this.setTriggerPhrase("Whenever you scry or surveil, ");
        this.setDoOnlyOnceEachTurn(true);
        this.setOptional(false);
    }

    private PlanetariumOfWanShiTongTriggeredAbility(final PlanetariumOfWanShiTongTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PlanetariumOfWanShiTongTriggeredAbility copy() {
        return new PlanetariumOfWanShiTongTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case SCRIED:
            case SURVEILED:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId());
    }
}

class PlanetariumOfWanShiTongEffect extends OneShotEffect {

    PlanetariumOfWanShiTongEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. You may cast that card without paying its mana cost";
    }

    private PlanetariumOfWanShiTongEffect(final PlanetariumOfWanShiTongEffect effect) {
        super(effect);
    }

    @Override
    public PlanetariumOfWanShiTongEffect copy() {
        return new PlanetariumOfWanShiTongEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.lookAtCards("Top of library", card, game);
        if (!CardUtil.castSpellWithAttributesForFree(player, source, game, card)) {
            return false;
        }
        TriggeredAbility.setDidThisTurn(source, game);
        return true;
    }
}
