package mage.cards.v;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VoteOut extends CardImpl {

    public VoteOut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Destroy target creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private VoteOut(final VoteOut card) {
        super(card);
    }

    @Override
    public VoteOut copy() {
        return new VoteOut(this);
    }
}
