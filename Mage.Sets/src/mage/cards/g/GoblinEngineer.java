package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.SearchEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoblinEngineer extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledArtifactPermanent("an artifact");
    private static final FilterCard filter2
            = new FilterArtifactCard("artifact card with mana value 3 or less from your graveyard");

    static {
        filter2.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public GoblinEngineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Goblin Engineer enters the battlefield, you may search your library for an artifact card, put it into your graveyard, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GoblinEngineerEffect(), true));

        // {R}, {T}, Sacrifice an artifact: Return target artifact card with converted mana cost 3 or less from your graveyard to the battlefield.
        Ability ability = new SimpleActivatedAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), new ManaCostsImpl<>("{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetCardInYourGraveyard(filter2));
        this.addAbility(ability);
    }

    private GoblinEngineer(final GoblinEngineer card) {
        super(card);
    }

    @Override
    public GoblinEngineer copy() {
        return new GoblinEngineer(this);
    }
}

class GoblinEngineerEffect extends SearchEffect {

    GoblinEngineerEffect() {
        super(new TargetCardInLibrary(StaticFilters.FILTER_CARD_ARTIFACT_AN), Outcome.Neutral);
        staticText = "search your library for an artifact card, put it into your graveyard, then shuffle";
    }

    private GoblinEngineerEffect(final GoblinEngineerEffect effect) {
        super(effect);
    }

    @Override
    public GoblinEngineerEffect copy() {
        return new GoblinEngineerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (controller.searchLibrary(target, source, game)) {
            controller.moveCards(game.getCard(target.getFirstTarget()), Zone.GRAVEYARD, source, game);
        }
        controller.shuffleLibrary(source, game);
        return true;
    }

}
