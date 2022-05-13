package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CanBeEnchantedByPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * @author L_J
 */
public final class Retether extends CardImpl {

    public Retether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");

        // Return each Aura card from your graveyard to the battlefield. Only creatures can be enchanted this way.
        this.getSpellAbility().addEffect(new RetetherEffect());
    }

    private Retether(final Retether card) {
        super(card);
    }

    @Override
    public Retether copy() {
        return new Retether(this);
    }
}

class RetetherEffect extends OneShotEffect {

    private static final FilterCard filterAura = new FilterCard("Aura card from your graveyard");

    static {
        filterAura.add(CardType.ENCHANTMENT.getPredicate());
        filterAura.add(SubType.AURA.getPredicate());
    }

    public RetetherEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Return each Aura card from your graveyard to the battlefield. Only creatures can be enchanted this way";
    }

    public RetetherEffect(final RetetherEffect effect) {
        super(effect);
    }

    @Override
    public RetetherEffect copy() {
        return new RetetherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<Card, Permanent> auraMap = new HashMap<>();
            auraCardsInGraveyard:
            for (Card aura : controller.getGraveyard().getCards(filterAura, source.getControllerId(), source, game)) {
                if (aura != null) {
                    FilterCreaturePermanent filter = new FilterCreaturePermanent("creature to enchant (" + aura.getLogName() + ')');
                    filter.add(new CanBeEnchantedByPredicate(aura));
                    Target target = null;

                    auraLegalitySearch:
                    for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, playerId, game)) {
                            if (permanent != null) {
                                for (Ability ability : aura.getAbilities()) {
                                    if (ability instanceof SpellAbility) {
                                        for (Target abilityTarget : ability.getTargets()) {
                                            if (abilityTarget.possibleTargets(controller.getId(), game).contains(permanent.getId())) {
                                                target = abilityTarget.copy();
                                                break auraLegalitySearch;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (target != null) {
                        target.getFilter().add(CardType.CREATURE.getPredicate());
                        target.setNotTarget(true);
                        if (target.canChoose(controller.getId(), source, game)) {
                            target.setTargetName("creature to enchant (" + aura.getLogName() + ')');
                            if (controller.choose(Outcome.PutCardInPlay, target, source, game)) {
                                Permanent permanent = game.getPermanent(target.getFirstTarget());
                                if (permanent != null && !permanent.cantBeAttachedBy(aura, source, game, true)) {
                                    auraMap.put(aura, permanent);
                                    game.getState().setValue("attachTo:" + aura.getId(), permanent);
                                    continue auraCardsInGraveyard;
                                }
                            }
                        }
                    }
                    game.informPlayers("No valid creature targets for " + aura.getLogName());
                }
            }
            controller.moveCards(auraMap.keySet(), Zone.BATTLEFIELD, source, game);
            for (Entry<Card, Permanent> entry : auraMap.entrySet()) {
                Card aura = entry.getKey();
                Permanent permanent = entry.getValue();
                if (aura != null && permanent != null) {
                    permanent.addAttachment(aura.getId(), source, game);
                }
            }
            return true;
        }
        return false;
    }
}
