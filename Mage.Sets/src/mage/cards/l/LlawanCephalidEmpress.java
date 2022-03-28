
package mage.cards.l;

import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class LlawanCephalidEmpress extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("blue creatures your opponents control");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(CardType.CREATURE.getPredicate());
    }

    public LlawanCephalidEmpress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CEPHALID, SubType.NOBLE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Llawan, Cephalid Empress enters the battlefield, return all blue creatures your opponents control to their owners' hands.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ReturnToHandFromBattlefieldAllEffect(filter), false));

        // Your opponents can't cast blue creature spells.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LlawanCephalidRuleModifyingEffect()));
    }

    private LlawanCephalidEmpress(final LlawanCephalidEmpress card) {
        super(card);
    }

    @Override
    public LlawanCephalidEmpress copy() {
        return new LlawanCephalidEmpress(this);
    }
}


class LlawanCephalidRuleModifyingEffect extends ContinuousRuleModifyingEffectImpl {

    private static final FilterCard filter = new FilterCard("blue creature spells");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter.add(CardType.CREATURE.getPredicate());
    }

    public LlawanCephalidRuleModifyingEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Your opponents can't cast blue creature spells";
    }

    public LlawanCephalidRuleModifyingEffect(final LlawanCephalidRuleModifyingEffect effect) {
        super(effect);
    }

    @Override
    public LlawanCephalidRuleModifyingEffect copy() {
        return new LlawanCephalidRuleModifyingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't cast blue creature spells (" + mageObject.getLogName() + " in play).";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && game.isOpponent(controller, event.getPlayerId())) {
            Card card = game.getCard(event.getSourceId());
            if (card != null && filter.match(card, source.getControllerId(), game)) {
                return true;
            }
        }
        return false;
    }

}
