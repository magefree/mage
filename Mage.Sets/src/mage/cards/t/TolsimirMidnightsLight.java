package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TolsimirMidnightsLightToken;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.watchers.common.AttackedOrBlockedThisCombatWatcher;

/**
 *
 * @author DominionSpy
 */
public final class TolsimirMidnightsLight extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.WOLF, "Wolf you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public TolsimirMidnightsLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Tolsimir, Midnight's Light enters the battlefield, create Voja Fenstalker, a legendary 5/5 green and white Wolf creature token with trample.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TolsimirMidnightsLightToken())));

        // Whenever a Wolf you control attacks, if Tolsimir attacked this combat, target creature an opponent controls blocks that Wolf this combat if able.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new AttacksAllTriggeredAbility(new TolsimirMidnightsLightEffect(), false, filter, SetTargetPointer.PERMANENT, false),
                TolsimirMidnightsLightCondition.instance,
                "Whenever a Wolf you control attacks, if {this} attacked this combat, target creature an opponent controls blocks that Wolf this combat if able.");
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability, new AttackedOrBlockedThisCombatWatcher());
    }

    private TolsimirMidnightsLight(final TolsimirMidnightsLight card) {
        super(card);
    }

    @Override
    public TolsimirMidnightsLight copy() {
        return new TolsimirMidnightsLight(this);
    }
}

enum TolsimirMidnightsLightCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent == null) {
            return false;
        }
        AttackedOrBlockedThisCombatWatcher watcher = game.getState().getWatcher(AttackedOrBlockedThisCombatWatcher.class);
        if (watcher == null) {
            return false;
        }
        for (MageObjectReference mor : watcher.getAttackedThisTurnCreatures()) {
            if (mor.refersTo(sourcePermanent, game)) {
                return true;
            }
        }
        return false;
    }
}

class TolsimirMidnightsLightEffect extends RequirementEffect {

    TolsimirMidnightsLightEffect() {
        super(Duration.EndOfCombat);
    }

    private TolsimirMidnightsLightEffect(final TolsimirMidnightsLightEffect effect) {
        super(effect);
    }

    @Override
    public TolsimirMidnightsLightEffect copy() {
        return new TolsimirMidnightsLightEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent attacker = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (attacker == null) {
            discard();
            return false;
        }
        return permanent != null &&
                permanent.getId().equals(source.getFirstTarget()) &&
                permanent.canBlock(getTargetPointer().getFirst(game, source), game);
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

    @Override
    public UUID mustBlockAttacker(Ability source, Game game) {
        return getTargetPointer().getFirst(game, source);
    }
}
