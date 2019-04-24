
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterOpponent;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.AnotherTargetPredicate;
import mage.target.Target;
import mage.target.common.TargetOpponent;

/**
 *
 * @author anonymous
 */
public final class VindictiveLich extends CardImpl {

    public VindictiveLich(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ZOMBIE, SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // When Vindictive Lich dies, choose one or more. Each mode must target a different player.
        // *Target opponent sacrifices a creature.
        Ability ability = new DiesTriggeredAbility(new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "target opponent"));
        ability.getModes().setMinModes(1);
        ability.getModes().setMaxModes(3);
        ability.getModes().setEachModeOnlyOnce(true);
        ability.getModes().setMaxModesFilter(new FilterOpponent("a different player"));

        FilterOpponent filter = new FilterOpponent("opponent (sacrifice)");
        filter.add(new AnotherTargetPredicate(1, true));
        Target target = new TargetOpponent(filter, false);
        target.setTargetTag(1);
        ability.addTarget(target);

        // *Target opponent discards two cards.
        Mode mode = new Mode();
        mode.getEffects().add(new DiscardTargetEffect(2, false));
        filter = new FilterOpponent("opponent (discard)");
        filter.add(new AnotherTargetPredicate(2, true));
        target = new TargetOpponent(filter, false);
        target.setTargetTag(2);
        mode.getTargets().add(target);
        ability.addMode(mode);

        // *Target opponent loses 5 life.
        mode = new Mode();
        mode.getEffects().add(new LoseLifeTargetEffect(5));
        filter = new FilterOpponent("opponent (life loss)");
        filter.add(new AnotherTargetPredicate(3, true));
        target = new TargetOpponent(filter, false);
        target.setTargetTag(3);
        mode.getTargets().add(target);
        ability.addMode(mode);
        this.addAbility(ability);
    }

    public VindictiveLich(final VindictiveLich card) {
        super(card);
    }

    @Override
    public VindictiveLich copy() {
        return new VindictiveLich(this);
    }

}
