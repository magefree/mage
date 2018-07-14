
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class CruelReality extends CardImpl {

    public CruelReality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{B}{B}");
        this.subtype.add(SubType.AURA, SubType.CURSE);
        this.color.setBlack(true);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Damage));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        //At the beginning of enchanted player's upkeep, that player sacrifices a creature or planeswalker. If the player can't, he or she loses 5 life.
        this.addAbility(new CruelRealityTriggeredAbiilty());

    }

    public CruelReality(final CruelReality card) {
        super(card);
    }

    @Override
    public CruelReality copy() {
        return new CruelReality(this);
    }
}

class CruelRealityTriggeredAbiilty extends TriggeredAbilityImpl {

    public CruelRealityTriggeredAbiilty() {
        super(Zone.BATTLEFIELD, new CruelRealityEffect());
    }

    public CruelRealityTriggeredAbiilty(final CruelRealityTriggeredAbiilty ability) {
        super(ability);
    }

    @Override
    public CruelRealityTriggeredAbiilty copy() {
        return new CruelRealityTriggeredAbiilty(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.sourceId);
        if (enchantment != null
                && enchantment.getAttachedTo() != null) {
            Player cursedPlayer = game.getPlayer(enchantment.getAttachedTo());
            if (cursedPlayer != null
                    && game.isActivePlayer(cursedPlayer.getId())) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(cursedPlayer.getId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of enchanted player's upkeep, " + super.getRule();
    }
}

class CruelRealityEffect extends OneShotEffect {

    public CruelRealityEffect() {
        super(Outcome.LoseLife);
        staticText = "that player sacrifices a creature or planeswalker. If the player can't, he or she loses 5 life";
    }

    public CruelRealityEffect(final CruelRealityEffect effect) {
        super(effect);
    }

    @Override
    public CruelRealityEffect copy() {
        return new CruelRealityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player cursedPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (cursedPlayer != null
                && controller != null) {
                FilterControlledPermanent filter = new FilterControlledPermanent("creature or planeswalker");
                filter.add(Predicates.or(
                        new CardTypePredicate(CardType.CREATURE),
                        new CardTypePredicate(CardType.PLANESWALKER)));
                TargetPermanent target = new TargetPermanent(filter);
                if (cursedPlayer.choose(Outcome.Sacrifice, target, source.getId(), game)) {
                    Permanent objectToBeSacrificed = game.getPermanent(target.getFirstTarget());
                    if (objectToBeSacrificed != null) {
                        if (objectToBeSacrificed.sacrifice(source.getId(), game)) {
                            return true;
                        }
                    }
                }
            cursedPlayer.loseLife(5, game, false);
            return true;
        }
        return false;
    }
}
