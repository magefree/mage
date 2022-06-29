package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RelentlessDead extends CardImpl {

    public RelentlessDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // When Relentless Dead dies, you may pay {B}. If you do, return it to its owner's hand.
        this.addAbility(new DiesSourceTriggeredAbility(new DoIfCostPaid(new ReturnToHandSourceEffect().setText("return it to its owner's hand"), new ManaCostsImpl<>("{B}"))));

        // When Relentless Dead dies, you may pay {X}. If you do, return another target Zombie creature card with converted mana cost X from your graveyard to the battlefield.
        this.addAbility(new DiesSourceTriggeredAbility(new RelentlessDeadEffect()));
    }

    private RelentlessDead(final RelentlessDead card) {
        super(card);
    }

    @Override
    public RelentlessDead copy() {
        return new RelentlessDead(this);
    }
}

class RelentlessDeadEffect extends OneShotEffect {

    public RelentlessDeadEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "you may pay {X}. If you do, return another target Zombie creature card with mana value X from your graveyard to the battlefield";
    }

    public RelentlessDeadEffect(final RelentlessDeadEffect effect) {
        super(effect);
    }

    @Override
    public RelentlessDeadEffect copy() {
        return new RelentlessDeadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.chooseUse(Outcome.Benefit, "Do you want to pay {X} to return zombie?", source, game)) {
                int payCount = ManaUtil.playerPaysXGenericMana(true, "Relentless Dead", controller, source, game);
                // can be 0
                FilterCard filter = new FilterCard("Another target Zombie card with mana value {" + payCount + "}");
                filter.add(SubType.ZOMBIE.getPredicate());
                filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, payCount));
                filter.add(AnotherPredicate.instance);
                TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(filter);
                if (controller.chooseTarget(outcome, target, source, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                    }
                }

            }
            return true;
        }
        return false;

    }
}
