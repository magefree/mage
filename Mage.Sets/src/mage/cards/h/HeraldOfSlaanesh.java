package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeraldOfSlaanesh extends CardImpl {

    private static final FilterCard filter = new FilterCard("Demon spells");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.DEMON, "Demons");

    static {
        filter.add(SubType.DEMON.getPredicate());
    }

    public HeraldOfSlaanesh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Locus of Slaanesh -- Demon spells you cast cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(
                new SpellsCostReductionControllerEffect(filter, 2)
        ).withFlavorWord("Locus of Slaanesh"));

        // Other Demons you control have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter2, true
        )));
    }

    private HeraldOfSlaanesh(final HeraldOfSlaanesh card) {
        super(card);
    }

    @Override
    public HeraldOfSlaanesh copy() {
        return new HeraldOfSlaanesh(this);
    }
}
