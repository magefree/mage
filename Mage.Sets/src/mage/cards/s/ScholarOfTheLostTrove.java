package mage.cards.s;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScholarOfTheLostTrove extends CardImpl {

    private static final FilterCard filter
            = new FilterCard("instant, sorcery, or artifact card from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate(),
                CardType.ARTIFACT.getPredicate()
        ));
    }

    public ScholarOfTheLostTrove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Scholar of the Lost Trove enters the battlefield, you may cast target instant, sorcery, or artifact card from your graveyard without paying its mana cost. If an instant or sorcery spell cast this way would be put into your graveyard this turn, exile it instead.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ScholarOfTheLostTroveEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private ScholarOfTheLostTrove(final ScholarOfTheLostTrove card) {
        super(card);
    }

    @Override
    public ScholarOfTheLostTrove copy() {
        return new ScholarOfTheLostTrove(this);
    }
}

class ScholarOfTheLostTroveEffect extends OneShotEffect {

    ScholarOfTheLostTroveEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may cast target instant, sorcery, or artifact card from your graveyard without paying its mana cost. " +
                "If an instant or sorcery spell cast this way would be put into your graveyard this turn, exile it instead";
    }

    private ScholarOfTheLostTroveEffect(final ScholarOfTheLostTroveEffect effect) {
        super(effect);
    }

    @Override
    public ScholarOfTheLostTroveEffect copy() {
        return new ScholarOfTheLostTroveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (card == null) {
            return true;
        }
        if (!controller.chooseUse(Outcome.PlayForFree, "Cast " + card.getLogName() + '?', source, game)) {
            return true;
        }
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
        boolean cardWasCast = controller.cast(controller.chooseAbilityForCast(card, game, true),
                game, true, new ApprovingObject(source, game));
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
        if (!cardWasCast || !card.isInstantOrSorcery(game)) {
            return true;
        }
        ContinuousEffect effect = new ThatSpellGraveyardExileReplacementEffect(true);
        effect.setTargetPointer(new FixedTarget(card.getId(), game.getState().getZoneChangeCounter(card.getId())));
        effect.setText("If an instant or sorcery spell cast this way would be put into your graveyard this turn, exile it instead");
        game.addEffect(effect, source);
        return true;
    }
}
