package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LullmagesDomination extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(
            "a creature whose controller has eight or more cards in their graveyard"
    );

    static {
        filter.add(LullmagesDominationPredicate.instance);
    }

    private static final Condition condition = new SourceTargetsPermanentCondition(filter);

    public LullmagesDomination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}{U}");

        // This spell costs {3} less to cast if it targets a creature whose controller has eight or more cards in their graveyard.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(3, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Gain control of target creature with converted mana cost X.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.Custom)
                .setText("gain control of target creature with converted mana cost X"));
        this.getSpellAbility().setTargetAdjuster(LullmagesDominationAdjuster.instance);
    }

    private LullmagesDomination(final LullmagesDomination card) {
        super(card);
    }

    @Override
    public LullmagesDomination copy() {
        return new LullmagesDomination(this);
    }
}

enum LullmagesDominationPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        Player player = game.getPlayer(input.getControllerId());
        return player != null && player.getGraveyard().size() >= 8;
    }
}

enum LullmagesDominationAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with converted mana cost " + xValue);
        filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, xValue));
        ability.addTarget(new TargetCreaturePermanent(filter));
    }
}
