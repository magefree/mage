
package mage.cards.c;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author Styxo
 */
public final class ColfenorsUrn extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature with toughness 4 or greater");

    static {
        filter.add(new ToughnessPredicate(ComparisonType.MORE_THAN, 3));
    }

    public ColfenorsUrn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever a creature with toughness 4 or greater is put into your graveyard from the battlefield, you may exile it.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new ExileTargetForSourceEffect().setText("exile it"), true, filter, true, true));

        // At the beginning of the end step, if three or more cards have been exiled with Colfenor's Urn, sacrifice it. If you do, return those cards to the battlefield under their owner's control.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new ColfenorsUrnEffect(), TargetController.NEXT, new ColfenorsUrnCondition(), false));
    }

    private ColfenorsUrn(final ColfenorsUrn card) {
        super(card);
    }

    @Override
    public ColfenorsUrn copy() {
        return new ColfenorsUrn(this);
    }
}

class ColfenorsUrnEffect extends OneShotEffect {

    public ColfenorsUrnEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "If you do, return those cards to the battlefield under their owner's control";
    }

    public ColfenorsUrnEffect(final ColfenorsUrnEffect effect) {
        super(effect);
    }

    @Override
    public ColfenorsUrnEffect copy() {
        return new ColfenorsUrnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = (Permanent) source.getSourceObjectIfItStillExists(game);
        if (controller != null && permanent != null) {
            UUID exileId = CardUtil.getCardExileZoneId(game, source);
            ExileZone exile = game.getExile().getExileZone(exileId);
            if (permanent.sacrifice(source, game)) {
                controller.moveCards(exile.getCards(game), Zone.BATTLEFIELD, source, game);
            }
            return true;

        }
        return false;
    }
}

class ColfenorsUrnCondition implements Condition {

    @Override
    public final boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            UUID exileId = CardUtil.getCardExileZoneId(game, source);
            ExileZone exile = game.getExile().getExileZone(exileId);
            if (exile != null) {
                return exile.size() > 2;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "if three or more cards have been exiled with Colfenor's Urn, sacrifice it.";
    }
}
