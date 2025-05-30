package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.RavenousAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Aberrant extends CardImpl {

    private static final FilterArtifactOrEnchantmentPermanent filter
            = new FilterArtifactOrEnchantmentPermanent("artifact or enchantment that player controls");

    public Aberrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{1}{G}");

        this.subtype.add(SubType.TYRANID);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Ravenous
        this.addAbility(new RavenousAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Heavy Power Hammer -- Whenever Aberrant deals combat damage to a player, destroy target artifact or enchantment that player controls.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DestroyTargetEffect(), false, true);
        ability.withFlavorWord("Heavy Power Hammer");
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster());
        this.addAbility(ability);
    }

    private Aberrant(final Aberrant card) {
        super(card);
    }

    @Override
    public Aberrant copy() {
        return new Aberrant(this);
    }
}
