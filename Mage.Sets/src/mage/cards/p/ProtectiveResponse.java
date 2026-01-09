package mage.cards.p;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingOrBlockingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProtectiveResponse extends CardImpl {

    public ProtectiveResponse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Destroy target attacking or blocking creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
    }

    private ProtectiveResponse(final ProtectiveResponse card) {
        super(card);
    }

    @Override
    public ProtectiveResponse copy() {
        return new ProtectiveResponse(this);
    }
}
