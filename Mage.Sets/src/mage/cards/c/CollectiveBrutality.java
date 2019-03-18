package mage.cards.c;

import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.keyword.EscalateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.other.PlayerPredicate;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class CollectiveBrutality extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant or sorcery card");
    private static final FilterPlayer filterDiscard = new FilterPlayer("opponent to discard");
    private static final FilterCreaturePermanent filterCreatureMinus = new FilterCreaturePermanent("creature to get -2/-2");
    private static final FilterPlayer filterLoseLife = new FilterPlayer("opponent to lose life");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
        filterDiscard.add(new PlayerPredicate(TargetController.OPPONENT));
        filterLoseLife.add(new PlayerPredicate(TargetController.OPPONENT));
    }

    public CollectiveBrutality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Escalate - Discard a card.
        Cost cost = new DiscardCardCost();
        cost.setText("&mdash; Discard a card");
        this.addAbility(new EscalateAbility(cost));

        // Choose one or more &mdash;
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(3);

        // Target opponent reveals their hand. You choose an instant or sorcery card from it. That player discards that card.;
        Effect effect = new DiscardCardYouChooseTargetEffect(filter, TargetController.ANY);
        effect.setText("Target opponent reveals their hand. You choose an instant or sorcery card from it. That player discards that card");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPlayer(1, 1, false, filterDiscard).withChooseHint("reveals hand, you choose to discard"));

        // Target creature gets -2/-2 until end of turn.;
        Mode mode = new Mode();
        effect = new BoostTargetEffect(-2, -2, Duration.EndOfTurn);
        effect.setText("Target creature gets -2/-2 until end of turn");
        mode.addEffect(effect);
        mode.addTarget(new TargetCreaturePermanent(filterCreatureMinus).withChooseHint("gets -2/-2 until end of turn"));
        this.getSpellAbility().addMode(mode);

        // Target opponent loses 2 life and you gain 2 life.
        mode = new Mode();
        effect = new LoseLifeTargetEffect(2);
        effect.setText("Target opponent loses 2 life");
        mode.addEffect(effect);
        mode.addTarget(new TargetPlayer(1, 1, false, filterLoseLife).withChooseHint("loses 2 life"));
        effect = new GainLifeEffect(2);
        mode.addEffect(effect.concatBy("and"));
        this.getSpellAbility().addMode(mode);
    }

    public CollectiveBrutality(final CollectiveBrutality card) {
        super(card);
    }

    @Override
    public CollectiveBrutality copy() {
        return new CollectiveBrutality(this);
    }
}
