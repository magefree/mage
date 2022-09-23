package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TomikDistinguishedAdvokist extends CardImpl {

    public TomikDistinguishedAdvokist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.ADVISOR);
        this.subtype.add();
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lands on the battlefield and land cards in graveyards can't be the targets of spells or abilities your opponents control.
        this.addAbility(new SimpleStaticAbility(new TomikDistinguishedAdvokistTargetEffect()));

        // Your opponents can't play land cards from graveyards.
        this.addAbility(new SimpleStaticAbility(new TomikDistinguishedAdvokistRestrictionEffect()));
    }

    private TomikDistinguishedAdvokist(final TomikDistinguishedAdvokist card) {
        super(card);
    }

    @Override
    public TomikDistinguishedAdvokist copy() {
        return new TomikDistinguishedAdvokist(this);
    }
}

class TomikDistinguishedAdvokistTargetEffect extends ContinuousRuleModifyingEffectImpl {

    TomikDistinguishedAdvokistTargetEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "lands on the battlefield and land cards in graveyards can't be the targets of spells or abilities your opponents control";
    }

    private TomikDistinguishedAdvokistTargetEffect(final TomikDistinguishedAdvokistTargetEffect effect) {
        super(effect);
    }

    @Override
    public TomikDistinguishedAdvokistTargetEffect copy() {
        return new TomikDistinguishedAdvokistTargetEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGET;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        UUID targetId = event.getTargetId();
        Zone zone = game.getState().getZone(targetId);
        if (zone != Zone.BATTLEFIELD && zone != Zone.GRAVEYARD) {
            return false;
        }
        Card targetCard = (zone == Zone.BATTLEFIELD ? game.getPermanent(targetId) : game.getCard(targetId));
        Player player = game.getPlayer(source.getControllerId());
        return targetCard != null && player != null && targetCard.isLand(game) && player.hasOpponent(event.getPlayerId(), game);
    }
}

class TomikDistinguishedAdvokistRestrictionEffect extends ContinuousRuleModifyingEffectImpl {

    TomikDistinguishedAdvokistRestrictionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.staticText = "Your opponents can't play land cards from graveyards";
    }

    private TomikDistinguishedAdvokistRestrictionEffect(final TomikDistinguishedAdvokistRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public TomikDistinguishedAdvokistRestrictionEffect copy() {
        return new TomikDistinguishedAdvokistRestrictionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PLAY_LAND;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.hasOpponent(event.getPlayerId(), game)
                && game.getState().getZone(event.getSourceId()) == Zone.GRAVEYARD;
    }
}
