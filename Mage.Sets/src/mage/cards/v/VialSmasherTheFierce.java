package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.watchers.common.SpellsCastWatcher;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public final class VialSmasherTheFierce extends CardImpl {

    public VialSmasherTheFierce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cast your first spell each turn, Vial Smasher the Fierce deals damage equal to that spell's converted mana cost to an opponent chosen at random.
        this.addAbility(new VialSmasherTheFierceTriggeredAbility(), new SpellsCastWatcher());

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private VialSmasherTheFierce(final VialSmasherTheFierce card) {
        super(card);
    }

    @Override
    public VialSmasherTheFierce copy() {
        return new VialSmasherTheFierce(this);
    }
}

class VialSmasherTheFierceTriggeredAbility extends SpellCastControllerTriggeredAbility {

    VialSmasherTheFierceTriggeredAbility() {
        super(new VialSmasherTheFierceEffect(), false);
    }

    VialSmasherTheFierceTriggeredAbility(VialSmasherTheFierceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VialSmasherTheFierceTriggeredAbility copy() {
        return new VialSmasherTheFierceTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
            if (watcher != null) {
                List<Spell> spells = watcher.getSpellsCastThisTurn(event.getPlayerId());
                if (spells != null && spells.size() == 1) {
                    Spell spell = game.getStack().getSpell(event.getTargetId());
                    if (spell != null) {
                        for (Effect effect : getEffects()) {
                            effect.setValue("VialSmasherTheFierceCMC", spell.getManaValue());
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast your first spell each turn, choose an opponent at random. "
                + "{this} deals damage equal to that spell's mana value to that player or a planeswalker that player controls.";
    }
}

class VialSmasherTheFierceEffect extends OneShotEffect {

    public VialSmasherTheFierceEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} choose an opponent at random. {this} deals damage equal to that spell's mana value to that player or a planeswalker that player controls";
    }

    public VialSmasherTheFierceEffect(final VialSmasherTheFierceEffect effect) {
        super(effect);
    }

    @Override
    public VialSmasherTheFierceEffect copy() {
        return new VialSmasherTheFierceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int damage = (Integer) getValue("VialSmasherTheFierceCMC");
            if (damage > 0) {
                List<Player> opponents = game.getOpponents(source.getControllerId())
                        .stream()
                        .map(game::getPlayer)
                        .filter(Objects::nonNull)
                        .filter(o -> !o.hasLost())
                        .filter(o -> !o.hasLeft())
                        .collect(Collectors.toList());
                if(opponents.isEmpty()){
                    return false;
                }
                Collections.shuffle(opponents);
                Player opponent = opponents.get(0);
                game.informPlayers(opponent.getLogName() + " was chosen at random.");
                if (game.getBattlefield().getAllActivePermanents(new FilterPlaneswalkerPermanent(), opponent.getId(), game).size() > 0) {
                    if (controller.chooseUse(Outcome.Damage, "Redirect to a planeswalker controlled by " + opponent.getLogName() + "?", source, game)) {
                        FilterPlaneswalkerPermanent filter = new FilterPlaneswalkerPermanent("a planeswalker controlled by " + opponent.getLogName());
                        filter.add(new ControllerIdPredicate(opponent.getId()));
                        TargetPermanent target = new TargetPermanent(1, 1, filter, false);
                        if (target.choose(Outcome.Damage, controller.getId(), source.getSourceId(), source, game)) {
                            Permanent permanent = game.getPermanent(target.getFirstTarget());
                            if (permanent != null) {
                                return permanent.damage(damage, source.getSourceId(), source, game, false, true) > 0;
                            }
                        }
                    }
                }
                opponent.damage(damage, source.getSourceId(), source, game);

            }
            return true;
        }
        return false;
    }
}
