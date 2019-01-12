
package mage.cards.g;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetadjustment.TargetAdjuster;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GrimReturn extends CardImpl {

    private static final String textFilter = "creature card in a graveyard that was put there from the battlefield this turn";

    public GrimReturn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Choose target creature card in a graveyard that was put there from the battlefield this turn. Put that card onto the battlefield under your control.
        Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
        effect.setText("Choose target creature card in a graveyard that was put there from the battlefield this turn. Put that card onto the battlefield under your control");
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().setTargetAdjuster(GrimReturnAdjuster.instance);
        this.getSpellAbility().addWatcher(new CardsPutIntoGraveyardWatcher());
    }

    public GrimReturn(final GrimReturn card) {
        super(card);
    }

    @Override
    public GrimReturn copy() {
        return new GrimReturn(this);
    }
}

enum GrimReturnAdjuster implements TargetAdjuster {
    instance;
    private static final String textFilter = "creature card in a graveyard that was put there from the battlefield this turn";

    @Override
    public void adjustTargets(Ability ability, Game game) {
        CardsPutIntoGraveyardWatcher watcher = game.getState().getWatcher(CardsPutIntoGraveyardWatcher.class);
        if (watcher == null) {
            return;
        }
        FilterCard filter = new FilterCreatureCard(textFilter);
        List<CardIdPredicate> uuidPredicates = new ArrayList<>();
        for (MageObjectReference mor : watcher.getCardsPutToGraveyardFromBattlefield()) {
            if (mor.zoneCounterIsCurrent(game)) {
                uuidPredicates.add(new CardIdPredicate(mor.getSourceId()));
            }
        }
        filter.add(Predicates.or(uuidPredicates));
        ability.getTargets().clear();
        ability.addTarget(new TargetCardInGraveyard(filter));
    }
}