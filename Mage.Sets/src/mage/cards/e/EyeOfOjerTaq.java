package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.keyword.CraftAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInGraveyardBattlefieldOrStack;

/**
 *
 * @author jeffwadsworth
 */
public class EyeOfOjerTaq extends CardImpl {

    public EyeOfOjerTaq(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.secondSideCardClazz = mage.cards.a.ApexObservatory.class;

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // Craft with two that share a card type {6} ({6}, Exile this artifact, Exile the two from among other permanents you control 
        // and/or cards from your graveyard: Return this card transformed under its owner’s control. Craft only as a sorcery.)
        this.addAbility(new CraftAbility(
                "{6}", "two that share a card type", new EyeOfOjerTaqTarget()
        ));
    }

    private EyeOfOjerTaq(final EyeOfOjerTaq card) {
        super(card);
    }

    @Override
    public EyeOfOjerTaq copy() {
        return new EyeOfOjerTaq(this);
    }

}

class EyeOfOjerTaqTarget extends TargetCardInGraveyardBattlefieldOrStack {

    private static final FilterCard filterCard
            = new FilterCard();
    private static final FilterControlledPermanent filterPermanent
            = new FilterControlledPermanent();

    static {
        filterCard.add(TargetController.YOU.getOwnerPredicate());
        filterPermanent.add(AnotherPredicate.instance);

    }

    EyeOfOjerTaqTarget() {
        super(2, 2, filterCard, filterPermanent);
    }

    private EyeOfOjerTaqTarget(final EyeOfOjerTaqTarget target) {
        super(target);
    }

    @Override
    public EyeOfOjerTaqTarget copy() {
        return new EyeOfOjerTaqTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card cardObject = game.getCard(id);
        return cardObject != null
                && this.getTargets()
                        .stream()
                        .map(game::getCard)
                        .noneMatch(c -> sharesCardtype(cardObject, c, game));
    }
    
    public static boolean sharesCardtype(Card card1, Card card2, Game game) {
        if (card1.getId().equals(card2.getId())) {
            return false;
        }
        // this should be returned true, but the invert works.
        // if you note the code logic issue, please speak up.
        for (CardType type : card1.getCardType(game)) {
            if (card2.getCardType(game).contains(type)) {
                return false;
            }
        }
        return true;
    }
}
