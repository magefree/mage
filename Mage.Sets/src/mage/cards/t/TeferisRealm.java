
package mage.cards.t;

import java.util.*;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PhaseOutAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class TeferisRealm extends CardImpl {

    public TeferisRealm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{U}");
        this.supertype.add(SuperType.WORLD);

        // At the beginning of each player's upkeep, that player chooses artifact, creature, land, or non-Aura enchantment. All nontoken permanents of that type phase out.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new TeferisRealmEffect(), TargetController.ANY, false));
    }

    private TeferisRealm(final TeferisRealm card) {
        super(card);
    }

    @Override
    public TeferisRealm copy() {
        return new TeferisRealm(this);
    }
}

class TeferisRealmEffect extends OneShotEffect {

    private static final String ARTIFACT = "Artifact";
    private static final String CREATURE = "Creature";
    private static final String LAND = "Land";
    private static final String NON_AURA_ENCHANTMENT = "Non-Aura enchantment";
    private static final Set<String> choices = new LinkedHashSet<>();

    static {
        choices.add(ARTIFACT);
        choices.add(CREATURE);
        choices.add(LAND);
        choices.add(NON_AURA_ENCHANTMENT);
    }

    public TeferisRealmEffect() {
        super(Outcome.Detriment);
        this.staticText = "that player chooses artifact, creature, land, or non-Aura enchantment. All nontoken permanents of that type phase out";
    }

    public TeferisRealmEffect(final TeferisRealmEffect effect) {
        super(effect);
    }

    @Override
    public TeferisRealmEffect copy() {
        return new TeferisRealmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (player != null && controller != null) {
            Choice choiceImpl = new ChoiceImpl(true);
            choiceImpl.setMessage("Phase out which kind of permanents?");
            choiceImpl.setChoices(choices);
            if (!player.choose(outcome, choiceImpl, game)) {
                return false;
            }
            String chosenType = choiceImpl.getChoice();
            FilterPermanent filter = new FilterPermanent();
            filter.add(TokenPredicate.FALSE);
            switch (chosenType) {
                case ARTIFACT:
                    filter.add(CardType.ARTIFACT.getPredicate());
                    break;
                case CREATURE:
                    filter.add(CardType.CREATURE.getPredicate());
                    break;
                case LAND:
                    filter.add(CardType.LAND.getPredicate());
                    break;
                case NON_AURA_ENCHANTMENT:
                    filter.add(CardType.ENCHANTMENT.getPredicate());
                    filter.add(Predicates.not(SubType.AURA.getPredicate()));
                    break;
                default:
                    return false;
            }
            game.informPlayers(player.getLogName() + " chooses " + chosenType + "s to phase out");
            List<UUID> permIds = new ArrayList<>();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, controller.getId(), game)) {
                permIds.add(permanent.getId());
            }
            return new PhaseOutAllEffect(permIds).apply(game, source);
        }
        return false;
    }
}
