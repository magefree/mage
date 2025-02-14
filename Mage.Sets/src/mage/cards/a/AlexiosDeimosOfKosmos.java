package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.effects.*;
import mage.abilities.effects.common.TargetPlayerGainControlSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.CantBeSacrificedSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;
import java.util.UUID;

public class AlexiosDeimosOfKosmos extends CardImpl {
    public AlexiosDeimosOfKosmos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // attacks each combat if able, can't be sacrificed, and can't attack it's owner
        Ability ability = new AttacksEachCombatStaticAbility();
        ability.addEffect(new CantBeSacrificedSourceEffect().setText(", can't be sacrificed"));
        ability.addEffect(new AlexiosDeimosOfKosmosRestrictionEffect());
        this.addAbility(ability);

        // at the beginning of each player's upkeep, that player gains control of alexios, untaps it, and puts a +1/+1 counter on it. it gains haste until end of turn
        Ability upkeepAbility = new BeginningOfUpkeepTriggeredAbility(TargetController.EACH_PLAYER, null, false);
        upkeepAbility.addEffect(new TargetPlayerGainControlSourceEffect().setText("that player gains control of Alexios"));
        upkeepAbility.addEffect(new UntapSourceEffect().setText(", untaps it"));
        upkeepAbility.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)).setText(", and puts a +1/+1 counter on it"));
        upkeepAbility.addEffect(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.EndOfTurn).setText("it gains haste until end of turn"));
        this.addAbility(upkeepAbility);
    }

    public AlexiosDeimosOfKosmos(CardImpl card) {
        super(card);
    }

    @Override
    public AlexiosDeimosOfKosmos copy() {
        return new AlexiosDeimosOfKosmos(this);
    }

    class AlexiosDeimosOfKosmosRestrictionEffect extends RestrictionEffect {
        public AlexiosDeimosOfKosmosRestrictionEffect() {
            super(Duration.WhileOnBattlefield);
            this.staticText = ", and can't attack it's owner";
        }

        public AlexiosDeimosOfKosmosRestrictionEffect(RestrictionEffect effect) {
            super(effect);
        }

        @Override
        public boolean applies(Permanent permanent, Ability source, Game game) {
            return Objects.equals(permanent.getId(), source.getSourceId());
        }

        @Override
        public AlexiosDeimosOfKosmosRestrictionEffect copy() {
            return new AlexiosDeimosOfKosmosRestrictionEffect(this);
        }

        @Override
        public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
            if (defenderId == null) {
                return true;
            }

            Permanent permanent = source.getSourcePermanentIfItStillExists(game);
            if (permanent == null) {
                return true;
            }

            UUID ownerId = permanent.getOwnerId();
            return !defenderId.equals(ownerId);
        }
    }
}
