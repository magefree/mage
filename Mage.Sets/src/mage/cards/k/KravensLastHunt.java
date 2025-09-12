package mage.cards.k;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KravensLastHunt extends CardImpl {

    public KravensLastHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Mill five cards. When you do, this Saga deals damage equal to the greatest power among creature cards in your graveyard to target creature.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new MillCardsControllerEffect(4), new KravensLastHuntEffect()
        );

        // II -- Target creature you control gets +2/+2 until end of turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new BoostTargetEffect(2, 2),
                new TargetControlledCreaturePermanent()
        );

        // III -- Return target creature card from your graveyard to your hand.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, new ReturnFromGraveyardToHandTargetEffect(),
                new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD)
        );
        this.addAbility(sagaAbility.addHint(KravensLastHuntValue.getHint()));
    }

    private KravensLastHunt(final KravensLastHunt card) {
        super(card);
    }

    @Override
    public KravensLastHunt copy() {
        return new KravensLastHunt(this);
    }
}

enum KravensLastHuntValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("Greatest power among creature cards in your graveyard", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player == null) {
            return 0;
        }
        return player
                .getGraveyard()
                .getCards(StaticFilters.FILTER_CARD_CREATURE, game)
                .stream()
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .max()
                .orElse(0);
    }

    @Override
    public KravensLastHuntValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}

class KravensLastHuntEffect extends OneShotEffect {


    KravensLastHuntEffect() {
        super(Outcome.Benefit);
        staticText = "When you do, {this} deals damage equal to the greatest power among creature cards in your graveyard to target creature";
    }

    private KravensLastHuntEffect(final KravensLastHuntEffect effect) {
        super(effect);
    }

    @Override
    public KravensLastHuntEffect copy() {
        return new KravensLastHuntEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(KravensLastHuntValue.instance)
                        .setText("{this} deals damage equal to the greatest power " +
                                "among creature cards in your graveyard to target creature"), false
        );
        ability.addTarget(new TargetCreaturePermanent());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
