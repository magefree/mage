package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.common.PermanentsYouControlHint;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.*;
import mage.cards.g.GishathSunsAvatar;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardAndOrCardInLibrary;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class OjerKaslemDeepestGrowth extends TransformingDoubleFacedCard {

    public OjerKaslemDeepestGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GOD}, "{3}{G}{G}",
                "Temple of Cultivation",
                new SuperType[]{}, new CardType[]{CardType.LAND}, new SubType[]{}, "");

        // Ojer Kaslem, Deepest Growth
        this.getLeftHalfCard().setPT(6, 5);

        // Trample
        this.getLeftHalfCard().addAbility(TrampleAbility.getInstance());

        // Whenever Ojer Kaslem deals combat damage to a player, reveal that many cards from the top of your library. You may put a creature card and/or a land card from among them onto the battlefield. Put the rest on the bottom in a random order.
        this.getLeftHalfCard().addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new OjerKaslemDeepestGrowthEffect(), false, true));

        // When Ojer Kaslem dies, return it to the battlefield tapped and transformed under its owner's control.
        this.getLeftHalfCard().addAbility(new DiesSourceTriggeredAbility(new OjerKaslemDeepestGrowthTransformEffect()));

        // Temple of Cultivation
        // {T}: Add {G}.
        this.getRightHalfCard().addAbility(new GreenManaAbility());

        // {2}{G}, {T}: Transform Temple of Cultivation. Activate only if you control ten or more permanents and only as a sorcery.
        Condition condition = new PermanentsOnTheBattlefieldCondition(
                new FilterControlledPermanent("you control ten or more permanents"), ComparisonType.MORE_THAN, 9
        );
        Ability ability = new ActivateIfConditionActivatedAbility(
                new TransformSourceEffect(), new ManaCostsImpl<>("{2}{G}"), condition
        ).setTiming(TimingRule.SORCERY);
        ability.addCost(new TapSourceCost());
        this.getRightHalfCard().addAbility(ability.addHint(PermanentsYouControlHint.instance));
    }

    private OjerKaslemDeepestGrowth(final OjerKaslemDeepestGrowth card) {
        super(card);
    }

    @Override
    public OjerKaslemDeepestGrowth copy() {
        return new OjerKaslemDeepestGrowth(this);
    }
}

class OjerKaslemDeepestGrowthTransformEffect extends OneShotEffect {

    OjerKaslemDeepestGrowthTransformEffect() {
        super(Outcome.Benefit);
        staticText = "return it to the battlefield tapped and transformed under its owner's control";
    }

    private OjerKaslemDeepestGrowthTransformEffect(final OjerKaslemDeepestGrowthTransformEffect effect) {
        super(effect);
    }

    @Override
    public OjerKaslemDeepestGrowthTransformEffect copy() {
        return new OjerKaslemDeepestGrowthTransformEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = source.getSourceCardIfItStillExists(game);
        if (controller == null || card == null) {
            return false;
        }
        game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
        controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, true, null);
        return true;
    }
}

/**
 * Inspired by {@link GishathSunsAvatar}
 */
class OjerKaslemDeepestGrowthEffect extends OneShotEffect {

    OjerKaslemDeepestGrowthEffect() {
        super(Outcome.Benefit);
        this.staticText = "reveal that many cards from the top of your library. "
                + "You may put a creature card and/or a land card from among them onto the battlefield. "
                + "Put the rest on the bottom in a random order";
    }

    private OjerKaslemDeepestGrowthEffect(final OjerKaslemDeepestGrowthEffect effect) {
        super(effect);
    }

    @Override
    public OjerKaslemDeepestGrowthEffect copy() {
        return new OjerKaslemDeepestGrowthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int xValue = (Integer) getValue("damage");
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, xValue));
        if (!cards.isEmpty()) {
            controller.revealCards(source, cards, game);
            TargetCard target = new TargetCardAndOrCardInLibrary(CardType.CREATURE, CardType.LAND);
            controller.choose(Outcome.PutCardInPlay, cards, target, source, game);
            Cards toBattlefield = new CardsImpl(target.getTargets());
            cards.removeAll(toBattlefield);
            controller.moveCards(toBattlefield.getCards(game), Zone.BATTLEFIELD, source, game);
            controller.putCardsOnBottomOfLibrary(cards, game, source, false);
        }
        return true;
    }
}
