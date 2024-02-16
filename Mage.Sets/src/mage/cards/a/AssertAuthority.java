package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetWithReplacementEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.target.TargetSpell;

/**
 *
 * @author North
 */
public final class AssertAuthority extends CardImpl {

    public AssertAuthority(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{5}{U}{U}");


        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());
        // Counter target spell. If that spell is countered this way, exile it instead of putting it into its owner's graveyard.
        this.getSpellAbility().addEffect(new CounterTargetWithReplacementEffect(PutCards.EXILED));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private AssertAuthority(final AssertAuthority card) {
        super(card);
    }

    @Override
    public AssertAuthority copy() {
        return new AssertAuthority(this);
    }
}
