package mage.cards.m;

import mage.abilities.Mode;
import mage.abilities.effects.common.continuous.GainControlAllUntapGainHasteEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class MobRule extends CardImpl {

    private static final FilterPermanent filter4orMore = new FilterCreaturePermanent("creatures with power 4 or greater");
    private static final FilterPermanent filter3orLess = new FilterCreaturePermanent("creatures with power 3 or less");

    static {
        filter4orMore.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
        filter3orLess.add(new PowerPredicate(ComparisonType.FEWER_THAN, 4));
    }

    public MobRule(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        // Choose one
        // Gain control of all creatures with power 4 or greater until end of turn. Untap those creatures. They gain haste until end of turn.
        this.getSpellAbility().addEffect(new GainControlAllUntapGainHasteEffect(filter4orMore).withTextOptions("those creatures"));

        // Gain control of all creatures with power 3 or less until end of turn. Untap those creatures. They gain haste until end of turn.
        this.getSpellAbility().addMode(new Mode(new GainControlAllUntapGainHasteEffect(filter3orLess).withTextOptions("those creatures")));
    }

    private MobRule(final MobRule card) {
        super(card);
    }

    @Override
    public MobRule copy() {
        return new MobRule(this);
    }
}
