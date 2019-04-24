
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.abilities.effects.common.continuous.GainControlAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.other.OwnerPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class GruulCharm extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures without flying");
    private static final FilterPermanent filter2 = new FilterPermanent("all permanents you own");
    private static final FilterCreaturePermanent filter3 = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
        filter2.add(new OwnerPredicate(TargetController.YOU));
        filter3.add(new AbilityPredicate(FlyingAbility.class));
    }

    public GruulCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}{G}");


        // Choose one - Creatures without flying can't block this turn;
        this.getSpellAbility().addEffect(new CantBlockAllEffect(filter, Duration.EndOfTurn));

        // or gain control of all permanents you own;
        Mode mode = new Mode();
        mode.getEffects().add(new GainControlAllEffect(Duration.EndOfGame, filter2));
        this.getSpellAbility().addMode(mode);

        // or Gruul Charm deals 3 damage to each creature with flying.
        Mode mode2 = new Mode();
        mode2.getEffects().add(new DamageAllEffect(3, filter3));
        this.getSpellAbility().addMode(mode2);
    }

    public GruulCharm(final GruulCharm card) {
        super(card);
    }

    @Override
    public GruulCharm copy() {
        return new GruulCharm(this);
    }
}
