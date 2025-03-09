package mage.cards.s;

import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.game.events.CreateTokenEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.ThopterColorlessToken;


/**
 * @author grimreap124
 */
public final class StridehangarAutomaton extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.THOPTER, "Thopters");

    public StridehangarAutomaton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Thopters you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter
        )));
        // If one or more artifact tokens would be created under your control, those tokens plus an additional 1/1 colorless Thopter artifact creature token with flying are created instead.
        this.addAbility(new SimpleStaticAbility(new StridehangarAutomatonReplacementEffect()));
    }

    private StridehangarAutomaton(final StridehangarAutomaton card) {
        super(card);
    }

    @Override
    public StridehangarAutomaton copy() {
        return new StridehangarAutomaton(this);
    }
}

class StridehangarAutomatonReplacementEffect extends ReplacementEffectImpl {

    StridehangarAutomatonReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "If one or more artifact tokens would be created under your control, those tokens plus an additional 1/1 colorless Thopter artifact creature token with flying are created instead";
    }

    private StridehangarAutomatonReplacementEffect(final StridehangarAutomatonReplacementEffect effect) {
        super(effect);
    }

    @Override
    public StridehangarAutomatonReplacementEffect copy() {
        return new StridehangarAutomatonReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATE_TOKEN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (source.isControlledBy(event.getPlayerId())) {
            for (Map.Entry<Token, Integer> entry : ((CreateTokenEvent) event).getTokens().entrySet()) {
                if (entry.getValue() > 0 && entry.getKey().isArtifact(game)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (event instanceof CreateTokenEvent) {
            CreateTokenEvent tokenEvent = (CreateTokenEvent) event;
            ThopterColorlessToken thopterToken = null;
            Map<Token, Integer> tokens = tokenEvent.getTokens();
            for (Map.Entry<Token, Integer> entry : tokens.entrySet()) {
                if (entry.getKey() instanceof ThopterColorlessToken) {
                    thopterToken = (ThopterColorlessToken) entry.getKey();
                }
            }
            if (thopterToken == null) {
                thopterToken = new ThopterColorlessToken();
            }
            tokens.put(thopterToken, tokens.getOrDefault(thopterToken, 0) + 1);
        }
        return false;
    }
}
