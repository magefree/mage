package mage.cards.u;

import mage.abilities.Mode;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterSpell;
import mage.filter.predicate.other.NumberOfTargetsPredicate;
import mage.target.TargetSpell;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UntimelyMalfunction extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell with a single target");

    static {
        filter.add(new NumberOfTargetsPredicate(1));
    }

    public UntimelyMalfunction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Choose one --
        // * Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());

        // * Change the target of target spell or ability with a single target.
        this.getSpellAbility().addMode(new Mode(new ChooseNewTargetsTargetEffect(true, true)).addTarget(new TargetSpell(filter)));

        // * One or two target creatures can't block this turn.
        this.getSpellAbility().addMode(new Mode(new CantBlockTargetEffect(Duration.EndOfTurn)).addTarget(new TargetCreaturePermanent(1, 2)));
    }

    private UntimelyMalfunction(final UntimelyMalfunction card) {
        super(card);
    }

    @Override
    public UntimelyMalfunction copy() {
        return new UntimelyMalfunction(this);
    }
}
