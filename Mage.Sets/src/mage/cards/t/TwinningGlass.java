package mage.cards.t;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;
import mage.watchers.common.SpellsCastWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author jeffwadsworth
 */
public final class TwinningGlass extends CardImpl {

    public TwinningGlass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {1}, {tap}: You may cast a nonland card from your hand without paying 
        // its mana cost if it has the same name as a spell that was cast this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new TwinningGlassEffect(), new ManaCostsImpl("{1}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability, new SpellsCastWatcher());

    }

    public TwinningGlass(final TwinningGlass card) {
        super(card);
    }

    @Override
    public TwinningGlass copy() {
        return new TwinningGlass(this);
    }
}

class TwinningGlassEffect extends OneShotEffect {

    public TwinningGlassEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "You may cast a nonland card from your hand "
                + "without paying its mana cost if it has the same name "
                + "as a spell that was cast this turn";
    }

    public TwinningGlassEffect(final TwinningGlassEffect effect) {
        super(effect);
    }

    @Override
    public TwinningGlassEffect copy() {
        return new TwinningGlassEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Spell> spells = new ArrayList<>();
        Permanent twinningGlass = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (twinningGlass == null) {
            twinningGlass = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (twinningGlass != null
                && controller != null
                && watcher != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (watcher.getSpellsCastThisTurn(playerId) != null) {
                    for (Spell spell : watcher.getSpellsCastThisTurn(playerId)) {
                        spells.add(spell);
                    }
                }
            }
            if (spells.isEmpty()) {
                return false;
            }
            List<NamePredicate> predicates = spells.stream()
                    .map(Spell::getName)
                    .filter(Objects::nonNull)
                    .filter(s -> !s.isEmpty())
                    .map(NamePredicate::new)
                    .collect(Collectors.toList());
            FilterNonlandCard filterCard = new FilterNonlandCard("nonland card that was cast this turn");
            filterCard.add(Predicates.or(predicates));
            TargetCard target = new TargetCard(0, 1, Zone.HAND, filterCard);
            if (controller.choose(Outcome.PlayForFree, controller.getHand(), target, game)) {
                Card chosenCard = game.getCard(target.getFirstTarget());
                if (chosenCard != null) {
                    if (controller.chooseUse(Outcome.PlayForFree, "Cast "
                            + chosenCard.getName() + " without paying its mana cost?", source, game)) {
                        game.getState().setValue("PlayFromNotOwnHandZone" + chosenCard.getId(), Boolean.TRUE);
                        Boolean cardWasCast = controller.cast(controller.chooseAbilityForCast(chosenCard, game, true),
                                game, true, new ApprovingObject(source, game));
                        game.getState().setValue("PlayFromNotOwnHandZone" + chosenCard.getId(), null);
                        return cardWasCast;
                    }
                }
            }
        }
        return false;
    }
}
