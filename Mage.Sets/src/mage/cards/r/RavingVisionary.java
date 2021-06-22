package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RavingVisionary extends CardImpl {

    public RavingVisionary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {U}, {T}: Draw a card, then discard a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawDiscardControllerEffect(1, 1), new ManaCostsImpl<>("{U}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Delirium — {2}{U}, {T}: Draw a card. Activate only if there are four or more card types among cards in your graveyard.
        ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1),
                new ManaCostsImpl<>("{2}{U}"), DeliriumCondition.instance
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.addHint(CardTypesInGraveyardHint.YOU).setAbilityWord(AbilityWord.DELIRIUM));
    }

    private RavingVisionary(final RavingVisionary card) {
        super(card);
    }

    @Override
    public RavingVisionary copy() {
        return new RavingVisionary(this);
    }
}
