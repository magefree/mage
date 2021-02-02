
package mage.cards.n;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.LoseAllCreatureTypesTargetEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class NamelessInversion extends CardImpl {

    public NamelessInversion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.INSTANT},"{1}{B}");
        this.subtype.add(SubType.SHAPESHIFTER);


        // Changeling
        this.addAbility(new ChangelingAbility());
        
        // Target creature gets +3/-3 and loses all creature types until end of turn.
        Effect effect = new BoostTargetEffect(3, -3, Duration.EndOfTurn);
        effect.setText("Target creature gets +3/-3");
        this.getSpellAbility().addEffect(effect);
        effect = new LoseAllCreatureTypesTargetEffect(Duration.EndOfTurn);
        effect.setText("and loses all creature types until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private NamelessInversion(final NamelessInversion card) {
        super(card);
    }

    @Override
    public NamelessInversion copy() {
        return new NamelessInversion(this);
    }
}