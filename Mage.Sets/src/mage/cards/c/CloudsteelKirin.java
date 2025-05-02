package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CantLoseGameSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReconfigureAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CloudsteelKirin extends CardImpl {

    public CloudsteelKirin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.EQUIPMENT);
        this.subtype.add(SubType.KIRIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Equipped creature has flying and "You can't lose the game and your opponents can't win the game."
        Ability ability = new SimpleStaticAbility(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.EQUIPMENT
        ));
        ability.addEffect(new GainAbilityAttachedEffect(
                new SimpleStaticAbility(new CantLoseGameSourceControllerEffect()), AttachmentType.EQUIPMENT
        ).setText("and \"You can't lose the game and your opponents can't win the game.\""));
        this.addAbility(ability);

        // Reconfigure {5}
        this.addAbility(new ReconfigureAbility("{5}"));
    }

    private CloudsteelKirin(final CloudsteelKirin card) {
        super(card);
    }

    @Override
    public CloudsteelKirin copy() {
        return new CloudsteelKirin(this);
    }
}
