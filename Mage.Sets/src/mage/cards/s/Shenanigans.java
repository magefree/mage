package mage.cards.s;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.DredgeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Shenanigans extends CardImpl {

    public Shenanigans(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());

        // Dredge 1
        this.addAbility(new DredgeAbility(1));
    }

    private Shenanigans(final Shenanigans card) {
        super(card);
    }

    @Override
    public Shenanigans copy() {
        return new Shenanigans(this);
    }
}
// Hey Farva what's the name of that restaurant you like with all the goofy shit on the walls and the mozzarella sticks?
