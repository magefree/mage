package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterOpponent;
import mage.filter.FilterPlayer;
import mage.filter.StaticFilters;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class VindictiveLich extends CardImpl {

    private static final FilterPlayer filter0 = new FilterPlayer("a different player");
    private static final FilterPlayer filter1 = new FilterOpponent();
    private static final FilterPlayer filter2 = new FilterOpponent();
    private static final FilterPlayer filter3 = new FilterOpponent();

    static {
        filter1.add(new AnotherTargetPredicate(1, true));
        filter2.add(new AnotherTargetPredicate(2, true));
        filter3.add(new AnotherTargetPredicate(3, true));
    }

    public VindictiveLich(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ZOMBIE, SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // When Vindictive Lich dies, choose one or more. Each mode must target a different player.

        // * Target opponent sacrifices a creature.
        Ability ability = new DiesSourceTriggeredAbility(new SacrificeEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE, 1, "target opponent"
        ));
        ability.getModes().setMinModes(1);
        ability.getModes().setMaxModes(3);
        ability.getModes().setEachModeOnlyOnce(true);
        ability.getModes().setMaxModesFilter(filter0);
        ability.addTarget(new TargetPlayer(filter1).setTargetTag(1).withChooseHint("to sacrifice a creature"));

        // * Target opponent discards two cards.
        ability.addMode(new Mode(new DiscardTargetEffect(2, false))
                .addTarget(new TargetPlayer(filter2).setTargetTag(2).withChooseHint("to discard a card")));

        // * Target opponent loses 5 life.
        ability.addMode(new Mode(new LoseLifeTargetEffect(5))
                .addTarget(new TargetPlayer(filter3).setTargetTag(3).withChooseHint("to lose 5 life")));
        this.addAbility(ability);
    }

    private VindictiveLich(final VindictiveLich card) {
        super(card);
    }

    @Override
    public VindictiveLich copy() {
        return new VindictiveLich(this);
    }
}
