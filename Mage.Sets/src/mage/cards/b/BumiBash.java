package mage.cards.b;

import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BumiBash extends CardImpl {

    private static final FilterPermanent filter = new FilterLandPermanent("land creature or nonbasic land");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                Predicates.not(SuperType.BASIC.getPredicate())
        ));
    }

    public BumiBash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Choose one --
        // * Bumi Bash deals damage equal to the number of lands you control to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(LandsYouControlCount.instance)
                .setText("{this} deals damage equal to the number of lands you control to target creature"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(LandsYouControlHint.instance);

        // * Destroy target land creature or nonbasic land.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetPermanent(filter)));
    }

    private BumiBash(final BumiBash card) {
        super(card);
    }

    @Override
    public BumiBash copy() {
        return new BumiBash(this);
    }
}
