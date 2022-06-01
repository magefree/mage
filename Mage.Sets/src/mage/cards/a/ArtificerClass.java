package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.BecomesClassLevelTriggeredAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.TargetPermanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArtificerClass extends CardImpl {

    private static final FilterCard filter = new FilterArtifactCard();

    static {
        filter.add(ArtificerClassPredicate.instance);
    }

    public ArtificerClass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // The first artifact spell you cast each turn costs {1} less to cast.
        this.addAbility(new SimpleStaticAbility(
                new SpellsCostReductionControllerEffect(filter, 1)
                        .setText("the first artifact spell you cast each turn costs {1} less to cast")
        ), new ArtificerClassWatcher());

        // {1}{U}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{1}{U}"));

        // When this class becomes level 2, reveal cards from the top of you library until you reveal an artifact card. Put that card into your hand and the rest on the bottom of your library in a random order.
        this.addAbility(new BecomesClassLevelTriggeredAbility(
                new RevealCardsFromLibraryUntilEffect(
                        StaticFilters.FILTER_CARD_ARTIFACT_AN,
                        Zone.HAND, Zone.LIBRARY, false, false
                ), 2
        ));

        // {5}{U}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{5}{U}"));

        // At the beginning of your end step, create a token that's a copy of target artifact you control.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new CreateTokenCopyTargetEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT));
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(ability, 3)));
    }

    private ArtificerClass(final ArtificerClass card) {
        super(card);
    }

    @Override
    public ArtificerClass copy() {
        return new ArtificerClass(this);
    }
}

enum ArtificerClassPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return input.getObject().isArtifact(game)
                && !ArtificerClassWatcher.checkPlayer(input.getPlayerId(), game);
    }
}

class ArtificerClassWatcher extends Watcher {

    private final Set<UUID> playerSet = new HashSet<>();

    ArtificerClassWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && spell.isArtifact(game)) {
            playerSet.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerSet.clear();
    }

    public static boolean checkPlayer(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(ArtificerClassWatcher.class)
                .playerSet
                .contains(playerId);
    }
}
