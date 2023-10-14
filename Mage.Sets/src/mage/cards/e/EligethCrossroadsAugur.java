package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PartnerAbility;
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
public final class EligethCrossroadsAugur extends CardImpl {

    public EligethCrossroadsAugur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If you would scry a number of cards, draw that many cards instead.
        this.addAbility(new SimpleStaticAbility(new EligethCrossroadsAugurReplacementEffect()));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private EligethCrossroadsAugur(final EligethCrossroadsAugur card) {
        super(card);
    }

    @Override
    public EligethCrossroadsAugur copy() {
        return new EligethCrossroadsAugur(this);
    }
}

class EligethCrossroadsAugurReplacementEffect extends ReplacementEffectImpl {

    EligethCrossroadsAugurReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would scry a number of cards, draw that many cards instead.";
    }

    private EligethCrossroadsAugurReplacementEffect(final EligethCrossroadsAugurReplacementEffect effect) {
        super(effect);
    }

    @Override
    public EligethCrossroadsAugurReplacementEffect copy() {
        return new EligethCrossroadsAugurReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SCRY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.drawCards(event.getAmount(), source, game); // original event is not a draw event, so skip it in params
        return true;
    }
}