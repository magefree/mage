
package mage.cards.n;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneSourceEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTargets;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.CreatureAttackedWhichPlayerWatcher;

/**
 *
 * @author spjspj
 */
public final class NacatlWarPride extends CardImpl {

    public NacatlWarPride(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}{G}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Nacatl War-Pride must be blocked by exactly one creature if able.
        Ability blockAbility = new SimpleStaticAbility(new MustBeBlockedByAtLeastOneSourceEffect(Duration.WhileOnBattlefield).setText("{this} must be blocked"));
        blockAbility.addEffect(new CantBeBlockedByMoreThanOneSourceEffect().setText(" by exactly one creature if able"));
        this.addAbility(blockAbility);

        // Whenever Nacatl War-Pride attacks, create X tokens that are copies of Nacatl War-Pride tapped and attacking, where X is the number of creatures defending player controls. Exile the tokens at the beginning of the next end step.
        Ability ability = new AttacksTriggeredAbility(new NacatlWarPrideEffect(), false);
        ability.addWatcher(new CreatureAttackedWhichPlayerWatcher());
        this.addAbility(ability);       
        
    }

    private NacatlWarPride(final NacatlWarPride card) {
        super(card);
    }

    @Override
    public NacatlWarPride copy() {
        return new NacatlWarPride(this);
    }
}

class NacatlWarPrideEffect extends OneShotEffect {

    public NacatlWarPrideEffect() {
        super(Outcome.Benefit);
        this.staticText = "create X tokens that are copies of {this} tapped and attacking, where X is the number of creatures defending player controls. Exile the tokens at the beginning of the next end step";
    }

    public NacatlWarPrideEffect(final NacatlWarPrideEffect effect) {
        super(effect);
    }

    @Override
    public NacatlWarPrideEffect copy() {
        return new NacatlWarPrideEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent origNactalWarPride = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (origNactalWarPride == null) {
            return false;
        }
        
        CreatureAttackedWhichPlayerWatcher PlayerAttackedWatcher = game.getState().getWatcher(CreatureAttackedWhichPlayerWatcher.class);

        // Count the number of creatures attacked opponent controls
        UUID defenderId = PlayerAttackedWatcher.getPlayerAttackedThisTurnByCreature(source.getSourceId());        

        int count = 0;
        if (defenderId != null) {
            count = game.getBattlefield().countAll(new FilterControlledCreaturePermanent(), defenderId, game);
        }

        if (count == 0) {
            return false;
        }

        List<Permanent> copies = new ArrayList<>();    
        Player controller = game.getPlayer(source.getControllerId());
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(controller.getId(), null, false, count, true, true);
        effect.setTargetPointer(new FixedTarget(origNactalWarPride, game));
        effect.apply(game, source);
        copies.addAll(effect.getAddedPermanents());
        
        if (!copies.isEmpty()) {
            FixedTargets fixedTargets = new FixedTargets(copies, game);
            ExileTargetEffect exileEffect = new ExileTargetEffect();
            exileEffect.setTargetPointer(fixedTargets).setText("exile the tokens");
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect), source);
            return true;
        }

        return false;
    }
}
