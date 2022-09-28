
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class MurderInvestigation extends CardImpl {

    public MurderInvestigation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When enchanted creature dies, create X 1/1 white Soldier creature tokens, where X is its power.
        this.addAbility(new DiesAttachedTriggeredAbility(new CreateTokenEffect(new SoldierToken(), new AttachedPermanentPowerCount()), "enchanted creature"));
    }

    private MurderInvestigation(final MurderInvestigation card) {
        super(card);
    }

    @Override
    public MurderInvestigation copy() {
        return new MurderInvestigation(this);
    }
}

class AttachedPermanentPowerCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent attachmentPermanent = game.getPermanent(sourceAbility.getSourceId());
        if (attachmentPermanent == null) {
            attachmentPermanent = (Permanent) game.getLastKnownInformation(sourceAbility.getSourceId(), Zone.BATTLEFIELD, sourceAbility.getSourceObjectZoneChangeCounter());
        }
        if (attachmentPermanent != null && attachmentPermanent.getAttachedTo() != null) {
            if (effect.getValue("attachedTo") != null) {
                Permanent attached = (Permanent)effect.getValue("attachedTo");
                if (attached != null) {
                    return attached.getPower().getValue();
                }
            }
        }
        return 0;
    }

    @Override
    public AttachedPermanentPowerCount copy() {
        return new AttachedPermanentPowerCount();
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
