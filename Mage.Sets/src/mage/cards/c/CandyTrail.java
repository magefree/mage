package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CandyTrail extends CardImpl {

    public CandyTrail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.FOOD);
        this.subtype.add(SubType.CLUE);

        // When Candy Trail enters the battlefield, scry 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(2, false)));

        // {2}, {T}, Sacrifice Candy Trail: You gain 3 life and draw a card.
        Ability ability = new SimpleActivatedAbility(new GainLifeEffect(3), new GenericManaCost(2));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private CandyTrail(final CandyTrail card) {
        super(card);
    }

    @Override
    public CandyTrail copy() {
        return new CandyTrail(this);
    }
}
