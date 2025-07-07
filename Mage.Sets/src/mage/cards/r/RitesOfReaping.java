package mage.cards.r;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RitesOfReaping extends CardImpl {

    public RitesOfReaping(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{G}");

        // Target creature gets +3/+3 until end of turn. Another target creature gets -3/-3 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 3));
        this.getSpellAbility().addEffect(new BoostTargetEffect(-3, -3)
                .setTargetPointer(new SecondTargetPointer())
                .setText("Another target creature gets -3/-3 until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("+3/+3").setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2).withChooseHint("-3/-3").setTargetTag(2));
    }

    private RitesOfReaping(final RitesOfReaping card) {
        super(card);
    }

    @Override
    public RitesOfReaping copy() {
        return new RitesOfReaping(this);
    }
}
