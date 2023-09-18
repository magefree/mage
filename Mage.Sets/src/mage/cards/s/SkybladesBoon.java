package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkybladesBoon extends CardImpl {

    public SkybladesBoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +1/+1 and has flying.
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.AURA
        ).setText("and has flying"));
        this.addAbility(ability);

        // {2}{W}: Return Skyblade's Boon to its owner's hand. Activate only if Skyblade's Boon is on the battlefield or in your graveyard.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                Zone.ALL, new ReturnToHandSourceEffect(),
                new ManaCostsImpl<>("{2}{W}"), SkybladesBoonCondition.instance
        ));
    }

    private SkybladesBoon(final SkybladesBoon card) {
        super(card);
    }

    @Override
    public SkybladesBoon copy() {
        return new SkybladesBoon(this);
    }
}

enum SkybladesBoonCondition implements Condition {
    instance;
    private static final List<Zone> zones = Arrays.asList(Zone.BATTLEFIELD, Zone.GRAVEYARD);

    @Override
    public boolean apply(Game game, Ability source) {
        return zones.contains(game.getState().getZone(source.getSourceId()));
    }

    @Override
    public String toString() {
        return "{this} is on the battlefield or in your graveyard";
    }
}
