package mage.cards.f;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.continuous.GainAnchorWordAbilitySourceEffect;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL;

/**
 * @author LevelX2
 */
public final class FrontierSiege extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("a creature you control with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public FrontierSiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // As Frontier Siege enters the battlefield, choose Khans or Dragons.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseModeEffect(ModeChoice.KHANS, ModeChoice.DRAGONS)));

        // * Khans - At the beginning of each of your main phases, add {G}{G}.
        this.addAbility(new SimpleStaticAbility(new GainAnchorWordAbilitySourceEffect(new FrontierSiegeKhansTriggeredAbility(), ModeChoice.KHANS)));

        // * Dragons - Whenever a creature with flying you control enters, you may have it fight target creature you don't control.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new FrontierSiegeFightEffect(), filter, true, SetTargetPointer.PERMANENT
        );
        ability.addTarget(new TargetPermanent(FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(new SimpleStaticAbility(new GainAnchorWordAbilitySourceEffect(ability, ModeChoice.DRAGONS)));
    }

    private FrontierSiege(final FrontierSiege card) {
        super(card);
    }

    @Override
    public FrontierSiege copy() {
        return new FrontierSiege(this);
    }
}

class FrontierSiegeKhansTriggeredAbility extends TriggeredAbilityImpl {

    FrontierSiegeKhansTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddManaToManaPoolSourceControllerEffect(Mana.GreenMana(2)), false);
        setTriggerPhrase("At the beginning of each of your main phases, ");
    }

    private FrontierSiegeKhansTriggeredAbility(final FrontierSiegeKhansTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FrontierSiegeKhansTriggeredAbility copy() {
        return new FrontierSiegeKhansTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PRECOMBAT_MAIN_PHASE_PRE
                || event.getType() == GameEvent.EventType.POSTCOMBAT_MAIN_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.controllerId);
    }
}

class FrontierSiegeFightEffect extends OneShotEffect {

    FrontierSiegeFightEffect() {
        super(Outcome.Damage);
        staticText = "have it fight target creature you don't control";
    }

    private FrontierSiegeFightEffect(final FrontierSiegeFightEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent triggeredCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (triggeredCreature != null
                && target != null
                && triggeredCreature.isCreature(game)
                && target.isCreature(game)) {
            triggeredCreature.fight(target, source, game);
            return true;
        }
        return false;
    }

    @Override
    public FrontierSiegeFightEffect copy() {
        return new FrontierSiegeFightEffect(this);
    }
}
