package mage.cards.n;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NyxbloomAncient extends CardImpl {

    public NyxbloomAncient(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{G}{G}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // If you tap a permanent for mana, it produces three times as much of that mana instead.
        this.addAbility(new SimpleStaticAbility(new NyxbloomAncientReplacementEffect()));
    }

    private NyxbloomAncient(final NyxbloomAncient card) {
        super(card);
    }

    @Override
    public NyxbloomAncient copy() {
        return new NyxbloomAncient(this);
    }
}

class NyxbloomAncientReplacementEffect extends ReplacementEffectImpl {

    NyxbloomAncientReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you tap a permanent for mana, it produces three times as much of that mana instead";
    }

    private NyxbloomAncientReplacementEffect(NyxbloomAncientReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Mana mana = ((ManaEvent) event).getMana();
        if (mana.getBlack() > 0) {
            mana.set(ManaType.BLACK, CardUtil.overflowMultiply(mana.getBlack(), 3));
        }
        if (mana.getBlue() > 0) {
            mana.set(ManaType.BLUE, CardUtil.overflowMultiply(mana.getBlue(), 3));
        }
        if (mana.getWhite() > 0) {
            mana.set(ManaType.WHITE, CardUtil.overflowMultiply(mana.getWhite(), 3));
        }
        if (mana.getGreen() > 0) {
            mana.set(ManaType.GREEN, CardUtil.overflowMultiply(mana.getGreen(), 3));
        }
        if (mana.getRed() > 0) {
            mana.set(ManaType.RED, CardUtil.overflowMultiply(mana.getRed(), 3));
        }
        if (mana.getColorless() > 0) {
            mana.set(ManaType.COLORLESS, CardUtil.overflowMultiply(mana.getColorless(), 3));
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }

    @Override
    public NyxbloomAncientReplacementEffect copy() {
        return new NyxbloomAncientReplacementEffect(this);
    }
}
