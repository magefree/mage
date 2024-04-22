
package mage.cards.d;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class DeathCloud extends CardImpl {

    public DeathCloud(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{B}{B}{B}");


        // Each player loses X life, discards X cards, sacrifices X creatures, then sacrifices X lands.
        DynamicValue xValue = ManacostVariableValue.REGULAR;
        this.getSpellAbility().addEffect(new LoseLifeAllPlayersEffect(xValue));
        Effect effect = new DiscardEachPlayerEffect(xValue, false);
        effect.setText(", discards X cards");
        this.getSpellAbility().addEffect(effect);
        effect = new SacrificeAllEffect(xValue, new FilterControlledCreaturePermanent("creatures"));
        effect.setText(", sacrifices X creatures");
        this.getSpellAbility().addEffect(effect);
        effect = new SacrificeAllEffect(xValue, new FilterControlledLandPermanent("lands"));
        effect.setText(", then sacrifices X lands");
        this.getSpellAbility().addEffect(effect);
    }

    private DeathCloud(final DeathCloud card) {
        super(card);
    }

    @Override
    public DeathCloud copy() {
        return new DeathCloud(this);
    }
}
