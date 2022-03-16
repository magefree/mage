package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacesCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.Pest11GainLifeToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ValentinDeanOfTheVein extends ModalDoubleFacesCard {

    public ValentinDeanOfTheVein(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.VAMPIRE, SubType.WARLOCK}, "{B}",
                "Lisette, Dean of the Root", new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.DRUID}, "{2}{G}{G}"
        );

        // 1.
        // Valentin, Dean of the Vein
        // Legendary Creature - Vampire Warlock
        this.getLeftHalfCard().addSuperType(SuperType.LEGENDARY);
        this.getLeftHalfCard().setPT(1, 1);

        // Menace
        this.getLeftHalfCard().addAbility(new MenaceAbility(false));

        // Lifelink
        this.getLeftHalfCard().addAbility(LifelinkAbility.getInstance());

        // If a nontoken creature an opponent controls would die, exile it instead. When you do, you may pay {2}. If you do, create a 1/1 black and green Pest creature token with "When this creature dies, you gain 1 life."
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new ValentinDeanOfTheVeinEffect()));

        // 2.
        // Lisette, Dean of the Root
        // Legendary Creature - Human Druid
        this.getRightHalfCard().addSuperType(SuperType.LEGENDARY);
        this.getRightHalfCard().setPT(4, 4);

        // Whenever you gain life, you may pay {1}. If you do, put a +1/+1 counter on each creature you control and those creatures gain trample until end of turn.
        this.getRightHalfCard().addAbility(new GainLifeControllerTriggeredAbility(
                new DoIfCostPaid(new AddCountersAllEffect(
                        CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
                ), new GenericManaCost(1)).addEffect(new GainAbilityControlledEffect(
                        TrampleAbility.getInstance(), Duration.EndOfTurn,
                        StaticFilters.FILTER_CONTROLLED_CREATURE
                ).setText("and those creatures gain trample until end of turn")), false
        ));
    }

    private ValentinDeanOfTheVein(final ValentinDeanOfTheVein card) {
        super(card);
    }

    @Override
    public ValentinDeanOfTheVein copy() {
        return new ValentinDeanOfTheVein(this);
    }
}

class ValentinDeanOfTheVeinEffect extends ReplacementEffectImpl {

    ValentinDeanOfTheVeinEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "if a nontoken creature an opponent controls would die, exile it instead. When you do, " +
                "you may pay {2}. If you do, create a 1/1 black and green Pest creature token " +
                "with \"When this creature dies, you gain 1 life.\"";
    }

    private ValentinDeanOfTheVeinEffect(final ValentinDeanOfTheVeinEffect effect) {
        super(effect);
    }

    @Override
    public ValentinDeanOfTheVeinEffect copy() {
        return new ValentinDeanOfTheVeinEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        game.fireReflexiveTriggeredAbility(new ReflexiveTriggeredAbility(
                new DoIfCostPaid(
                        new CreateTokenEffect(new Pest11GainLifeToken()), new GenericManaCost(2)
                ), false, "you may pay {2}. If you do, create a 1/1 black and green " +
                "Pest creature token with \"When this creature dies, you gain 1 life.\""
        ), source);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.isDiesEvent()
                && zEvent.getTarget() != null
                && !(zEvent.getTarget() instanceof PermanentToken)
                && zEvent.getTarget().isCreature(game)
                && game.getOpponents(zEvent.getTarget().getControllerId()).contains(source.getControllerId());
    }

}
