package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FirstTargetPointer;

/**
 * @author emerald000
 */
public final class EliteScaleguard extends CardImpl {

    public EliteScaleguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Elite Scaleguard enters the battlefield, bolster 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BolsterEffect(2)));

        // Whenever a creature you control with a +1/+1 counter on it attacks, tap target creature defending player controls.
        Ability ability = new AttacksCreatureYouControlTriggeredAbility(
                new TapTargetEffect(),
                false,
                StaticFilters.FILTER_CONTROLLED_CREATURE_P1P1,
                true);
        ability.addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature defending player controls")));
        ability.setTargetAdjuster(EliteScaleguardTargetAdjuster.instance);
        this.addAbility(ability);
    }

    private EliteScaleguard(final EliteScaleguard card) {
        super(card);
    }

    @Override
    public EliteScaleguard copy() {
        return new EliteScaleguard(this);
    }
}

enum EliteScaleguardTargetAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        FilterCreaturePermanent filterDefender = new FilterCreaturePermanent("creature defending player controls");
        for (Effect effect : ability.getEffects()) {
            if (effect instanceof TapTargetEffect) {
                filterDefender.add(new ControllerIdPredicate(game.getCombat().getDefendingPlayerId(effect.getTargetPointer().getFirst(game, ability), game)));
                effect.setTargetPointer(new FirstTargetPointer());// reset target pointer to first target to tap correct target
                break;
            }
        }
        ability.getTargets().clear();
        TargetCreaturePermanent target = new TargetCreaturePermanent(filterDefender);
        ability.addTarget(target);
    }
}
