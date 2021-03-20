package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.ManaPoolItem;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SunglassesOfUrza extends CardImpl {

    public SunglassesOfUrza(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // You may spend white mana as though it were red mana.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SunglassesOfUrzaManaAsThoughtEffect()));
    }

    private SunglassesOfUrza(final SunglassesOfUrza card) {
        super(card);
    }

    @Override
    public SunglassesOfUrza copy() {
        return new SunglassesOfUrza(this);
    }
}

class SunglassesOfUrzaManaAsThoughtEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public SunglassesOfUrzaManaAsThoughtEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may spend white mana as though it were red mana";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId);
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        if (mana.getWhite() > 0 && ManaType.RED == manaType) {
            return ManaType.WHITE;
        }
        return null;
    }

    @Override
    public SunglassesOfUrzaManaAsThoughtEffect copy() {
        return new SunglassesOfUrzaManaAsThoughtEffect(this);
    }

    private SunglassesOfUrzaManaAsThoughtEffect(SunglassesOfUrzaManaAsThoughtEffect effect) {
        super(effect);
    }
}
