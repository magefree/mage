package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.dynamicvalue.common.AttachedPermanentPowerCount;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class DyingWish extends CardImpl {

    public DyingWish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When enchanted creature dies, target player loses X life and you gain X life, where X is its power.
        ability = new DiesAttachedTriggeredAbility(new LoseLifeTargetEffect(AttachedPermanentPowerCount.instance)
                .setText("target player loses X life"), "enchanted creature");
        ability.addEffect(new GainLifeEffect(AttachedPermanentPowerCount.instance)
                .setText("and you gain X life, where X is its power"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private DyingWish(final DyingWish card) {
        super(card);
    }

    @Override
    public DyingWish copy() {
        return new DyingWish(this);
    }
}
