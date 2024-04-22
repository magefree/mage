package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;
import mage.target.TargetPermanent;

/**
 * @author spjspj
 */
public final class TezzeretTheSchemerEmblem extends Emblem {

    public TezzeretTheSchemerEmblem() {
        super("Emblem Tezzeret");

        Effect effect = new AddCardTypeTargetEffect(Duration.EndOfGame, CardType.ARTIFACT, CardType.CREATURE);
        effect.setText("target artifact you control becomes an artifact creature");
        Ability ability = new BeginningOfCombatTriggeredAbility(Zone.COMMAND, effect, TargetController.YOU, false, true);
        effect = new SetBasePowerToughnessTargetEffect(5, 5, Duration.EndOfGame);
        effect.setText("with base power and toughness 5/5");
        ability.addEffect(effect);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT));
        this.getAbilities().add(ability);
    }

    private TezzeretTheSchemerEmblem(final TezzeretTheSchemerEmblem card) {
        super(card);
    }

    @Override
    public TezzeretTheSchemerEmblem copy() {
        return new TezzeretTheSchemerEmblem(this);
    }
}
