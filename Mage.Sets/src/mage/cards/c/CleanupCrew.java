package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author weirddan455
 */
public final class CleanupCrew extends CardImpl {

    public CleanupCrew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When Cleanup Crew enters the battlefield, choose one —
        // • Destroy target artifact.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetArtifactPermanent());

        // • Destroy target enchantment.
        ability.addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetEnchantmentPermanent()));

        // • Exile target card from a graveyard.
        ability.addMode(new Mode(new ExileTargetEffect()).addTarget(new TargetCardInGraveyard()));

        // • You gain 4 life.
        ability.addMode(new Mode(new GainLifeEffect(4)));
        this.addAbility(ability);
    }

    private CleanupCrew(final CleanupCrew card) {
        super(card);
    }

    @Override
    public CleanupCrew copy() {
        return new CleanupCrew(this);
    }
}
