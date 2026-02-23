package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.keyword.ExploreTargetEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.events.ExploreEvent;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class TwistsAndTurns extends TransformingDoubleFacedCard {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledLandPermanent("you control seven or more lands"),
            ComparisonType.MORE_THAN, 6, true
    );

    public TwistsAndTurns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "{G}",
                "Mycoid Maze",
                new CardType[]{CardType.LAND}, new SubType[]{SubType.CAVE}, ""
        );

        // Twists and Turns
        // If a creature you control would explore, instead you scry 1, then that creature explores.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new TwistsAndTurnsReplacementEffect()));

        // When Twists and Turns enters the battlefield, target creature you control explores.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExploreTargetEffect(false));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.getLeftHalfCard().addAbility(ability);

        // When a land you control enters, if you control seven or more lands, transform Twists and Turns.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new TransformSourceEffect(), StaticFilters.FILTER_LAND
        ).withInterveningIf(condition).setTriggerPhrase("When a land you control enters, "));

        // Mycoid Maze
        // {T}: Add {G}.
        this.getRightHalfCard().addAbility(new GreenManaAbility());

        // {3}{G}, {T}: Look at the top four cards of your library. You may reveal a creature card from among them and put that card into your hand. Put the rest on the bottom of your library in a random order.
        Ability ability2 = new SimpleActivatedAbility(new LookLibraryAndPickControllerEffect(
                4, 1, StaticFilters.FILTER_CARD_CREATURE_A, PutCards.HAND, PutCards.BOTTOM_RANDOM
        ), new ManaCostsImpl<>("{3}{G}"));
        ability2.addCost(new TapSourceCost());
        this.getRightHalfCard().addAbility(ability2);
    }

    private TwistsAndTurns(final TwistsAndTurns card) {
        super(card);
    }

    @Override
    public TwistsAndTurns copy() {
        return new TwistsAndTurns(this);
    }
}

class TwistsAndTurnsReplacementEffect extends ReplacementEffectImpl {

    TwistsAndTurnsReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a creature you control would explore, instead you scry 1, then that creature explores";
    }

    private TwistsAndTurnsReplacementEffect(final TwistsAndTurnsReplacementEffect effect) {
        super(effect);
    }

    @Override
    public TwistsAndTurnsReplacementEffect copy() {
        return new TwistsAndTurnsReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.EXPLORE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getControllerId().equals(event.getPlayerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ExploreEvent exploreEvent = (ExploreEvent) event;
        exploreEvent.addScry();
        return false;
    }
}
