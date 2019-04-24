
package mage.cards.g;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public final class GoblinRacketeer extends CardImpl {

    private final UUID originalId;

    public GoblinRacketeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Whenever Goblin Racketeer attacks, you may goad target creature defending player controls.
        Ability ability = new AttacksTriggeredAbility(new GoadTargetEffect(), true, "Whenever {this} attacks, you may goad target creature defending player controls");
        ability.addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature defending player controls")));
        originalId = ability.getOriginalId();
        this.addAbility(ability);
    }

    public GoblinRacketeer(final GoblinRacketeer card) {
        super(card);
        this.originalId = card.originalId;

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            ability.getTargets().clear();
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");
            UUID defenderId = game.getCombat().getDefenderId(ability.getSourceId());
            filter.add(new ControllerIdPredicate(defenderId));
            TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
            ability.addTarget(target);
        }
    }

    @Override
    public GoblinRacketeer copy() {
        return new GoblinRacketeer(this);
    }
}
