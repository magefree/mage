package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MysteriousTome extends CardImpl {

    public MysteriousTome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        this.transformable = true;
        this.secondSideCardClazz = mage.cards.c.ChillingChronicle.class;

        // {2}, {T}: Draw a card. Transform Mysterious Tome.
        this.addAbility(new TransformAbility());
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new TransformSourceEffect(true));
        this.addAbility(ability);
    }

    private MysteriousTome(final MysteriousTome card) {
        super(card);
    }

    @Override
    public MysteriousTome copy() {
        return new MysteriousTome(this);
    }
}
