
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;

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
        DynamicValue attachedPower = new DyingWishAttachedPermanentPowerCount();
        ability = new DiesAttachedTriggeredAbility(new LoseLifeTargetEffect(attachedPower), "enchanted creature");
        ability.addEffect(new GainLifeEffect(attachedPower));
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

class DyingWishAttachedPermanentPowerCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent attachmentPermanent = game.getPermanent(sourceAbility.getSourceId());
        if (attachmentPermanent == null) {
            attachmentPermanent = (Permanent) game.getLastKnownInformation(sourceAbility.getSourceId(), Zone.BATTLEFIELD, sourceAbility.getSourceObjectZoneChangeCounter());
        }
        if (attachmentPermanent != null && attachmentPermanent.getAttachedTo() != null) {
            if (effect.getValue("attachedTo") != null) {
                Permanent attached = (Permanent) effect.getValue("attachedTo");
                if (attached != null) {
                    return attached.getPower().getValue();
                }
            }
        }
        return 0;
    }

    @Override
    public DyingWishAttachedPermanentPowerCount copy() {
        return new DyingWishAttachedPermanentPowerCount();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "its power";
    }
}
