package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

import java.util.Set;
import java.util.UUID;
import mage.ApprovingObject;

/**
 * @author LevelX2
 */
public final class JacesMindseeker extends CardImpl {

    public JacesMindseeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.subtype.add(SubType.FISH);
        this.subtype.add(SubType.ILLUSION);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Jace's Mindseeker enters the battlefield, target opponent puts 
        // the top five cards of their library into their graveyard.
        // You may cast an instant or sorcery card from among them without paying its mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new JaceMindseekerEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public JacesMindseeker(final JacesMindseeker card) {
        super(card);
    }

    @Override
    public JacesMindseeker copy() {
        return new JacesMindseeker(this);
    }
}

class JaceMindseekerEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterInstantOrSorceryCard();

    public JaceMindseekerEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "target opponent mills five cards. You may cast an instant or sorcery spell " +
                "from among them without paying its mana cost.";
    }

    public JaceMindseekerEffect(final JaceMindseekerEffect effect) {
        super(effect);
    }

    @Override
    public JaceMindseekerEffect copy() {
        return new JaceMindseekerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cards cardsToCast = new CardsImpl();
        Player targetOpponent = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetOpponent != null) {
            Set<Card> allCards = targetOpponent.millCards(5, source, game).getCards(game);
            for (Card card : allCards) {
                if (filter.match(card, game)) {
                    Zone zone = game.getState().getZone(card.getId());
                    // If the five cards are put into a public zone such as exile instead 
                    // of a graveyard (perhaps due to the ability of Rest in Peace),
                    // you can cast one of those instant or sorcery cards from that zone.
                    if (zone == Zone.GRAVEYARD
                            || zone == Zone.EXILED) {
                        cardsToCast.add(card);
                    }
                }
            }

            // cast an instant or sorcery for free
            if (!cardsToCast.isEmpty()) {
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {
                    TargetCard target = new TargetCard(Zone.GRAVEYARD, filter); // zone should be ignored here
                    target.setNotTarget(true);
                    if (controller.chooseUse(outcome, "Cast an instant or sorcery card from among them for free?", source, game)
                            && controller.choose(Outcome.PlayForFree, cardsToCast, target, game)) {
                        Card card = cardsToCast.get(target.getFirstTarget(), game);
                        if (card != null) {
                            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                            controller.cast(controller.chooseAbilityForCast(card, game, true),
                                    game, true, new ApprovingObject(source, game));
                            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                        }
                    }
                }

            }
            return true;
        }
        return false;
    }
}
