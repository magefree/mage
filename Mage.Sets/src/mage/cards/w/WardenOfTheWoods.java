package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.BecomesTargetTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WardenOfTheWoods extends CardImpl {

    public WardenOfTheWoods(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Warden of the Woods becomes the target of a spell or ability an opponent controls, you may draw two cards.
        this.addAbility(new BecomesTargetTriggeredAbility(
                new DrawCardSourceControllerEffect(2),
                StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS,
                SetTargetPointer.NONE, true
        ).setTriggerPhrase("Whenever {this} becomes the target of a spell or ability an opponent controls, "));
    }

    private WardenOfTheWoods(final WardenOfTheWoods card) {
        super(card);
    }

    @Override
    public WardenOfTheWoods copy() {
        return new WardenOfTheWoods(this);
    }
}
