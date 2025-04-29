package mage.cards.c;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreatureOrPlaneswalkerAmount;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class ChandraFlameshaper extends CardImpl {

    public ChandraFlameshaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{5}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);
        this.setStartingLoyalty(6);

        // +2: Add {R}{R}{R}. Exile the top three cards of your library. Choose one. You may play that card this turn.
        Ability plusTwoAbility = new LoyaltyAbility(new BasicManaEffect(Mana.RedMana(3)), 2);
        plusTwoAbility.addEffect(new ExileTopXMayPlayUntilEffect(3, true, Duration.EndOfTurn));
        this.addAbility(plusTwoAbility);

        // +1: Create a token that's a copy of target creature you control, except it has haste and "At the beginning of the end step, sacrifice this token."
        Ability plusOneAbility = new LoyaltyAbility(
                new CreateTokenCopyTargetEffect()
                        .addAdditionalAbilities(
                                HasteAbility.getInstance(),
                                new BeginningOfEndStepTriggeredAbility(
                                        TargetController.NEXT, new SacrificeSourceEffect(), false
                                )).setText("create a token that's a copy of target creature you control, " +
                                "except it has haste and \"At the beginning of the end step, sacrifice this token.\""),
                1
        );
        plusOneAbility.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(plusOneAbility);

        // -4: Chandra deals 8 damage divided as you choose among any number of target creatures and/or planeswalkers.
        Ability minusFourAbility = new LoyaltyAbility(
                new DamageMultiEffect(), -4
        );
        minusFourAbility.addTarget(new TargetCreatureOrPlaneswalkerAmount(8));
        this.addAbility(minusFourAbility);
    }

    private ChandraFlameshaper(final ChandraFlameshaper card) {
        super(card);
    }

    @Override
    public ChandraFlameshaper copy() {
        return new ChandraFlameshaper(this);
    }
}
