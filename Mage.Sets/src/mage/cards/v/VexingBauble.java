package mage.cards.v;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastNoManaSpentTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author grimreap124
 */
public final class VexingBauble extends CardImpl {

    public VexingBauble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.ARTIFACT }, "{1}");

        // Whenever a player casts a spell, if no mana was spent to cast it, counter that spell.
        this.addAbility(new SpellCastNoManaSpentTriggeredAbility(
                new CounterTargetEffect().setText("counter that spell")));

        // {1}, {T}, Sacrifice Vexing Bauble: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1),
                new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
        ;
    }

    private VexingBauble(final VexingBauble card) {
        super(card);
    }

    @Override
    public VexingBauble copy() {
        return new VexingBauble(this);
    }
}