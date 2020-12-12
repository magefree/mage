package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DiscipleOfPhenax extends CardImpl {

    public DiscipleOfPhenax(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Disciple of Phenax enters the battlefield, target player reveals a number of cards
        // from their hand equal to your devotion to black. You choose one of them. That player discards that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscipleOfPhenaxEffect(), false);
        ability.addTarget(new TargetPlayer());
        ability.addHint(DevotionCount.B.getHint());
        this.addAbility(ability);

    }

    private DiscipleOfPhenax(final DiscipleOfPhenax card) {
        super(card);
    }

    @Override
    public DiscipleOfPhenax copy() {
        return new DiscipleOfPhenax(this);
    }
}

class DiscipleOfPhenaxEffect extends OneShotEffect {

    DiscipleOfPhenaxEffect() {
        super(Outcome.Discard);
        staticText = "target player reveals a number of cards from their hand "
                + "equal to your devotion to black. You choose one of "
                + "them. That player discards that card";
    }

    private DiscipleOfPhenaxEffect(final DiscipleOfPhenaxEffect effect) {
        super(effect);
    }

    @Override
    public DiscipleOfPhenaxEffect copy() {
        return new DiscipleOfPhenaxEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int devotion = DevotionCount.B.calculate(game, source, this);
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (devotion <= 0 || targetPlayer == null) {
            return false;
        }
        Cards revealedCards = new CardsImpl();
        int amount = Math.min(targetPlayer.getHand().size(), devotion);
        if (targetPlayer.getHand().size() > amount) {
            FilterCard filter = new FilterCard("card in target player's hand");
            TargetCard chosenCards = new TargetCard(amount, amount, Zone.HAND, filter);
            chosenCards.setNotTarget(true);
            if (chosenCards.canChoose(source.getSourceId(), targetPlayer.getId(), game)
                    && targetPlayer.choose(Outcome.Discard, targetPlayer.getHand(), chosenCards, game)) {
                if (!chosenCards.getTargets().isEmpty()) {
                    List<UUID> targets = chosenCards.getTargets();
                    for (UUID targetid : targets) {
                        Card card = game.getCard(targetid);
                        if (card != null) {
                            revealedCards.add(card);
                        }
                    }
                }
            }
        } else {
            revealedCards.addAll(targetPlayer.getHand());
        }
        if (revealedCards.isEmpty()) {
            return true;
        }
        targetPlayer.revealCards("Disciple of Phenax", revealedCards, game);
        Player you = game.getPlayer(source.getControllerId());
        if (you == null) {
            return false;
        }
        TargetCard yourChoice = new TargetCard(Zone.HAND, new FilterCard());
        yourChoice.setNotTarget(true);
        if (you.choose(Outcome.Benefit, revealedCards, yourChoice, game)) {
            Card card = targetPlayer.getHand().get(yourChoice.getFirstTarget(), game);
            return targetPlayer.discard(card, false, source, game);

        }
        return true;
    }
}
