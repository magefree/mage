
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author lunaskyrise
 */
public final class IsperiaTheInscrutable extends CardImpl {

    public IsperiaTheInscrutable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}{U}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever Isperia the Inscrutable deals combat damage to a player, name a card. That player reveals their hand. If he or she reveals the named card, search your library for a creature card with flying, reveal it, put it into your hand, then shuffle your library.
        Effect effect1 = new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL);
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(effect1, false, true);
        Effect effect2 = new IsperiaTheInscrutableEffect();
        ability.addEffect(effect2);
        this.addAbility(ability);
    }

    public IsperiaTheInscrutable(final IsperiaTheInscrutable card) {
        super(card);
    }

    @Override
    public IsperiaTheInscrutable copy() {
        return new IsperiaTheInscrutable(this);
    }
}

class IsperiaTheInscrutableEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("creature card with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public IsperiaTheInscrutableEffect() {
        super(Outcome.Neutral);
        staticText = "That player reveals their hand. If a card with the chosen name is revealed this way, search your library for a creature card with flying, reveal it, put it into your hand, then shuffle your library";
    }

    public IsperiaTheInscrutableEffect(final IsperiaTheInscrutableEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Object object = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (player != null && object instanceof String) {
            player.revealCards(player.getLogName() + " hand", player.getHand(), game, true);
            String namedCard = (String) object;
            for (Card card : player.getHand().getCards(game)) {
                if (card != null && card.getName().equals(namedCard)) {
                    return new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true, true).apply(game, source);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public IsperiaTheInscrutableEffect copy() {
        return new IsperiaTheInscrutableEffect(this);
    }
}
