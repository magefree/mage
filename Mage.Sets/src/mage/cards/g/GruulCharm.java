package mage.cards.g;

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
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class GruulCharm extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures without flying");
    private static final FilterPermanent filter2 = new FilterPermanent("permanents you own");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
        filter2.add(TargetController.YOU.getOwnerPredicate());
    }

    public GruulCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{G}");

        // Choose one - Creatures without flying can't block this turn;
        this.getSpellAbility().addEffect(new CantBlockAllEffect(filter, Duration.EndOfTurn));

        // or gain control of all permanents you own;
        this.getSpellAbility().addMode(new Mode(new GainControlAllEffect(Duration.Custom, filter2)));

        // or Gruul Charm deals 3 damage to each creature with flying.
        this.getSpellAbility().addMode(new Mode(new DamageAllEffect(3, StaticFilters.FILTER_CREATURE_FLYING)));
    }

    private GruulCharm(final GruulCharm card) {
        super(card);
    }

    @Override
    public GruulCharm copy() {
        return new GruulCharm(this);
    }
}
