package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MysticSkull extends CardImpl {

    public MysticSkull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.secondSideCardClazz = mage.cards.m.MysticMonstrosity.class;

        // {1}, {T}: Add one mana of any color.
        Ability ability = new AnyColorManaAbility(new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {5}, {T}: Transform Mystic Skull.
        this.addAbility(new TransformAbility());
        ability = new SimpleActivatedAbility(new TransformSourceEffect(), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private MysticSkull(final MysticSkull card) {
        super(card);
    }

    @Override
    public MysticSkull copy() {
        return new MysticSkull(this);
    }
}
