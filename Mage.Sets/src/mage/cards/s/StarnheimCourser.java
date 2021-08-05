package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactOrEnchantmentCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StarnheimCourser extends CardImpl {

    private static final FilterCard filter = new FilterArtifactOrEnchantmentCard("artifact and enchantment spells");

    public StarnheimCourser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.PEGASUS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Artifact and enchantment spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));
    }

    private StarnheimCourser(final StarnheimCourser card) {
        super(card);
    }

    @Override
    public StarnheimCourser copy() {
        return new StarnheimCourser(this);
    }
}
