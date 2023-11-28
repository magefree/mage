package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackingBlockingDelayedWatcher;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class ThijarianWitness extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("another creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(WasAttackBlockAlonePredicate.instance);
    }

    public ThijarianWitness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Bear Witness -- Whenever another creature dies, if it was attacking or blocking alone, exile it and investigate.
        DiesCreatureTriggeredAbility ability = new DiesCreatureTriggeredAbility(
                new ExileTargetEffect().setText("if it was attacking or blocking alone, exile it"),
                false, filter, true
        );
        ability.addEffect(new InvestigateEffect().concatBy("and"));
        ability.withFlavorWord("Bear Witness");
        ability.addWatcher(new AttackingBlockingDelayedWatcher());

        this.addAbility(ability);
    }

    private ThijarianWitness(final ThijarianWitness card) {
        super(card);
    }

    @Override
    public ThijarianWitness copy() {
        return new ThijarianWitness(this);
    }
}
enum WasAttackBlockAlonePredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        AttackingBlockingDelayedWatcher watcher = AttackingBlockingDelayedWatcher.getWatcher(game);
        if (watcher == null) {
            return false;
        }
        return (watcher.checkAttacker(input.getId()) && watcher.countAttackers() == 1)
                || (watcher.checkBlocker(input.getId()) && watcher.countBlockers() == 1);
    }

    @Override
    public String toString() {
        return "attacking or blocking alone";
    }
}
