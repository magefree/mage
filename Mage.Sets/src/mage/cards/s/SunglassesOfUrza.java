
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.ManaPoolItem;

/**
 *
 * @author LevelX2
 */
public final class SunglassesOfUrza extends CardImpl {

    public SunglassesOfUrza(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // You may spend white mana as though it were red mana.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SunglassesOfUrzaManaAsThoughtEffect()));
    }

    public SunglassesOfUrza(final SunglassesOfUrza card) {
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
        return affectedControllerId.equals(source.getControllerId());
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        if (mana.getWhite() > 0 && ManaType.RED == manaType) {
            return ManaType.WHITE;
        }
        return manaType;
    }

    @Override
    public SunglassesOfUrzaManaAsThoughtEffect copy() {
        return new SunglassesOfUrzaManaAsThoughtEffect(this);
    }

    private SunglassesOfUrzaManaAsThoughtEffect(SunglassesOfUrzaManaAsThoughtEffect effect) {
        super(effect);
    }
}
