package mage.cards.x;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.*;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.LoseLifePermanentControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponent;



/**
 *
 * @author jesusjbr
 */

public final class XantchaSleeperAgent extends CardImpl {



    public XantchaSleeperAgent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // As Xantcha, Sleeper Agent enters the battlefield, an opponent of your choice gains control of it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new XantchaSleeperAgentChangeControlEffect()) ;
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Xantcha attacks each combat if able and can’t attack its owner or planeswalkers its owner controls.
        ability = new AttacksEachCombatStaticAbility();
        Effect effect = new XantchaSleeperAgentAttackRestrictionEffect();
        ability.addEffect(effect);
        this.addAbility(ability);

        // {3}: Xantcha’s controller loses 2 life and you draw a card. Any player may activate this ability.
        effect = new LoseLifePermanentControllerEffect(2);
        effect.setText("Xantcha’s controller loses 2 life");
        SimpleActivatedAbility simpleAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{3}"));

        simpleAbility.addEffect(new DrawCardSourceControllerEffect(1).setText("and you draw a card"));
        simpleAbility.addEffect(new InfoEffect("Any player may activate this ability"));
        simpleAbility.setMayActivate(TargetController.ANY);
        this.addAbility(simpleAbility);


    }

    public XantchaSleeperAgent(final XantchaSleeperAgent card) {
        super(card);
    }

    @Override
    public XantchaSleeperAgent copy() {
        return new XantchaSleeperAgent(this);
    }
}



class XantchaSleeperAgentChangeControlEffect extends ContinuousEffectImpl {

    public XantchaSleeperAgentChangeControlEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "an opponent of your choice gains control of it";
    }

    public XantchaSleeperAgentChangeControlEffect(final XantchaSleeperAgentChangeControlEffect effect) {
        super(effect);
    }

    @Override
    public XantchaSleeperAgentChangeControlEffect copy() {
        return new XantchaSleeperAgentChangeControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) source.getSourceObjectIfItStillExists(game);
        if (permanent != null) {
            return permanent.changeControllerId(source.getFirstTarget(), game);
        } else {
            discard();
        }
        return false;
    }
}

class XantchaSleeperAgentAttackRestrictionEffect extends RestrictionEffect {

    XantchaSleeperAgentAttackRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "and can't attack its owner or planeswalkers its owner controls.";
    }

    XantchaSleeperAgentAttackRestrictionEffect(final XantchaSleeperAgentAttackRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public XantchaSleeperAgentAttackRestrictionEffect copy() {
        return new XantchaSleeperAgentAttackRestrictionEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return Objects.equals(permanent.getId(), source.getSourceId());
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game) {

        boolean allowAttack = true;
        UUID ownerPlayerId = source.getSourcePermanentIfItStillExists(game).getOwnerId();

        if (defenderId.equals(ownerPlayerId)) {
            allowAttack = false;
        }
        else {
            Permanent planeswalker = game.getPermanent(defenderId);
            if (planeswalker != null && planeswalker.isControlledBy(ownerPlayerId)) {
                allowAttack = false;
            }
        }
        return allowAttack;
    }
}
