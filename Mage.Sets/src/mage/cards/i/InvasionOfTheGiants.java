package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponentOrPlaneswalker;
import mage.util.CardUtil;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfTheGiants extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Giant card from your hand");

    static {
        filter.add(SubType.GIANT.getPredicate());
    }

    public InvasionOfTheGiants(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_III);

        // I — Scry 2.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new ScryEffect(2));

        // II — Draw a card. Then you may reveal a Giant card from your hand. When you do, Invasion of the Giants deals 2 damage to target opponent or planeswalker.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(2), false,
                "{this} deals 2 damage to target opponent or planeswalker"
        );
        ability.addTarget(new TargetOpponentOrPlaneswalker());
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new DrawCardSourceControllerEffect(1),
                new DoWhenCostPaid(
                        ability,
                        new RevealTargetFromHandCost(new TargetCardInHand(filter)),
                        "Reveal a Giant card from your hand?"
                ).concatBy("Then")
        );

        // III — The next Giant spell you cast this turns costs {2} less to cast.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new InvasionOfTheGiantsEffect());

        this.addAbility(sagaAbility);
    }

    private InvasionOfTheGiants(final InvasionOfTheGiants card) {
        super(card);
    }

    @Override
    public InvasionOfTheGiants copy() {
        return new InvasionOfTheGiants(this);
    }
}

class InvasionOfTheGiantsEffect extends CostModificationEffectImpl {

    private int spellsCast;

    InvasionOfTheGiantsEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "the next Giant spell you cast this turn costs {2} less to cast";
    }

    private InvasionOfTheGiantsEffect(final InvasionOfTheGiantsEffect effect) {
        super(effect);
        this.spellsCast = effect.spellsCast;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if (watcher != null) {
            spellsCast = watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(source.getControllerId());
        }
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if (watcher == null) {
            return false;
        }
        if (watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(source.getControllerId()) > spellsCast) {
            discard(); // only one use
            return false;
        }
        if (!(abilityToModify instanceof SpellAbility)
                || !abilityToModify.isControlledBy(source.getControllerId())) {
            return false;
        }
        Card spellCard = ((SpellAbility) abilityToModify).getCharacteristics(game);
        return spellCard != null && spellCard.hasSubtype(SubType.GIANT, game);
    }

    @Override
    public InvasionOfTheGiantsEffect copy() {
        return new InvasionOfTheGiantsEffect(this);
    }
}
