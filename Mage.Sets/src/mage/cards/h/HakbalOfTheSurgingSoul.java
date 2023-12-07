package mage.cards.h;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetControlledCreaturePermanent;

import static mage.abilities.effects.keyword.ExploreSourceEffect.explorePermanent;

/**
 *
 * @author Grath
 */
public final class HakbalOfTheSurgingSoul extends CardImpl {

    public HakbalOfTheSurgingSoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, each Merfolk creature you control explores.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new HakbalOfTheSurgingSoulExploreEffect(), TargetController.YOU, false));

        // Whenever Hakbal of the Surging Soul attacks, you may put a land card from your hand onto the battlefield. If you don't, draw a card.
        this.addAbility(new AttacksTriggeredAbility(new HakbalOfTheSurgingSoulEffect(), false));
    }

    private HakbalOfTheSurgingSoul(final HakbalOfTheSurgingSoul card) {
        super(card);
    }

    @Override
    public HakbalOfTheSurgingSoul copy() {
        return new HakbalOfTheSurgingSoul(this);
    }
}

class HakbalOfTheSurgingSoulExploreEffect extends OneShotEffect {

    HakbalOfTheSurgingSoulExploreEffect( ) {
        super(Outcome.Untap);
    }

    HakbalOfTheSurgingSoulExploreEffect(HakbalOfTheSurgingSoulExploreEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(SubType.MERFOLK);

        List<Permanent> creatures = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game);

        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.chooseUse(Outcome.AIDontUseIt, "Choose order for Merfolk to explore? (Note: You will need to set \"Auto-choose targets for player:\" to \"Off\" in Preferences.)", source, game)) {
            TargetPermanent target = new TargetControlledCreaturePermanent(creatures.size(), creatures.size(), filter, true);
            target.withChooseHint("the order in which to explore (first selected will explore first)");
            player.choose(outcome, target, source, game);
            for (UUID targetId : target.getTargets()) {
                explorePermanent(game, targetId, source, 1);
            }
        } else {
            for (Permanent creature : creatures) {
                explorePermanent(game, creature.getId(), source, 1);
            }
        }

        return true;
    }

    @Override
    public HakbalOfTheSurgingSoulExploreEffect copy() {
        return new HakbalOfTheSurgingSoulExploreEffect(this);
    }

}

class HakbalOfTheSurgingSoulEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("a land card");

    static {
        filter.add(CardType.LAND.getPredicate());
    }

    public HakbalOfTheSurgingSoulEffect() {
        super(Outcome.DrawCard);
        staticText = "you may put a land card from your hand onto the battlefield. If you don’t, draw a card";
    }

    private HakbalOfTheSurgingSoulEffect(final HakbalOfTheSurgingSoulEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (player.chooseUse(Outcome.PutCardInPlay, "Put " + filter.getMessage() + " from your hand onto the battlefield?", source, game)) {
                TargetCardInHand target = new TargetCardInHand(filter);
                if (player.choose(Outcome.PutCardInPlay, target, source, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        return player.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, false, null);
                    }
                }
            } else {
                player.drawCards(1, source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public HakbalOfTheSurgingSoulEffect copy() {
        return new HakbalOfTheSurgingSoulEffect(this);
    }
}