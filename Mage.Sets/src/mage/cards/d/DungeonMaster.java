package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.token.*;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;

import java.util.Random;
import java.util.UUID;

/**
 *
 * @author jmharmon
 */

public final class DungeonMaster extends CardImpl {

    public DungeonMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER},"{2}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DUNGEON_MASTER);

        Random rnd = new Random();
        int number = 0;
        number = rnd.nextInt(4) + 2;

        if (number == 2) {
            this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(2));
        }
        else if (number == 3) {
            this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(3));
        }
        else if (number == 4) {
            this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(4));
        }
        else if (number == 5){
            this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(5));
        }

        // +1: Target opponent creates a 1/1 black Skeleton creature token with “When this creature dies, each opponent gains 2 life.”
        LoyaltyAbility ability = new LoyaltyAbility(new CreateTokenTargetEffect(new SkeletonToken2()), 1);
        Target target = new TargetOpponent();
        ability.addTarget(target);
        this.addAbility(ability);

        // +1: Roll a d20. If you roll a 1, skip your next turn. If you roll a 12 or higher, draw a card.
        LoyaltyAbility ability2 = new LoyaltyAbility(new DungeonMasterDieEffect(), 1);
        this.addAbility(ability2);

        // −6: You get an adventuring party. (Your party is a 3/3 red Fighter with first strike, a 1/1 white Cleric with lifelink, a 2/2 black Rogue with hexproof, and a 1/1 blue Wizard with flying.)
        LoyaltyAbility ability3 = new LoyaltyAbility(new CreateTokenEffect(new FighterToken()), -6);
        ability3.addEffect(new CreateTokenEffect(new ClericToken()));
        ability3.addEffect(new CreateTokenEffect(new RogueToken()));
        ability3.addEffect(new CreateTokenEffect(new WizardToken()));
        this.addAbility(ability3);
    }

    public DungeonMaster(final DungeonMaster card) {
        super(card);
    }

    @Override
    public DungeonMaster copy() {
        return new DungeonMaster(this);
    }
}

class DungeonMasterDieEffect extends OneShotEffect {

    public DungeonMasterDieEffect() {
        super(Outcome.DrawCard);
        staticText = "Roll a d20. If you roll a 1, skip your next turn. If you roll a 12 or higher, draw a card";
    }

    public DungeonMasterDieEffect(final DungeonMasterDieEffect effect) {
        super(effect);
    }

    @Override
    public DungeonMasterDieEffect copy() {
        return new DungeonMasterDieEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int amount = controller.rollDice(game, 20);
            if (amount == 1) {
                game.getState().getTurnMods().add(new TurnMod(source.getControllerId(), true));
            }
            else if (amount >= 12) {
                controller.drawCards(1, game);
            }
            return true;
        }
        return false;
    }
}
