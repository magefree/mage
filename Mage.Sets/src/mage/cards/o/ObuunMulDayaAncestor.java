package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ObuunMulDayaAncestor extends CardImpl {

    public ObuunMulDayaAncestor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, up to one target land you control becomes an X/X Elemental creature with trample and haste until end of turn, where X is Obuun's power. It's still a land.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new ObuunMulDayaAncestorEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetPermanent(
                0, 1, StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, false
        ));
        this.addAbility(ability);

        // Landfall - Whenever a land enters the battlefield under your control, put a +1/+1 counter on target creature.
        ability = new LandfallAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ObuunMulDayaAncestor(final ObuunMulDayaAncestor card) {
        super(card);
    }

    @Override
    public ObuunMulDayaAncestor copy() {
        return new ObuunMulDayaAncestor(this);
    }
}

class ObuunMulDayaAncestorEffect extends OneShotEffect {

    ObuunMulDayaAncestorEffect() {
        super(Outcome.Benefit);
        staticText = "up to one target land you control becomes an X/X Elemental creature " +
                "with trample and haste until end of turn, where X is {this}'s power. It's still a land";
    }

    private ObuunMulDayaAncestorEffect(final ObuunMulDayaAncestorEffect effect) {
        super(effect);
    }

    @Override
    public ObuunMulDayaAncestorEffect copy() {
        return new ObuunMulDayaAncestorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        game.addEffect(new BecomesCreatureTargetEffect(
                new ObuunMulDayaAncestorToken(permanent.getPower().getValue()),
                false, true, Duration.EndOfTurn
        ), source);
        return true;
    }
}

class ObuunMulDayaAncestorToken extends TokenImpl {

    ObuunMulDayaAncestorToken(int xValue) {
        super("", "X/X Elemental creature with trample and haste");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(xValue);
        this.toughness = new MageInt(xValue);

        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
    }

    private ObuunMulDayaAncestorToken(final ObuunMulDayaAncestorToken token) {
        super(token);
    }

    public ObuunMulDayaAncestorToken copy() {
        return new ObuunMulDayaAncestorToken(this);
    }
}
