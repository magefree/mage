package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShatterTheSource extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature, planeswalker, or battle");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate(),
                CardType.BATTLE.getPredicate()
        ));
    }

    public ShatterTheSource(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{R}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Choose one --
        // * Shatter the Source deals 6 damage to target creature, planeswalker, or battle.
        this.getSpellAbility().addEffect(new DamageTargetEffect(6));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // * Destroy target artifact.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetArtifactPermanent()));
    }

    private ShatterTheSource(final ShatterTheSource card) {
        super(card);
    }

    @Override
    public ShatterTheSource copy() {
        return new ShatterTheSource(this);
    }
}
