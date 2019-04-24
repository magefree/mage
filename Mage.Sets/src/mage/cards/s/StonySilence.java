
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward
 */
public final class StonySilence extends CardImpl {

    public StonySilence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");


        // Activated abilities of artifacts can't be activated.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new StonySilenceCantActivateEffect()));

    }

    public StonySilence(final StonySilence card) {
        super(card);
    }

    @Override
    public StonySilence copy() {
        return new StonySilence(this);
    }
}

class StonySilenceCantActivateEffect extends RestrictionEffect {

    public StonySilenceCantActivateEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Activated abilities of artifacts can't be activated";
    }

    public StonySilenceCantActivateEffect(final StonySilenceCantActivateEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.isArtifact();
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game) {
        return false;
    }

    @Override
    public StonySilenceCantActivateEffect copy() {
        return new StonySilenceCantActivateEffect(this);
    }

}
