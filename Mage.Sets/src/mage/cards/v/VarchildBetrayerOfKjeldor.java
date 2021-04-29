package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.combat.CantAttackYouOrPlaneswalkerAllEffect;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.abilities.effects.common.continuous.GainControlAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.SurvivorToken;

/**
 *
 * @author TheElk801
 */
public final class VarchildBetrayerOfKjeldor extends CardImpl {

    private static final FilterCreaturePermanent filter1
            = new FilterCreaturePermanent(
                    SubType.SURVIVOR,
                    "Survivors your opponents control"
            );

    static {
        filter1.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public VarchildBetrayerOfKjeldor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Varchild, Betrayer of Kjeldor deals combat damage to a player, that player creates that many 1/1 red Survivor creature tokens.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new VarchildBetrayerOfKjeldorEffect(), false, true
        ));

        // Survivors your opponents control can't block, and they can't attack you or a planeswalker you control.
        Ability ability = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new CantBlockAllEffect(
                        filter1, Duration.WhileOnBattlefield
                )
        );
        ability.addEffect(new CantAttackYouOrPlaneswalkerAllEffect(
                Duration.WhileOnBattlefield, filter1
        ).setText("and can't attack you or planeswalkers you control"));
        this.addAbility(ability);

        // When Varchild leaves the battlefield, gain control of all Survivors.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(
                new GainControlAllEffect(Duration.Custom, new FilterCreaturePermanent(SubType.SURVIVOR, "all Survivors")), false
        ));
    }

    private VarchildBetrayerOfKjeldor(final VarchildBetrayerOfKjeldor card) {
        super(card);
    }

    @Override
    public VarchildBetrayerOfKjeldor copy() {
        return new VarchildBetrayerOfKjeldor(this);
    }
}

class VarchildBetrayerOfKjeldorEffect extends OneShotEffect {

    public VarchildBetrayerOfKjeldorEffect() {
        super(Outcome.Benefit);
        this.staticText = "that player creates "
                + "that many 1/1 red Survivor creature tokens";
    }

    public VarchildBetrayerOfKjeldorEffect(final VarchildBetrayerOfKjeldorEffect effect) {
        super(effect);
    }

    @Override
    public VarchildBetrayerOfKjeldorEffect copy() {
        return new VarchildBetrayerOfKjeldorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = (int) this.getValue("damage");
        if (damage > 0) {
            Effect effect = new CreateTokenTargetEffect(new SurvivorToken(), damage);
            effect.setTargetPointer(getTargetPointer());
            return effect.apply(game, source);
        }
        return false;
    }
}
