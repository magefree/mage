package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public final class CorneredMarket extends CardImpl {

    public CorneredMarket(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Players can't cast spells with the same name as a nontoken permanent.
        // Players can't play nonbasic lands with the same name as a nontoken permanent.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CorneredMarketReplacementEffect()));

    }

    private CorneredMarket(final CorneredMarket card) {
        super(card);
    }

    @Override
    public CorneredMarket copy() {
        return new CorneredMarket(this);
    }
}

class CorneredMarketReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public CorneredMarketReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral, true, true);
        staticText = "Players can't cast spells with the same name as a nontoken permanent."
                + "<br> Players can't play nonbasic lands with the same name as a nontoken permanent.";
    }

    CorneredMarketReplacementEffect(final CorneredMarketReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL_LATE
                || event.getType() == GameEvent.EventType.PLAY_LAND;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(event.getSourceId());
        if (card != null) {
            Spell spell = game.getState().getStack().getSpell(event.getSourceId());
            // Face Down cast spell (Morph creature) has no name
            if (spell != null
                    && spell.isFaceDown(game)) {
                return false;
            }
            // play land check
            if (card.isLand(game)
                    && !card.isBasic(game)) {
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, game)) {
                    if (permanent != null) {
                        if (CardUtil.haveSameNames(card, permanent.getName(), game)) {
                            return true;
                        }
                    }
                }
                return false;
            }
            // cast spell check
            if (spell != null) {
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, game)) {
                    if (permanent != null) {
                        if (CardUtil.haveSameNames(card, permanent.getName(), game)) {
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
    public CorneredMarketReplacementEffect copy() {
        return new CorneredMarketReplacementEffect(this);
    }
}
