package mage.cards.m;

import java.util.UUID;

import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author NinthWorld
 */
public final class MaelstromBlockade extends CardImpl {

    public MaelstromBlockade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W/B}");
        

        // Exile target attacking creature.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
    }

    private MaelstromBlockade(final MaelstromBlockade card) {
        super(card);
    }

    @Override
    public MaelstromBlockade copy() {
        return new MaelstromBlockade(this);
    }
}
