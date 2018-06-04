
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageSourceEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author TheElk801
 */
public final class KukemssaPirates extends CardImpl {

    private final UUID originalId;

    public KukemssaPirates(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Kukemssa Pirates attacks and isn't blocked, you may gain control of target artifact defending player controls. If you do, Kukemssa Pirates assigns no combat damage this turn.
        Ability ability = new AttacksAndIsNotBlockedTriggeredAbility(new GainControlTargetEffect(Duration.Custom), true);
        ability.addEffect(new AssignNoCombatDamageSourceEffect(Duration.EndOfTurn, true));
        ability.addTarget(new TargetArtifactPermanent(new FilterArtifactPermanent("artifact defending player controls")));
        originalId = ability.getOriginalId();
        this.addAbility(ability);
    }

    public KukemssaPirates(final KukemssaPirates card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            ability.getTargets().clear();
            FilterArtifactPermanent filter = new FilterArtifactPermanent("artifact defending player controls");
            UUID defenderId = game.getCombat().getDefenderId(ability.getSourceId());
            filter.add(new ControllerIdPredicate(defenderId));
            TargetArtifactPermanent target = new TargetArtifactPermanent(filter);
            ability.addTarget(target);
        }
    }

    @Override
    public KukemssaPirates copy() {
        return new KukemssaPirates(this);
    }
}
