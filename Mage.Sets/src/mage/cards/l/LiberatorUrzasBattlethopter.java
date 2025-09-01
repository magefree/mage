package mage.cards.l;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.AbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class LiberatorUrzasBattlethopter extends CardImpl {

    private static final FilterCard filter = new FilterCard("colorless spells and artifact spells");

    static {
        filter.add(Predicates.or(
                ColorlessPredicate.instance,
                CardType.ARTIFACT.getPredicate()
        ));
    }

    public LiberatorUrzasBattlethopter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.THOPTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You may cast colorless spells and artifact spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(
                new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter)
        ));

        // Whenever you cast a spell, if the amount of mana spent to cast that spell is greater
        // than Liberator, Urza's Battlethopter's power, put a +1/+1 counter on Liberator.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.SPELL
        ).withInterveningIf(LiberatorUrzasBattlethopterCondition.instance));
    }

    private LiberatorUrzasBattlethopter(final LiberatorUrzasBattlethopter card) {
        super(card);
    }

    @Override
    public LiberatorUrzasBattlethopter copy() {
        return new LiberatorUrzasBattlethopter(this);
    }
}

enum LiberatorUrzasBattlethopterCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return permanent != null
                && CardUtil
                .getEffectValueFromAbility(source, "spellCast", Spell.class)
                .map(Spell::getSpellAbility)
                .map(AbilityImpl::getManaCostsToPay)
                .map(ManaCost::getUsedManaToPay)
                .map(Mana::count)
                .filter(x -> x > permanent.getPower().getValue())
                .isPresent();
    }

    @Override
    public String toString() {
        return "the amount of mana spent to cast that spell is greater than {this}'s power";
    }
}
