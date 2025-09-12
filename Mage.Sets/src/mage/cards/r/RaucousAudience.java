package mage.cards.r;

import mage.MageInt;
import mage.Mana;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalManaEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RaucousAudience extends CardImpl {

    public RaucousAudience(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {T}: Add {G}. If you control a creature with power 4 or greater, add {G}{G} instead.
        this.addAbility(new SimpleManaAbility(new ConditionalManaEffect(
                new BasicManaEffect(Mana.GreenMana(2)), new BasicManaEffect(Mana.GreenMana(1)),
                FerociousCondition.instance, "Add {G}. If you control a creature with power 4 or greater, add {G}{G} instead."
        ), new TapSourceCost()).addHint(FerociousHint.instance));
    }

    private RaucousAudience(final RaucousAudience card) {
        super(card);
    }

    @Override
    public RaucousAudience copy() {
        return new RaucousAudience(this);
    }
}
