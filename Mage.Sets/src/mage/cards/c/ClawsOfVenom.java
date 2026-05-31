package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenAttachSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.permanent.token.ArtifactShapeshifterToken;
import java.util.UUID;

/**
 * @author ChesseTheWasp
 */
public final class ClawsOfVenom extends CardImpl {

    public ClawsOfVenom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}");

        this.subtype.add(SubType.EQUIPMENT);

        // Create a 0/1 Artifact Shapeshifter and attach to it
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenAttachSourceEffect(new ArtifactShapeshifterToken())
        ));

        // Equipped creature gets +1/+1
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        this.addAbility(ability);

        ability.addEffect(new GainAbilityAttachedEffect(
                DeathtouchAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has deathtouch"));

        // Equip {2}{G}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{2}{G}"), false));
    }

    private ClawsOfVenom(final ClawsOfVenom card) {
        super(card);
    }

    @Override
    public ClawsOfVenom copy() {
        return new ClawsOfVenom(this);
    }
}
