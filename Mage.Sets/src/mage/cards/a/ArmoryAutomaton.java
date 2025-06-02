package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class ArmoryAutomaton extends CardImpl {

    public ArmoryAutomaton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Armory Automaton enters or attacks, you may attach any number of target Equipment to it.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new ArmoryAutomatonEffect(), true);
        ability.addTarget(new TargetPermanent(0, Integer.MAX_VALUE, StaticFilters.FILTER_PERMANENT_EQUIPMENT));
        this.addAbility(ability);
    }

    private ArmoryAutomaton(final ArmoryAutomaton card) {
        super(card);
    }

    @Override
    public ArmoryAutomaton copy() {
        return new ArmoryAutomaton(this);
    }
}

class ArmoryAutomatonEffect extends OneShotEffect {

    ArmoryAutomatonEffect() {
        super(Outcome.Benefit);
        this.staticText = "attach any number of target Equipment to it";
    }

    private ArmoryAutomatonEffect(final ArmoryAutomatonEffect effect) {
        super(effect);
    }

    @Override
    public ArmoryAutomatonEffect copy() {
        return new ArmoryAutomatonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = source.getSourcePermanentIfItStillExists(game);
        if (creature == null) {
            return false;
        }
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent equipment = game.getPermanent(targetId);
            if (equipment != null) {
                creature.addAttachment(equipment.getId(), source, game);
            }
        }
        return true;
    }
}
