
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.RemoveFromCombatSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.SpaceflightAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Styxo
 */
public final class AWing extends CardImpl {

    public AWing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.STARSHIP);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());

        // {1}:Remove A-wing from combat. It must attack on your next combat if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RemoveFromCombatSourceEffect(), new GenericManaCost(1));
        ability.addEffect(new AWingAttacksNextCombatIfAbleSourceEffect());
        this.addAbility(ability);
    }

    private AWing(final AWing card) {
        super(card);
    }

    @Override
    public AWing copy() {
        return new AWing(this);
    }
}

class AWingAttacksNextCombatIfAbleSourceEffect extends RequirementEffect {

    int turnNumber;
    int phaseCount;
    int nextPhaseTurnNumber = 0;
    int nextPhasePhaseCount = 0;

    public AWingAttacksNextCombatIfAbleSourceEffect() {
        super(Duration.Custom);
        staticText = "It must attack on your next combat if able";
    }

    private AWingAttacksNextCombatIfAbleSourceEffect(final AWingAttacksNextCombatIfAbleSourceEffect effect) {
        super(effect);
        this.turnNumber = effect.turnNumber;
        this.phaseCount = effect.phaseCount;
        this.nextPhaseTurnNumber = effect.nextPhaseTurnNumber;
        this.nextPhasePhaseCount = effect.nextPhasePhaseCount;
    }

    @Override
    public void init(Ability source, Game game) {
        turnNumber = game.getTurnNum();
        phaseCount = game.getPhase().getCount();
    }

    @Override
    public AWingAttacksNextCombatIfAbleSourceEffect copy() {
        return new AWingAttacksNextCombatIfAbleSourceEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            if (game.getTurnNum() != turnNumber || game.getPhase().getCount() != phaseCount) {
                if (nextPhaseTurnNumber == 0) {
                    nextPhasePhaseCount = game.getPhase().getCount();
                    nextPhaseTurnNumber = game.getTurnNum();
                } else if (game.getTurnNum() != nextPhaseTurnNumber || game.getPhase().getCount() != nextPhasePhaseCount) {
                    this.discard();
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

}
