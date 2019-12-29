package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KruphixGodOfHorizons extends CardImpl {

    public KruphixGodOfHorizons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{G}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // As long as your devotion to green and blue is less than seven, Kruhpix isn't a creature.
        Effect effect = new LoseCreatureTypeSourceEffect(DevotionCount.GU, 7);
        effect.setText("As long as your devotion to green and blue is less than seven, {this} isn't a creature");
        this.addAbility(new SimpleStaticAbility(effect).addHint(new ValueHint("Devotion to green and blue", DevotionCount.GU)));

        // You have no maximum hand size.
        this.addAbility(new SimpleStaticAbility(new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.WhileOnBattlefield,
                MaximumHandSizeControllerEffect.HandSizeModification.SET
        )));
        // If unused mana would empty from your mana pool, that mana becomes colorless instead.
        this.addAbility(new SimpleStaticAbility(new KruphixGodOfHorizonsEffect()));

    }

    private KruphixGodOfHorizons(final KruphixGodOfHorizons card) {
        super(card);
    }

    @Override
    public KruphixGodOfHorizons copy() {
        return new KruphixGodOfHorizons(this);
    }
}

class KruphixGodOfHorizonsEffect extends ReplacementEffectImpl {

    KruphixGodOfHorizonsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would lose unspent mana, that mana becomes colorless instead.";
    }

    private KruphixGodOfHorizonsEffect(final KruphixGodOfHorizonsEffect effect) {
        super(effect);
    }

    @Override
    public KruphixGodOfHorizonsEffect copy() {
        return new KruphixGodOfHorizonsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.EMPTY_MANA_POOL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }
}
