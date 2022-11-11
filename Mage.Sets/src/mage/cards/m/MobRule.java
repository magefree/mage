package mage.cards.m;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainControlAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

/**
 *
 * @author awjackson
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
        this.getSpellAbility().addEffect(new GainControlAllEffect(Duration.EndOfTurn, filter4orMore));
        this.getSpellAbility().addEffect(new UntapAllEffect(filter4orMore).setText("untap those creatures"));
        this.getSpellAbility().addEffect(new GainAbilityAllEffect(
                HasteAbility.getInstance(),
                Duration.EndOfTurn,
                filter4orMore,
                "they gain haste until end of turn"
        ));

        // Gain control of all creatures with power 3 or less until end of turn. Untap those creatures. They gain haste until end of turn.
        Mode mode = new Mode(new GainControlAllEffect(Duration.EndOfTurn, filter3orLess));
        mode.addEffect(new UntapAllEffect(filter3orLess).setText("untap those creatures"));
        mode.addEffect(new GainAbilityAllEffect(
                HasteAbility.getInstance(),
                Duration.EndOfTurn,
                filter3orLess,
                "they gain haste until end of turn"
        ));

        this.getSpellAbility().addMode(mode);
    }

    private MobRule(final MobRule card) {
        super(card);
    }

    @Override
    public MobRule copy() {
        return new MobRule(this);
    }
}
