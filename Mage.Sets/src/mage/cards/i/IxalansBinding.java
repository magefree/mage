
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

/**
 *
 * @author TheElk801
 */
public final class IxalansBinding extends CardImpl {

    private final static FilterNonlandPermanent filter = new FilterNonlandPermanent();

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public IxalansBinding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // When Ixalan's Binding enters the battlefield, exile target nonland permanent an opponent controls until Ixalan's Binding leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect(filter.getMessage()));
        ability.addTarget(new TargetPermanent(filter));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);

        // Your opponents can't cast spells with the same name as the exiled card.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new IxalansBindingReplacementEffect()));
    }

    public IxalansBinding(final IxalansBinding card) {
        super(card);
    }

    @Override
    public IxalansBinding copy() {
        return new IxalansBinding(this);
    }
}

class IxalansBindingReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    IxalansBindingReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Your opponents can't cast spells with the same name as the exiled card";
    }

    IxalansBindingReplacementEffect(final IxalansBindingReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL_LATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Card card = game.getCard(event.getSourceId());
        if(event.getPlayerId().equals(source.getControllerId())){
            return false;
        }
        if (sourcePermanent != null && card != null) {
            UUID exileZone = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
            if (exileZone != null) {
                ExileZone exile = game.getExile().getExileZone(exileZone);
                if (exile == null) {
                    // try without ZoneChangeCounter - that is useful for tokens
                    exileZone = CardUtil.getCardExileZoneId(game, source);
                    if (exileZone != null) {
                        exile = game.getExile().getExileZone(exileZone);
                    }
                }
                if (exile != null) {
                    for (Card crad : exile.getCards(game)) {
                        if (crad.getName().equals(card.getName())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public IxalansBindingReplacementEffect copy() {
        return new IxalansBindingReplacementEffect(this);
    }
}
