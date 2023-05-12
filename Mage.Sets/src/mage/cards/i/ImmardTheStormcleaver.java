package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImmardTheStormcleaver extends CardImpl {

    public ImmardTheStormcleaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Guile, Sonic Soldier enters the battlefield or attacks, put a charge counter on him or remove one from him. When you remove a counter this way, choose one—
        // • Sonic Boom—Guile, Sonic Soldier deals 4 damage to any target.
        // • Flash Kick—Guile, Sonic Soldier gains lifelink and indestructible until end of turn.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new ImmardTheStormcleaverEffect()));
    }

    private ImmardTheStormcleaver(final ImmardTheStormcleaver card) {
        super(card);
    }

    @Override
    public ImmardTheStormcleaver copy() {
        return new ImmardTheStormcleaver(this);
    }
}

class ImmardTheStormcleaverEffect extends OneShotEffect {

    private final ReflexiveTriggeredAbility ability = makeAbility();

    ImmardTheStormcleaverEffect() {
        super(Outcome.Benefit);
        staticText = "put a charge counter on him or remove one from him. When you remove a counter this way, "
                + CardUtil.getTextWithFirstCharLowerCase(ability.getRule());
    }

    private ImmardTheStormcleaverEffect(final ImmardTheStormcleaverEffect effect) {
        super(effect);
    }

    @Override
    public ImmardTheStormcleaverEffect copy() {
        return new ImmardTheStormcleaverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        if (!permanent.getCounters(game).containsKey(CounterType.CHARGE) || player.chooseUse(
                outcome, "Add or remove a charge counter?", null,
                "Add", "Remove", source, game
        )) {
            permanent.addCounters(CounterType.CHARGE.createInstance(), source, game);
        } else {
            permanent.removeCounters(CounterType.CHARGE.createInstance(), source, game);
            game.fireReflexiveTriggeredAbility(ability, source);
        }
        return true;
    }

    private static final ReflexiveTriggeredAbility makeAbility() {
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(4), false
        );
        ability.addTarget(new TargetAnyTarget());
        Mode mode = new Mode(new GainAbilitySourceEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn
        ).setText("{this} gains lifelink"));
        mode.addEffect(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and indestructible until end of turn"));
        ability.withFirstModeFlavorWord("Sonic Boom").addMode(mode.withFlavorWord("Flash Kick"));
        return ability;
    }
}
