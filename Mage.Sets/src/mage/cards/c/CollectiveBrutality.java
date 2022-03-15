package mage.cards.c;

import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardCardCost;
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
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class CollectiveBrutality extends CardImpl {

    private static final FilterCard filter = new FilterCard("an instant or sorcery card");

    static {
        filter.add(Predicates.or(CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
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

        // Target opponent reveals their hand. You choose an instant or sorcery card from it. That player discards that card.
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(filter, TargetController.OPPONENT));
        this.getSpellAbility().addTarget(new TargetOpponent().withChooseHint("reveals hand, you choose to discard"));

        // Target creature gets -2/-2 until end of turn.
        Mode mode = new Mode(new BoostTargetEffect(-2, -2, Duration.EndOfTurn));
        mode.addTarget(new TargetCreaturePermanent().withChooseHint("gets -2/-2 until end of turn"));
        this.getSpellAbility().addMode(mode);

        // Target opponent loses 2 life and you gain 2 life.
        mode = new Mode(new LoseLifeTargetEffect(2));
        mode.addEffect(new GainLifeEffect(2).concatBy("and"));
        mode.addTarget(new TargetOpponent().withChooseHint("loses 2 life"));
        this.getSpellAbility().addMode(mode);
    }

    private CollectiveBrutality(final CollectiveBrutality card) {
        super(card);
    }

    @Override
    public CollectiveBrutality copy() {
        return new CollectiveBrutality(this);
    }
}
