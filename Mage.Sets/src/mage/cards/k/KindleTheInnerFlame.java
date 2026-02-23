package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.costs.common.BeholdCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KindleTheInnerFlame extends CardImpl {

    public KindleTheInnerFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.KINDRED, CardType.SORCERY}, "{3}{R}");

        this.subtype.add(SubType.ELEMENTAL);

        // Create a token that's a copy of target creature you control, except it has haste and "At the beginning of the end step, sacrifice this token."
        this.getSpellAbility().addEffect(new CreateTokenCopyTargetEffect().addAdditionalAbilities(
                HasteAbility.getInstance(),
                new BeginningOfEndStepTriggeredAbility(
                        TargetController.NEXT, new SacrificeSourceEffect(), false
                )
        ).setText("create a token that's a copy of target creature you control, " +
                "except it has haste and \"At the beginning of the end step, sacrifice this token.\""));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Flashback--{1}{R}, Behold three Elementals.
        Ability ability = new FlashbackAbility(this, new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new BeholdCost(SubType.ELEMENTAL, 3));
        this.addAbility(ability);
    }

    private KindleTheInnerFlame(final KindleTheInnerFlame card) {
        super(card);
    }

    @Override
    public KindleTheInnerFlame copy() {
        return new KindleTheInnerFlame(this);
    }
}
