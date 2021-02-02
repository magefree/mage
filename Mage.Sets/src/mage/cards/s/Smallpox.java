
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author nantuko
 */
public final class Smallpox extends CardImpl {
    
    private static final FilterControlledPermanent filterCreature = new FilterControlledCreaturePermanent();
    private static final FilterControlledPermanent filterLand = new FilterControlledLandPermanent();
    
    public Smallpox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}{B}");


        // Each player loses 1 life, discards a card, sacrifices a creature, then sacrifices a land.
        this.getSpellAbility().addEffect(new LoseLifeAllPlayersEffect(1));
        Effect effect = new DiscardEachPlayerEffect();
        effect.setText(", discards a card");
        this.getSpellAbility().addEffect(effect);
        effect = new SacrificeAllEffect(1, filterCreature);
        effect.setText(", sacrifices a creature");
        this.getSpellAbility().addEffect(effect);
        effect = new SacrificeAllEffect(1, filterLand);
        effect.setText(", then sacrifices a land");
        this.getSpellAbility().addEffect(effect);
    }

    private Smallpox(final Smallpox card) {
        super(card);
    }

    @Override
    public Smallpox copy() {
        return new Smallpox(this);
    }
}
