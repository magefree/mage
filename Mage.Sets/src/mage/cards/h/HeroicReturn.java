package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureAttackingYou;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class HeroicReturn extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterCreatureAttackingYou("a creature is attacking you"), false
    );

    public HeroicReturn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{W}");

        // This spell costs {2} less to cast if a creature is attacking you.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, condition)
        ).setRuleAtTheTop(true));

        // Return target creature card from your graveyard to the battlefield. If a Hero enters this way, it enters with two additional +1/+1 counters on it.
        this.getSpellAbility().addEffect(new HeroicReturnEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    private HeroicReturn(final HeroicReturn card) {
        super(card);
    }

    @Override
    public HeroicReturn copy() {
        return new HeroicReturn(this);
    }
}

class HeroicReturnEffect extends ReturnFromGraveyardToBattlefieldTargetEffect {

    private static final FilterCard filter = new FilterCreatureCard("a Hero");

    static {
        filter.add(SubType.HERO.getPredicate());
    }

    HeroicReturnEffect() {
        super();
    }

    private HeroicReturnEffect(final HeroicReturnEffect effect) {
        super(effect);
    }

    @Override
    public HeroicReturnEffect copy() {
        return new HeroicReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            if (filter.match(game.getCard(targetId), source.getControllerId(), source, game)) {
                game.setEnterWithCounters(targetId, new Counters().addCounter(CounterType.P1P1.createInstance(2)));
            }
        }
        return super.apply(game, source);
    }

    @Override
    public String getText(Mode mode) {
        String text = super.getText(mode);
        return text.concat(". If a Hero enters this way, it enters with two additional +1/+1 counters on it");
    }
}
