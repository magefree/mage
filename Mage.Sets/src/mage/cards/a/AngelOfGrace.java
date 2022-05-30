package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.SetPlayerLifeSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
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
public final class AngelOfGrace extends CardImpl {

    public AngelOfGrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Angel of Grace enters the battlefield, until end of turn, damage that would reduce your life total to less than 1 reduces it to 1 instead.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AngelOfGraceReplacementEffect()));

        // {4}{W}{W}, Exile Angel of Grace from your graveyard: Your life total becomes 10.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD, new SetPlayerLifeSourceEffect(10), new ManaCostsImpl<>("{4}{W}{W}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private AngelOfGrace(final AngelOfGrace card) {
        super(card);
    }

    @Override
    public AngelOfGrace copy() {
        return new AngelOfGrace(this);
    }
}

class AngelOfGraceReplacementEffect extends ReplacementEffectImpl {

    AngelOfGraceReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "until end of turn, damage that would reduce your life total to less than 1 reduces it to 1 instead";
    }

    private AngelOfGraceReplacementEffect(final AngelOfGraceReplacementEffect effect) {
        super(effect);
    }

    @Override
    public AngelOfGraceReplacementEffect copy() {
        return new AngelOfGraceReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_CAUSES_LIFE_LOSS;
    }


    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null
                    && (controller.getLife() - event.getAmount()) < 1) {
                event.setAmount(controller.getLife() - 1);
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return false;
    }

}
