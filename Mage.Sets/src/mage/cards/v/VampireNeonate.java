package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class VampireNeonate extends CardImpl {

    public VampireNeonate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // {2}, {T}: Each opponent loses 1 life and you gain 1 life.
        Ability ability = new SimpleActivatedAbility(
                new LoseLifeOpponentsEffect(1),
                new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new GainLifeEffect(1).setText("and you gain 1 life"));
        this.addAbility(ability);
    }

    private VampireNeonate(final VampireNeonate card) {
        super(card);
    }

    @Override
    public VampireNeonate copy() {
        return new VampireNeonate(this);
    }
}
