
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.watchers.common.DragonOnTheBattlefieldWhileSpellWasCastWatcher;

/**
 *
 * @author LevelX2
 */
public final class OratorOfOjutai extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Dragon card from your hand (you don't have to)");

    static {
        filter.add(new SubtypePredicate(SubType.DRAGON));
    }

    public OratorOfOjutai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender, flying
        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(FlyingAbility.getInstance());

        // As an additional cost to cast Orator of Ojutai, you may reveal a Dragon card from your hand.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new InfoEffect("as an additional cost to cast this spell, you may reveal a Dragon card from your hand")));

        // When Orator of Ojutai enters the battlefield, if you revealed a Dragon card or controlled a Dragon as you cast Orator of Ojutai, draw a card.
        this.addAbility(new OratorOfOjutaiTriggeredAbility(new OratorOfOjutaiEffect()), new DragonOnTheBattlefieldWhileSpellWasCastWatcher());
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (ability.getAbilityType() == AbilityType.SPELL) {
            Player controller = game.getPlayer(ability.getControllerId());
            if (controller != null) {
                if (controller.getHand().count(filter, game) > 0) {
                    ability.addCost(new RevealTargetFromHandCost(new TargetCardInHand(0, 1, filter)));
                }
            }
        }
    }

    public OratorOfOjutai(final OratorOfOjutai card) {
        super(card);
    }

    @Override
    public OratorOfOjutai copy() {
        return new OratorOfOjutai(this);
    }
}

class OratorOfOjutaiTriggeredAbility extends TriggeredAbilityImpl {

    public OratorOfOjutaiTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    public OratorOfOjutaiTriggeredAbility(final OratorOfOjutaiTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        //Intervening if must be checked
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(getSourceId());
        DragonOnTheBattlefieldWhileSpellWasCastWatcher watcher = game.getState().getWatcher(DragonOnTheBattlefieldWhileSpellWasCastWatcher.class);
        return event.getTargetId().equals(getSourceId())
                && watcher != null
                && watcher.castWithConditionTrue(sourcePermanent.getSpellAbility().getId());
    }

    @Override
    public String getRule() {
        return "When {this} enters the battlefield, " + super.getRule();
    }

    @Override
    public OratorOfOjutaiTriggeredAbility copy() {
        return new OratorOfOjutaiTriggeredAbility(this);
    }
}

class OratorOfOjutaiEffect extends OneShotEffect {

    public OratorOfOjutaiEffect() {
        super(Outcome.Benefit);
        this.staticText = "If you revealed a Dragon card or controlled a Dragon as you cast {this}, draw a card";
    }

    public OratorOfOjutaiEffect(final OratorOfOjutaiEffect effect) {
        super(effect);
    }

    @Override
    public OratorOfOjutaiEffect copy() {
        return new OratorOfOjutaiEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        //Intervening if is checked again on resolution
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (sourcePermanent != null) {
                DragonOnTheBattlefieldWhileSpellWasCastWatcher watcher = game.getState().getWatcher(DragonOnTheBattlefieldWhileSpellWasCastWatcher.class);
                if (watcher != null && watcher.castWithConditionTrue(sourcePermanent.getSpellAbility().getId())) {
                    controller.drawCards(1, game);
                    return true;
                }
            }
        }
        return false;
    }
}
