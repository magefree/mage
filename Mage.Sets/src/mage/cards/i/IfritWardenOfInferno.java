package mage.cards.i;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.SagaAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IfritWardenOfInferno extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("other target creature");
    private static final Condition condition = new SourceHasCounterCondition(CounterType.LORE, 3);

    public IfritWardenOfInferno(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);
        this.nightCard = true;
        this.color.setRed(true);

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Lunge -- Ifrit fights up to one other target creature.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, ability -> {
            ability.addEffect(new FightTargetSourceEffect());
            ability.addTarget(new TargetPermanent(0, 1, filter));
            ability.withFlavorWord("Lunge");
        });

        // II, III -- Brimstone -- Add {R}{R}{R}{R}. If Ifrit has three or more lore counters on it, exile it, then return it to the battlefield
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III, ability -> {
            ability.addEffect(new BasicManaEffect(Mana.RedMana(4)));
            ability.addEffect(new ConditionalOneShotEffect(
                    new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD), condition,
                    "If {this} has three or more lore counters on it, exile it, " +
                            "then return it to the battlefield <i>(front face up.)</i>."
            ));
            ability.withFlavorWord("Brimstone");
        });
        this.addAbility(sagaAbility);
    }

    private IfritWardenOfInferno(final IfritWardenOfInferno card) {
        super(card);
    }

    @Override
    public IfritWardenOfInferno copy() {
        return new IfritWardenOfInferno(this);
    }
}
