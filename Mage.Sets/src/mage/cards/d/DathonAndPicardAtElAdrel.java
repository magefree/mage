package mage.cards.d;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class DathonAndPicardAtElAdrel extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("nontoken artifact or enchantment");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(Predicates.or(
            CardType.ARTIFACT.getPredicate(),
            CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public DathonAndPicardAtElAdrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Choose one --
        // * Temba, His Arms Wide -- Target creature gets +3/+3 and gains trample until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 3).setText("target creature gets +3/+3"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance()).setText("and gains trample until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().withFirstModeFlavorWord("Temba, His Arms Wide");

        // * Shaka, When the Walls Fell -- Each opponent sacrifices a nontoken artifact or enchantment of their choice.
        this.getSpellAbility().addMode(new Mode(
            new SacrificeOpponentsEffect(filter)
        ).withFlavorWord("Shaka, When the Walls Fell"));
    }

    private DathonAndPicardAtElAdrel(final DathonAndPicardAtElAdrel card) {
        super(card);
    }

    @Override
    public DathonAndPicardAtElAdrel copy() {
        return new DathonAndPicardAtElAdrel(this);
    }
}
