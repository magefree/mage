package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BardClass extends CardImpl {

    private static final FilterCard filter = new FilterCard("legendary spells");
    private static final FilterSpell filter2 = new FilterSpell("a legendary spell");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter2.add(SuperType.LEGENDARY.getPredicate());
    }

    public BardClass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}{G}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // Legendary creatures you control enter the battlefield with an additional +1/+1 counter on them.
        this.addAbility(new SimpleStaticAbility(new BardClassReplacementEffect()));

        // {R}{G}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{R}{G}"));

        // Legendary spells you cast cost {R}{G} less to cast. This effect reduces only the amount of colored mana you pay.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(
                new SpellsCostReductionControllerEffect(filter, new ManaCostsImpl<>("{R}{G}")), 2
        )));

        // {3}{R}{G}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{3}{R}{G}"));

        // Whenever you cast a legendary spell, exile the top two cards of your library. You may play them this turn.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(
                new SpellCastControllerTriggeredAbility(
                        new ExileTopXMayPlayUntilEndOfTurnEffect(2)
                                .setText("exile the top two cards of your library. You may play them this turn"),
                        filter2, false
                ), 3
        )));
    }

    private BardClass(final BardClass card) {
        super(card);
    }

    @Override
    public BardClass copy() {
        return new BardClass(this);
    }
}

class BardClassReplacementEffect extends ReplacementEffectImpl {

    BardClassReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "legendary creatures you control enter the battlefield with an additional +1/+1 counter on them";
    }

    private BardClassReplacementEffect(final BardClassReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        return creature != null
                && creature.isControlledBy(source.getControllerId())
                && creature.isCreature(game)
                && creature.isLegendary(game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null) {
            creature.addCounters(
                    CounterType.P1P1.createInstance(), source.getControllerId(),
                    source, game, event.getAppliedEffects()
            );
        }
        return false;
    }

    @Override
    public BardClassReplacementEffect copy() {
        return new BardClassReplacementEffect(this);
    }
}
