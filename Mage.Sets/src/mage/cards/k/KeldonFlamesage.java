package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EnlistAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KeldonFlamesage extends CardImpl {

    public KeldonFlamesage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Enlist
        this.addAbility(new EnlistAbility());

        // Whenever Keldon Flamesage attacks, look at the top X cards of your library, where X is Keldon Flamesage's power. You may exile an instant or sorcery card with mana value X or less from among them. Put the rest on the bottom of your library in a random order. You may cast the exiled card without paying its mana cost.
        this.addAbility(new AttacksTriggeredAbility(new KeldonFlamesageEffect()));
    }

    private KeldonFlamesage(final KeldonFlamesage card) {
        super(card);
    }

    @Override
    public KeldonFlamesage copy() {
        return new KeldonFlamesage(this);
    }
}

class KeldonFlamesageEffect extends OneShotEffect {

    KeldonFlamesageEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top X cards of your library, where X is {this}'s power. " +
                "You may exile an instant or sorcery card with mana value X or less from among them. " +
                "Put the rest on the bottom of your library in a random order. " +
                "You may cast the exiled card without paying its mana cost";
    }

    private KeldonFlamesageEffect(final KeldonFlamesageEffect effect) {
        super(effect);
    }

    @Override
    public KeldonFlamesageEffect copy() {
        return new KeldonFlamesageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (player == null || permanent == null) {
            return false;
        }
        int power = permanent.getPower().getValue();
        if (power < 1) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, power));
        FilterCard filter = new FilterInstantOrSorceryCard(
                "instant or sorcery card with mana value " + power + " or less"
        );
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, power + 1));
        TargetCard target = new TargetCardInLibrary(0, 1, filter);
        player.choose(outcome, target, source, game);
        Card card = cards.get(target.getFirstTarget(), game);
        if (card != null) {
            player.moveCards(card, Zone.EXILED, source, game);
        }
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(card, game, source, false);
        CardUtil.castSpellWithAttributesForFree(player, source, game, card);
        return true;
    }
}
