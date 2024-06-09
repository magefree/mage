package mage.cards.x;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.LoseLifePermanentControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;
import java.util.UUID;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.EntersBattlefieldUnderControlOfOpponentOfChoiceEffect;


/**
 * @author jesusjbr
 */
public final class XantchaSleeperAgent extends CardImpl {

    public XantchaSleeperAgent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Xantcha, Sleeper Agent enters the battlefield under the control of an opponent of your choice.
        /** 
         * Xantcha’s first ability is a replacement effect that modifies how it enters the battlefield, 
         * not a triggered ability. Players can’t take actions (such as activating its last ability) 
         * while Xantcha’s on the battlefield before it’s controlled by another player. (2018-07-13)
         * */
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldUnderControlOfOpponentOfChoiceEffect()));
        
        // Xantcha attacks each combat if able and can’t attack its owner or planeswalkers its owner controls.
        Ability ability = new AttacksEachCombatStaticAbility();
        ability.addEffect(new XantchaSleeperAgentAttackRestrictionEffect());
        this.addAbility(ability);

        // {3}: Xantcha’s controller loses 2 life and you draw a card. Any player may activate this ability.
        SimpleActivatedAbility simpleAbility = new SimpleActivatedAbility(
                new LoseLifePermanentControllerEffect(2)
                        .setText("{this}'s controller loses 2 life"),
                new GenericManaCost(3)
        );
        simpleAbility.addEffect(new DrawCardSourceControllerEffect(1).setText("and you draw a card"));
        simpleAbility.addEffect(new InfoEffect("Any player may activate this ability"));
        simpleAbility.setMayActivate(TargetController.ANY);
        this.addAbility(simpleAbility);
    }

    private XantchaSleeperAgent(final XantchaSleeperAgent card) {
        super(card);
    }

    @Override
    public XantchaSleeperAgent copy() {
        return new XantchaSleeperAgent(this);
    }
}

class XantchaSleeperAgentAttackRestrictionEffect extends RestrictionEffect {

    XantchaSleeperAgentAttackRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "and can't attack its owner or planeswalkers its owner controls.";
    }

    private XantchaSleeperAgentAttackRestrictionEffect(final XantchaSleeperAgentAttackRestrictionEffect effect) {
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
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null) {
            return true;
        }

        boolean allowAttack = true;
        UUID ownerPlayerId = source.getSourcePermanentIfItStillExists(game).getOwnerId();

        if (defenderId.equals(ownerPlayerId)
                && game.getPlayers().size() == 2) { // if only 2 players are left, it can't attack at all.
            allowAttack = false;
        }
        if (defenderId.equals(ownerPlayerId)) { // can't attack owner
            allowAttack = false;
        }
        Permanent planeswalker = game.getPermanent(defenderId);
        if (planeswalker != null && planeswalker.isControlledBy(ownerPlayerId)) {  // can't attack the owner's planeswalkers
            allowAttack = false;
        }

        return allowAttack;
    }
}
