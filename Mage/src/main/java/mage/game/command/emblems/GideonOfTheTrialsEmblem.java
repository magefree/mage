package mage.game.command.emblems;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.common.continuous.CantLoseGameSourceControllerEffect;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.game.command.Emblem;

/**
 * @author spjspj
 */
public final class GideonOfTheTrialsEmblem extends Emblem {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterPlaneswalkerPermanent(SubType.GIDEON), true
    );

    public GideonOfTheTrialsEmblem() {
        super("Emblem Gideon");
        this.getAbilities().add(new SimpleStaticAbility(
                Zone.COMMAND,
                new ConditionalContinuousRuleModifyingEffect(new CantLoseGameSourceControllerEffect(), condition)
                        .setText("as long as you control a Gideon planeswalker, you can't lose the game and your opponents can't win the game")
        ));
    }

    private GideonOfTheTrialsEmblem(final GideonOfTheTrialsEmblem card) {
        super(card);
    }

    @Override
    public GideonOfTheTrialsEmblem copy() {
        return new GideonOfTheTrialsEmblem(this);
    }
}
