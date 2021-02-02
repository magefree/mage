package mage.cards.a;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorceryCard;

import java.util.UUID;

/**
 * @author noxx
 */
public final class ArcaneMelee extends CardImpl {

    public ArcaneMelee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}");

        // Instant and sorcery spells cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new SpellsCostReductionAllEffect(new FilterInstantOrSorceryCard("Instant and sorcery spells"), 2))
        );
    }

    private ArcaneMelee(final ArcaneMelee card) {
        super(card);
    }

    @Override
    public ArcaneMelee copy() {
        return new ArcaneMelee(this);
    }
}
