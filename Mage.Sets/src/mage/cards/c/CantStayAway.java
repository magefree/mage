package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author weirddan455
 */
public final class CantStayAway extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public CantStayAway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}{B}");

        // Return target creature card with mana value 3 or less from your graveyard to the battlefield. It gains "If this creature would die, exile it instead."
        this.getSpellAbility().addEffect(new CantStayAwayEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));

        // Flashback {3}{W}{B}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl<>("{3}{W}{B}"), TimingRule.SORCERY));
    }

    private CantStayAway(final CantStayAway card) {
        super(card);
    }

    @Override
    public CantStayAway copy() {
        return new CantStayAway(this);
    }
}

class CantStayAwayEffect extends OneShotEffect {

    public CantStayAwayEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Return target creature card with mana value 3 or less from your graveyard to the battlefield. It gains \"If this creature would die, exile it instead.\"";
    }

    private CantStayAwayEffect(final CantStayAwayEffect effect) {
        super(effect);
    }

    @Override
    public CantStayAwayEffect copy() {
        return new CantStayAwayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card targetCard = game.getCard(source.getFirstTarget());
        if (controller == null || targetCard == null) {
            return false;
        }
        controller.moveCards(targetCard, Zone.BATTLEFIELD, source, game);
        Permanent permanent = game.getPermanent(targetCard.getId());
        if (permanent != null) {
            ContinuousEffect effect = new GainAbilityTargetEffect(new SimpleStaticAbility(new CantStayAwayReplacementEffect()), Duration.Custom);
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class CantStayAwayReplacementEffect extends ReplacementEffectImpl {

    public CantStayAwayReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If {this} would die, exile it instead";
    }

    private CantStayAwayReplacementEffect(final CantStayAwayReplacementEffect effect) {
        super(effect);
    }

    @Override
    public CantStayAwayReplacementEffect copy() {
        return new CantStayAwayReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())) {
            ZoneChangeEvent zce = (ZoneChangeEvent) event;
            return zce.isDiesEvent();
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }
}
