
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author fireshoes
 */
public final class HeartwoodDryad extends CardImpl {

    public HeartwoodDryad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.DRYAD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Heartwood Dryad can block creatures with shadow as though Heartwood Dryad had shadow.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CanBlockAsThoughtIthadShadowEffect(Duration.WhileOnBattlefield)));
    }

    public HeartwoodDryad(final HeartwoodDryad card) {
        super(card);
    }

    @Override
    public HeartwoodDryad copy() {
        return new HeartwoodDryad(this);
    }
}

class CanBlockAsThoughtIthadShadowEffect extends AsThoughEffectImpl {

    public CanBlockAsThoughtIthadShadowEffect(Duration duration) {
        super(AsThoughEffectType.BLOCK_SHADOW, duration, Outcome.Benefit);
        staticText = "{this} can block creatures with shadow as though {this} had shadow";
    }

    public CanBlockAsThoughtIthadShadowEffect(final CanBlockAsThoughtIthadShadowEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CanBlockAsThoughtIthadShadowEffect copy() {
        return new CanBlockAsThoughtIthadShadowEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return sourceId.equals(source.getSourceId());
    }

}