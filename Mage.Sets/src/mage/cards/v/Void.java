package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Void extends CardImpl {

    public Void(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{R}");

        // Choose a number. Destroy all artifacts and creatures with converted mana cost equal to that number. Then target player reveals their hand and discards all nonland cards with converted mana cost equal to the number.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new VoidEffect());
    }

    public Void(final Void card) {
        super(card);
    }

    @Override
    public Void copy() {
        return new Void(this);
    }
}

class VoidEffect extends OneShotEffect {

    VoidEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Choose a number. Destroy all artifacts and creatures with converted mana cost equal to that number. Then target player reveals their hand and discards all nonland cards with converted mana cost equal to the number";
    }

    private VoidEffect(final VoidEffect effect) {
        super(effect);
    }

    @Override
    public VoidEffect copy() {
        return new VoidEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Choice numberChoice = new ChoiceImpl();
        Set<String> numbers = new HashSet<>(16);
        for (int i = 0; i <= 15; i++) {
            numbers.add(Integer.toString(i));
        }
        numberChoice.setChoices(numbers);
        numberChoice.setMessage("Choose a number");
        if (!controller.choose(Outcome.DestroyPermanent, numberChoice, game)) {
            return false;
        }
        int number = Integer.parseInt(numberChoice.getChoice());
        for (Permanent permanent : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
            if ((permanent.isArtifact() || permanent.isCreature())
                    && permanent.getConvertedManaCost() == number) {
                permanent.destroy(source.getSourceId(), game, false);
            }
        }
        FilterCard filterCard = new FilterCard();
        filterCard.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, number));
        filterCard.add(Predicates.not(CardType.LAND.getPredicate()));

        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer == null) {
            return true;
        }
        targetPlayer.revealCards(source, targetPlayer.getHand(), game);
        targetPlayer.discard(new CardsImpl(targetPlayer.getHand().getCards(filterCard, game)), source, game);
        return true;
    }
}
