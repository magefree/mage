package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IxalansBinding extends CardImpl {

    public IxalansBinding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // When Ixalan's Binding enters the battlefield, exile target nonland permanent an opponent controls until Ixalan's Binding leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND));
        this.addAbility(ability);

        // Your opponents can't cast spells with the same name as the exiled card.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new IxalansBindingReplacementEffect()));
    }

    private IxalansBinding(final IxalansBinding card) {
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
        if (event.getPlayerId().equals(source.getControllerId())) {
            return false;
        }
        Card card = game.getCard(event.getSourceId());
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
                    for (Card e : exile.getCards(game)) {
                        if (CardUtil.haveSameNames(e, card)) {
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
