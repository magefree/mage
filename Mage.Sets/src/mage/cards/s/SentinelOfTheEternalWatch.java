
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FirstTargetPointer;

/**
 *
 * @author LevelX2
 */
public final class SentinelOfTheEternalWatch extends CardImpl {

    private final UUID originalId;

    public SentinelOfTheEternalWatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // At the beginning of combat on each opponent's turn, tap target creature that player controls.
        Ability ability = new BeginningOfCombatTriggeredAbility(Zone.BATTLEFIELD, new TapTargetEffect("target creature that player controls"), TargetController.OPPONENT, false, true);
        originalId = ability.getOriginalId();
        this.addAbility(ability);

    }

    public SentinelOfTheEternalWatch(final SentinelOfTheEternalWatch card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public SentinelOfTheEternalWatch copy() {
        return new SentinelOfTheEternalWatch(this);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            for (Effect effect : ability.getEffects()) {
                UUID opponentId = effect.getTargetPointer().getFirst(game, ability);
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    effect.setTargetPointer(new FirstTargetPointer());
                    FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature from the active opponent");
                    filter.add(new ControllerIdPredicate(opponentId));
                    Target target = new TargetCreaturePermanent(filter);
                    ability.addTarget(target);
                }
            }
        }
    }

}
