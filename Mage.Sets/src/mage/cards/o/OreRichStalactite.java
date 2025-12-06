package mage.cards.o;

import mage.MageObject;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CraftAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.common.InstantOrSorcerySpellManaBuilder;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * Ore-Rich Stalactite {1}{R}
 * Artifact
 * {T}: Add {R}. Spend this mana only to cast an instant or sorcery spell.
 * Craft with four or more red instant and/or sorcery cards {3}{R}{R}.
 *
 * @author DominionSpy
 */
public class OreRichStalactite extends TransformingDoubleFacedCard {

    public OreRichStalactite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{1}{R}",
                "Cosmium Catalyst",
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "R"
        );

        // Ore-Rich Stalactite
        // {T}: Add {R}. Spend this mana only to cast an instant or sorcery spell.
        this.getLeftHalfCard().addAbility(new ConditionalColoredManaAbility(Mana.RedMana(1), new InstantOrSorcerySpellManaBuilder()));

        // Craft with four or more red instant and/or sorcery cards {3}{R}{R}
        this.getLeftHalfCard().addAbility(new CraftAbility("{3}{R}{R}", "four or more red instant and/or sorcery cards",
                "red instant and/or sorcery cards in your graveyard", 4, Integer.MAX_VALUE,
                new ColorPredicate(ObjectColor.RED),
                Predicates.or(CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate())));

        // Cosmium Catalyst
        // {1}{R}, {T}: Choose an exiled card used to craft Cosmium Catalyst at random. You may cast that card without paying its mana cost.
        Ability ability = new SimpleActivatedAbility(new CosmiumCatalystEffect(), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new TapSourceCost());
        this.getRightHalfCard().addAbility(ability);
    }

    private OreRichStalactite(final OreRichStalactite card) {
        super(card);
    }

    @Override
    public OreRichStalactite copy() {
        return new OreRichStalactite(this);
    }
}

class CosmiumCatalystEffect extends OneShotEffect {

    CosmiumCatalystEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Choose an exiled card used to craft {this} at random." +
                " You may cast that card without paying its mana cost.";
    }

    private CosmiumCatalystEffect(CosmiumCatalystEffect effect) {
        super(effect);
    }

    @Override
    public CosmiumCatalystEffect copy() {
        return new CosmiumCatalystEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject == null) {
            return false;
        }

        Target target = new TargetCardInExile(StaticFilters.FILTER_CARD,
                CardUtil.getExileZoneId(game, source.getSourceId(),
                        game.getState().getZoneChangeCounter(source.getSourceId()) - 2
                ));
        target.withNotTarget(true);
        target.setRandom(true);
        if (!target.canChoose(controller.getId(), source, game)) {
            return true;
        }
        target.chooseTarget(outcome, controller.getId(), source, game);
        Card chosenCard = game.getCard(target.getFirstTarget());
        if (chosenCard != null) {
            CardUtil.castSpellWithAttributesForFree(controller, source, game, chosenCard);
        }
        return true;
    }
}
