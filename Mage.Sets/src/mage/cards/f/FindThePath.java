package mage.cards.f;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FindThePath extends CardImpl {

    public FindThePath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Find the Path enters the battlefield, venture into the dungeon.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new VentureIntoTheDungeonEffect()));

        // Enchanted land has "{T}: Add {G}{G}."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new SimpleManaAbility(
                        Zone.BATTLEFIELD, new Mana(ManaType.GREEN, 2), new TapSourceCost()
                ), AttachmentType.AURA
        ).setText("enchanted land has \"{T}: Add {G}{G}.\"")));
    }

    private FindThePath(final FindThePath card) {
        super(card);
    }

    @Override
    public FindThePath copy() {
        return new FindThePath(this);
    }
}
