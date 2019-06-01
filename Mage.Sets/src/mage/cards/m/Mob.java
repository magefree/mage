package mage.cards.m;

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
public final class Mob extends CardImpl {

    public Mob(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Destroy target creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Mob(final Mob card) {
        super(card);
    }

    @Override
    public Mob copy() {
        return new Mob(this);
    }
}
