
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2 & L_J
 */
public final class AzorTheLawbringer extends CardImpl {

    public AzorTheLawbringer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}{U}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Azor, the Lawbringer enters the battlefield, each opponent can't cast instant or sorcery spells during that player's next turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AzorTheLawbringerEntersBattlefieldEffect(), false));

        // Whenever Azor attacks, you may pay {X}{W}{U}{U}. If you do, you gain X life and draw X cards.
        this.addAbility(new AttacksTriggeredAbility(new AzorTheLawbringerAttacksEffect(), false));
    }

    private AzorTheLawbringer(final AzorTheLawbringer card) {
        super(card);
    }

    @Override
    public AzorTheLawbringer copy() {
        return new AzorTheLawbringer(this);
    }
}

class AzorTheLawbringerEntersBattlefieldEffect extends OneShotEffect {

    public AzorTheLawbringerEntersBattlefieldEffect() {
        super(Outcome.Benefit);
        this.staticText = "each opponent can't cast instant or sorcery spells during that player's next turn";
    }

    public AzorTheLawbringerEntersBattlefieldEffect(final AzorTheLawbringerEntersBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public AzorTheLawbringerEntersBattlefieldEffect copy() {
        return new AzorTheLawbringerEntersBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            ContinuousEffect effect = new AzorTheLawbringerCantCastEffect();
            effect.setTargetPointer(new FixedTarget(opponentId));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class AzorTheLawbringerCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    int playersNextTurn;

    public AzorTheLawbringerCantCastEffect() {
        super(Duration.Custom, Outcome.Detriment);
        staticText = "You can't cast instant or sorcery spells during this turn";
        playersNextTurn = 0;
    }

    public AzorTheLawbringerCantCastEffect(final AzorTheLawbringerCantCastEffect effect) {
        super(effect);
        this.playersNextTurn = effect.playersNextTurn;
    }

    @Override
    public AzorTheLawbringerCantCastEffect copy() {
        return new AzorTheLawbringerCantCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't cast instant or sorcery spells this turn (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        UUID opponentId = getTargetPointer().getFirst(game, source);
        if (game.isActivePlayer(opponentId)) {
            if (playersNextTurn == 0) {
                playersNextTurn = game.getTurnNum();
            }
            if (playersNextTurn == game.getTurnNum()) {
                if (opponentId.equals(event.getPlayerId())) {
                    MageObject object = game.getObject(event.getSourceId());
                    if (event.getType() == GameEvent.EventType.CAST_SPELL) {
                        if (object.isInstantOrSorcery(game)) {
                            return true;
                        }
                    }
                }
            } else {
                discard();
            }
        } else if (playersNextTurn > 0) {
            discard();
        }
        return false;
    }
}

class AzorTheLawbringerAttacksEffect extends OneShotEffect {

    AzorTheLawbringerAttacksEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay {X}{W}{U}{U}. If you do, you gain X life and draw X cards";
    }

    AzorTheLawbringerAttacksEffect(final AzorTheLawbringerAttacksEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ManaCosts cost = new ManaCostsImpl("{X}{W}{U}{U}");
            if (controller.chooseUse(Outcome.Damage, "Pay " + cost.getText() + "? If you do, you gain X life and draw X cards.", source, game)) {
                int costX = controller.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
                cost.add(new GenericManaCost(costX));
                if (cost.pay(source, game, source, source.getControllerId(), false, null)) {
                    controller.resetStoredBookmark(game); // otherwise you can undo the payment
                    controller.gainLife(costX, game, source);
                    controller.drawCards(costX, source, game);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public AzorTheLawbringerAttacksEffect copy() {
        return new AzorTheLawbringerAttacksEffect(this);
    }

}
