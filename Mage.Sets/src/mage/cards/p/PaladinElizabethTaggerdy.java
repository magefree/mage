package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.BattalionAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.ManaValueLessThanOrEqualToSourcePowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class PaladinElizabethTaggerdy extends CardImpl {
    public PaladinElizabethTaggerdy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Battalion -- Whenever Paladin Elizabeth Taggerdy and at least two other creatures attack, draw a card, then you may put a creature card with mana value X or less from your hand onto the battlefield tapped and attacking, where X is Paladin Elizabeth Taggerdy's power.
        Ability ability = new BattalionAbility(new DrawCardSourceControllerEffect(1));
        ability.addEffect(new PaladinElizabethTaggerdyEffect().concatBy(", then"));
        this.addAbility(ability);

    }

    private PaladinElizabethTaggerdy(final PaladinElizabethTaggerdy card) {
        super(card);
    }

    @Override
    public PaladinElizabethTaggerdy copy() {
        return new PaladinElizabethTaggerdy(this);
    }
}

//Based on Preeminent Captain
class PaladinElizabethTaggerdyEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCreatureCard("a creature card with mana value less than or equal to {this}'s power");

    static {
        filter.add(ManaValueLessThanOrEqualToSourcePowerPredicate.instance);
    }

    public PaladinElizabethTaggerdyEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "you may put a creature card with mana value X or less from your hand onto the battlefield tapped and attacking, where X is {this}'s power";
    }

    private PaladinElizabethTaggerdyEffect(final PaladinElizabethTaggerdyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        TargetCardInHand target = new TargetCardInHand(filter);
        if (controller != null && target.canChoose(controller.getId(), source, game)
                && target.choose(outcome, controller.getId(), source.getSourceId(), source, game)) {
            if (!target.getTargets().isEmpty()) {
                UUID cardId = target.getFirstTarget();
                Card card = controller.getHand().get(cardId, game);
                if (card != null) {
                    if (controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, true, null)) {
                        Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
                        if (permanent != null) {
                            game.getCombat().addAttackingCreature(permanent.getId(), game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public PaladinElizabethTaggerdyEffect copy() {
        return new PaladinElizabethTaggerdyEffect(this);
    }

}
