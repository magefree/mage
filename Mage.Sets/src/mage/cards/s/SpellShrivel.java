package mage.cards.s;

import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SpellShrivel extends CardImpl {

    public SpellShrivel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Counter target spell unless its controller pays {4}. If that spell is countered this way, exile it instead of putting it into its owner's graveyard.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(4), true));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private SpellShrivel(final SpellShrivel card) {
        super(card);
    }

    @Override
    public SpellShrivel copy() {
        return new SpellShrivel(this);
    }
}
