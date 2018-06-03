
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.watchers.common.DragonOnTheBattlefieldWhileSpellWasCastWatcher;

/**
 *
 * @author LevelX2
 */
public final class ScaleguardSentinels extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Dragon card from your hand (you don't have to)");

    static {
        filter.add(new SubtypePredicate(SubType.DRAGON));
    }

    public ScaleguardSentinels(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // As an additional cost to cast Scaleguard Sentinels, you may reveal a Dragon card from your hand.
        this.getSpellAbility().addEffect(new InfoEffect("as an additional cost to cast this spell, you may reveal a Dragon card from your hand"));

        // Scaleguard Sentinels enters the battlefield with a +1/+1 counter on it if you revealed a Dragon card or controlled a Dragon as you cast Scaleguard Sentinels.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(), true),
                ScaleguardSentinelsCondition.instance,
                "{this} enters the battlefield with a +1/+1 counter on it if you revealed a Dragon card or controlled a Dragon as you cast {this}.", ""),
                new DragonOnTheBattlefieldWhileSpellWasCastWatcher());

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

    public ScaleguardSentinels(final ScaleguardSentinels card) {
        super(card);
    }

    @Override
    public ScaleguardSentinels copy() {
        return new ScaleguardSentinels(this);
    }
}

enum ScaleguardSentinelsCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanentEntering(source.getSourceId());
        if (sourcePermanent != null) {
            DragonOnTheBattlefieldWhileSpellWasCastWatcher watcher = (DragonOnTheBattlefieldWhileSpellWasCastWatcher) game.getState().getWatchers().get(DragonOnTheBattlefieldWhileSpellWasCastWatcher.class.getSimpleName());
            return (watcher != null && watcher.castWithConditionTrue(sourcePermanent.getSpellAbility().getId()));
        }
        return false;
    }
}
