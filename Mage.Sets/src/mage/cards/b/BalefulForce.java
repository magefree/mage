package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BalefulForce extends CardImpl {

    public BalefulForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}{B}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // At the beginning of each upkeep, you draw a card and you lose 1 life.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new DrawCardSourceControllerEffect(1, "you"), TargetController.ANY, false);
        Effect effect = new LoseLifeSourceControllerEffect(1);
        ability.addEffect(effect.concatBy("and"));
        this.addAbility(ability);
    }

    private BalefulForce(final BalefulForce card) {
        super(card);
    }

    @Override
    public BalefulForce copy() {
        return new BalefulForce(this);
    }
}
