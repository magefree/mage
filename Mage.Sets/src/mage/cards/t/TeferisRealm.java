
package mage.cards.t;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
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
        addSuperType(SuperType.WORLD);

        // At the beginning of each player's upkeep, that player chooses artifact, creature, land, or non-Aura enchantment. All nontoken permanents of that type phase out.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new TeferisRealmEffect(), TargetController.ANY, false));
    }

    public TeferisRealm(final TeferisRealm card) {
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
    private static final Set<String> choices = new HashSet<>();

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
            String choosenType = choiceImpl.getChoice();
            FilterPermanent filter = new FilterPermanent();
            filter.add(Predicates.not(TokenPredicate.instance));
            switch (choosenType) {
                case ARTIFACT:
                    filter.add(new CardTypePredicate(CardType.ARTIFACT));
                    break;
                case CREATURE:
                    filter.add(new CardTypePredicate(CardType.CREATURE));
                    break;
                case LAND:
                    filter.add(new CardTypePredicate(CardType.LAND));
                    break;
                case NON_AURA_ENCHANTMENT:
                    filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
                    filter.add(Predicates.not(new SubtypePredicate(SubType.AURA)));
                    break;
                default:
                    return false;
            }
            game.informPlayers(player.getLogName() + " chooses " + choosenType + "s to phase out");
            List<UUID> permIds = new ArrayList<>();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, controller.getId(), game)) {
                permIds.add(permanent.getId());
            }
            return new PhaseOutAllEffect(permIds).apply(game, source);
        }
        return false;
    }
}
