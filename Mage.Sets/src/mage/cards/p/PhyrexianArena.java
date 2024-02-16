package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author Loki
 */
public final class PhyrexianArena extends CardImpl {

    public PhyrexianArena(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");

        // At the beginning of your upkeep, you draw a card and you lose 1 life.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new DrawCardSourceControllerEffect(1, "you"), TargetController.YOU, false
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private PhyrexianArena(final PhyrexianArena card) {
        super(card);
    }

    @Override
    public PhyrexianArena copy() {
        return new PhyrexianArena(this);
    }
}
