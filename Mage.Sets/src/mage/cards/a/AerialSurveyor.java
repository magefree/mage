package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class AerialSurveyor extends CardImpl {

    public AerialSurveyor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Aerial Surveyor attacks, if defending player controls more lands than you, search your library for a basic Plains card, put it onto the battlefield tapped, then shuffle.
        this.addAbility(new AttacksTriggeredAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_PLAINS), true))
                .withInterveningIf(AerialSurveyorCondition.instance)
                .addHint(LandsYouControlHint.instance)
                .addHint(AerialSurveyorHint.instance));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private AerialSurveyor(final AerialSurveyor card) {
        super(card);
    }

    @Override
    public AerialSurveyor copy() {
        return new AerialSurveyor(this);
    }
}

enum AerialSurveyorCondition implements Condition {
    instance;
    private static final FilterPermanent filter = new FilterLandPermanent();

    static {
        filter.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getBattlefield().count(
                filter, source.getControllerId(), source, game
        ) > game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                source.getControllerId(), source, game
        );
    }

    @Override
    public String toString() {
        return "defending player controls more lands than you";
    }
}

enum AerialSurveyorHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        return game.getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_LAND,
                        ability.getControllerId(),
                        ability, game
                ).stream()
                .map(Controllable::getControllerId)
                .filter(game.getOpponents(ability.getControllerId())::contains)
                .collect(Collectors.toMap(Function.identity(), u -> 1, Integer::sum))
                .entrySet()
                .stream()
                .filter(entry -> game.getPlayer(entry.getKey()) != null)
                .map(entry -> "Lands " + game.getPlayer(entry.getKey()).getName() + " controls: " + entry.getValue())
                .collect(Collectors.joining("<br>"));
    }

    @Override
    public AerialSurveyorHint copy() {
        return instance;
    }
}
