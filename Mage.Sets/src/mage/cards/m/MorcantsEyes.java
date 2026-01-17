package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.permanent.token.BlackGreenElfToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MorcantsEyes extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(new FilterCard(SubType.ELF, "Elf cards"), null);
    private static final Hint hint = new ValueHint("Elf cards in your graveyard", xValue);

    public MorcantsEyes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.KINDRED, CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.ELF);

        // At the beginning of your upkeep, surveil 1.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SurveilEffect(1)));

        // {4}{G}{G}, Sacrifice this enchantment: Create X 2/2 black and green Elf creature tokens, where X is the number of Elf cards in your graveyard. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new CreateTokenEffect(new BlackGreenElfToken(), xValue), new ManaCostsImpl<>("{4}{G}{G}")
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability.addHint(hint));
    }

    private MorcantsEyes(final MorcantsEyes card) {
        super(card);
    }

    @Override
    public MorcantsEyes copy() {
        return new MorcantsEyes(this);
    }
}
