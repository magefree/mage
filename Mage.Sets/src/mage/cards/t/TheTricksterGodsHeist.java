package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheTricksterGodsHeist extends CardImpl {

    public TheTricksterGodsHeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{B}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — You may exchange control of two target creatures.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
                new ExchangeControlTargetEffect(
                        Duration.EndOfGame, "exchange control of two target creatures"
                ), new TargetCreaturePermanent(2), true
        );

        // II — You may exchange control of two target nonbasic, noncreature permanents that share a card type.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new ExchangeControlTargetEffect(
                        Duration.EndOfGame, "exchange control of two target nonbasic, " +
                        "noncreature permanents that share a card type"
                ), new TheTricksterGodsHeistTarget(), true
        );

        // III — Target player loses 3 life and you gain 3 life.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                new Effects(
                        new LoseLifeTargetEffect(3),
                        new GainLifeEffect(3).concatBy("and")
                ), new TargetPlayer()
        );

        this.addAbility(sagaAbility);
    }

    private TheTricksterGodsHeist(final TheTricksterGodsHeist card) {
        super(card);
    }

    @Override
    public TheTricksterGodsHeist copy() {
        return new TheTricksterGodsHeist(this);
    }
}

class TheTricksterGodsHeistTarget extends TargetPermanent {
    private static final FilterPermanent filter
            = new FilterPermanent("nonbasic, noncreature permanents that share a card type");

    static {
        filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    TheTricksterGodsHeistTarget() {
        super(2, 2, filter, false);
    }

    private TheTricksterGodsHeistTarget(final TheTricksterGodsHeistTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(controllerId, id, source, game)) {
            return false;
        }
        if (getTargets().isEmpty()) {
            return true;
        }
        Permanent targetOne = game.getPermanent(getTargets().get(0));
        Permanent targetTwo = game.getPermanent(id);
        if (targetOne == null || targetTwo == null) {
            return false;
        }
        return targetOne.shareTypes(targetTwo, game);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        Set<CardType> cardTypes = new HashSet<>();
        MageObject targetSource = game.getObject(source);
        if (targetSource == null) {
            return false;
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game)) {
            if (!permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                continue;
            }
            for (CardType cardType : permanent.getCardType(game)) {
                if (cardTypes.contains(cardType)) {
                    return true;
                }
            }
            cardTypes.addAll(permanent.getCardType(game));
        }
        return false;
    }

    @Override
    public TheTricksterGodsHeistTarget copy() {
        return new TheTricksterGodsHeistTarget(this);
    }
}
