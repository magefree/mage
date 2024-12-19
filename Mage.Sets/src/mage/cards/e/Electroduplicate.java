package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class Electroduplicate extends CardImpl {

    public Electroduplicate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");
        

        // Create a token that's a copy of target creature you control, except it has haste and "At the beginning of the end step, sacrifice this token."
        Ability[] extraAbilities = new Ability[2];
        extraAbilities[0] = HasteAbility.getInstance();
        extraAbilities[1] = new BeginningOfEndStepTriggeredAbility(
                TargetController.NEXT, new SacrificeSourceEffect(), false
        );
        this.getSpellAbility().addEffect(new CreateTokenCopyTargetEffect().addAdditionalAbilities(extraAbilities)
                .setText("Create a token that's a copy of target creature you control, except it has haste and "
                        + "\"At the beginning of the end step, sacrifice this token.\""));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Flashback {2}{R}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{2}{R}{R}")));

    }

    private Electroduplicate(final Electroduplicate card) {
        super(card);
    }

    @Override
    public Electroduplicate copy() {
        return new Electroduplicate(this);
    }
}
