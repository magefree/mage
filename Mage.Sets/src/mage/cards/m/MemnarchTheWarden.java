package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.MyrToken;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class MemnarchTheWarden extends CardImpl {

    public MemnarchTheWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{10}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(8);
        this.toughness = new MageInt(9);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // When Memnarch enters, create two 1/1 colorless Myr artifact creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MyrToken(), 2)));

        // Whenever Memnarch attacks, draw a card for each artifact you control.
        this.addAbility(new AttacksTriggeredAbility(
            new DrawCardSourceControllerEffect(ArtifactYouControlCount.instance)
        ).addHint(ArtifactYouControlHint.instance));

    }

    private MemnarchTheWarden(final MemnarchTheWarden card) {
        super(card);
    }

    @Override
    public MemnarchTheWarden copy() {
        return new MemnarchTheWarden(this);
    }
}
