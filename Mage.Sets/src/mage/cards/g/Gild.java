
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.GoldToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Gild extends CardImpl {

    public Gild(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}");


        // Exile target creature.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Create a colorless artifact token named Gold. It has "Sacrifice this artifact: Add one mana of any color."
        Effect effect = new CreateTokenEffect(new GoldToken());
        effect.setText("Create a colorless artifact token named Gold. It has \"Sacrifice this artifact: Add one mana of any color.\"");
        this.getSpellAbility().addEffect(effect);
    }

    public Gild(final Gild card) {
        super(card);
    }

    @Override
    public Gild copy() {
        return new Gild(this);
    }
}
