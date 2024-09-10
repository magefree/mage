package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.dynamicvalue.common.AttachedPermanentPowerCount;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.permanent.token.SoldierToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

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
        this.addAbility(new DiesAttachedTriggeredAbility(new CreateTokenEffect(new SoldierToken(), AttachedPermanentPowerCount.instance), "enchanted creature"));
    }

    private MurderInvestigation(final MurderInvestigation card) {
        super(card);
    }

    @Override
    public MurderInvestigation copy() {
        return new MurderInvestigation(this);
    }
}
