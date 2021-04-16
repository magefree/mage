package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.TargetRemainsInZoneCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.common.cost.SpellsCostIncreasingSpecificCardUnderConditionEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class EliteSpellbinder extends CardImpl {

    public EliteSpellbinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Elite Spellbinder enters the battlefield, look at target opponent's hand. You may exile a nonland card from it. For as long as that card remains exiled, its owner may play it. A spell cast this way costs {2} more to cast.
        Ability ability = new EntersBattlefieldTriggeredAbility(new EliteSpellbinderEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private EliteSpellbinder(final EliteSpellbinder card) {
        super(card);
    }

    @Override
    public EliteSpellbinder copy() {
        return new EliteSpellbinder(this);
    }
}

class EliteSpellbinderEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterNonlandCard();

    static {
        filter.add(TargetController.OPPONENT.getOwnerPredicate());
    }

    EliteSpellbinderEffect() {
        super(Outcome.Benefit);
        staticText = "look at target opponent's hand. You may exile a nonland card from it. For as long as that card remains exiled, its owner may play it. A spell cast this way costs {2} more to cast";
    }

    EliteSpellbinderEffect(EliteSpellbinderEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (opponent != null) {
            controller.lookAtCards(source.getSourceObject(game).getName(), opponent.getHand(), game);
            boolean hasNonLandCard = opponent.getHand().stream()
                .anyMatch(card -> !game.getCard(card).isLand());
            if (hasNonLandCard && controller.chooseUse(outcome, "Exile a nonland card from " + opponent.getName() + "'s hand?", source, game)) {
                TargetCard targetCard = new TargetCardInHand(filter);
                targetCard.setNotTarget(true);
                if (controller.choose(outcome, opponent.getHand(), targetCard, game)) {
                    Card card = game.getCard(targetCard.getFirstTarget());
                    if (card != null) {
                        PlayFromNotOwnHandZoneTargetEffect.exileAndPlayFromExile(game, source, card, TargetController.OWNER, Duration.EndOfGame, false, true);
                        Condition condition = new TargetRemainsInZoneCondition(Zone.EXILED, card.getId());
                        game.addEffect(new SpellsCostIncreasingSpecificCardUnderConditionEffect(2, card.getId(), opponent.getId(), condition), source);
                        return true;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public EliteSpellbinderEffect copy() {
        return new EliteSpellbinderEffect(this);
    }
}