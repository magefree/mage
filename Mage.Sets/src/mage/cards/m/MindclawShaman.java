
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public final class MindclawShaman extends CardImpl {

    public MindclawShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Mindclaw Shaman enters the battlefield, target opponent reveals their hand. You may cast an instant or sorcery card from it without paying its mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MindclawShamanEffect(), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public MindclawShaman(final MindclawShaman card) {
        super(card);
    }

    @Override
    public MindclawShaman copy() {
        return new MindclawShaman(this);
    }
}

class MindclawShamanEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("instant or sorcery card");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public MindclawShamanEffect() {
        super(Outcome.Discard);
        this.staticText = "target opponent reveals their hand. You may cast an instant or sorcery card from it without paying its mana cost";
    }

    public MindclawShamanEffect(final MindclawShamanEffect effect) {
        super(effect);
    }

    @Override
    public MindclawShamanEffect copy() {
        return new MindclawShamanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        MageObject sourceObject = source.getSourceObject(game);
        if (targetOpponent != null && sourceObject != null) {
            if (!targetOpponent.getHand().isEmpty()) {
                targetOpponent.revealCards(sourceObject.getName(), targetOpponent.getHand(), game);
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {
                    TargetCard target = new TargetCard(Zone.HAND, filter);
                    target.setNotTarget(true);
                    if (controller.choose(Outcome.Benefit, targetOpponent.getHand(), target, source, game)) {
                        Card chosenCard = targetOpponent.getHand().get(target.getFirstTarget(), game);
                        if (chosenCard != null) {
                            if (controller.chooseUse(Outcome.Benefit, "Cast the chosen card?", source, game)) {
                                controller.cast(chosenCard.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game));
                            } else {
                                game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " canceled casting the card.");
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
