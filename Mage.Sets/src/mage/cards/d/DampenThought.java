
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.abilities.keyword.SpliceOntoArcaneAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class DampenThought extends CardImpl {

    public DampenThought(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");
        this.subtype.add(SubType.ARCANE);


        // Target player puts the top four cards of their library into their graveyard.
        this.getSpellAbility().addEffect(new PutLibraryIntoGraveTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPlayer());
        // Splice onto Arcane {1}{U}
        this.addAbility(new SpliceOntoArcaneAbility("{1}{U}"));
    }

    private DampenThought(final DampenThought card) {
        super(card);
    }

    @Override
    public DampenThought copy() {
        return new DampenThought(this);
    }
}
