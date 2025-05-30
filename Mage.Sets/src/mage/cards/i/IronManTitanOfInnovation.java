package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetSacrifice;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IronManTitanOfInnovation extends CardImpl {

    public IronManTitanOfInnovation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Genius Industrialist -- Whenever Iron Man attacks, create a Treasure token, then you may sacrifice a noncreature artifact. If you do, search your library for an artifact card with mana value equal to 1 plus the sacrificed artifact's mana value, put it onto the battlefield tapped, then shuffle.
        Ability ability = new AttacksTriggeredAbility(new CreateTokenEffect(new TreasureToken()));
        ability.addEffect(new IronManTitanOfInnovationEffect());
        this.addAbility(ability.withFlavorWord("Genius Industrialist"));
    }

    private IronManTitanOfInnovation(final IronManTitanOfInnovation card) {
        super(card);
    }

    @Override
    public IronManTitanOfInnovation copy() {
        return new IronManTitanOfInnovation(this);
    }
}

class IronManTitanOfInnovationEffect extends OneShotEffect {

    IronManTitanOfInnovationEffect() {
        super(Outcome.Benefit);
        staticText = ", then you may sacrifice a noncreature artifact. If you do, search your library " +
                "for an artifact card with mana value equal to 1 plus the sacrificed artifact's mana value, " +
                "put it onto the battlefield tapped, then shuffle";
    }

    private IronManTitanOfInnovationEffect(final IronManTitanOfInnovationEffect effect) {
        super(effect);
    }

    @Override
    public IronManTitanOfInnovationEffect copy() {
        return new IronManTitanOfInnovationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetSacrifice(0, 1, StaticFilters.FILTER_ARTIFACT_NON_CREATURE);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null || !permanent.sacrifice(source, game)) {
            return false;
        }
        int mv = permanent.getManaValue() + 1;
        FilterCard filter = new FilterArtifactCard("artifact card with mana value " + mv);
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, mv));
        TargetCardInLibrary targetCardInLibrary = new TargetCardInLibrary(filter);
        player.searchLibrary(targetCardInLibrary, source, game);
        Card card = player.getLibrary().getCard(targetCardInLibrary.getFirstTarget(), game);
        if (card != null) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
        }
        player.shuffleLibrary(source, game);
        return true;
    }
}
