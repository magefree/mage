
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author anonymous
 */
public final class MindwhipSliver extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("All Slivers");

    static {
        filter.add(SubType.SLIVER.getPredicate());
    }

    public MindwhipSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // All Slivers have "{2}, Sacrifice this permanent: Target player discards a card at random. Activate this ability only any time you could cast a sorcery."
        Ability gainedAbility = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new DiscardTargetEffect(1, true), new GenericManaCost(2));
        gainedAbility.addCost(new SacrificeSourceCost());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(gainedAbility, Duration.WhileOnBattlefield, filter,
                        "All Slivers have \"{2}, Sacrifice this permanent: Target player discards a card at random. Activate only as a sorcery.\"")));
    }

    private MindwhipSliver(final MindwhipSliver card) {
        super(card);
    }

    @Override
    public MindwhipSliver copy() {
        return new MindwhipSliver(this);
    }
}
