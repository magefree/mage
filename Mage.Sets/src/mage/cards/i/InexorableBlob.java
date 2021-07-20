package mage.cards.i;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.InexorableBlobOozeToken;

/**
 * @author fireshoes
 */
public final class InexorableBlob extends CardImpl {

    public InexorableBlob(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // <i>Delirium</i> &mdash; Whenever Inexorable Blob attacks, if there are four or more card types among cards
        // in your graveyard, create a 3/3 green Ooze creature token that’s tapped and attacking.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new InexorableBlobOozeToken(), 1, true, true), false),
                DeliriumCondition.instance,
                "<i>Delirium</i> &mdash; Whenever {this} attacks, if there are four or more card types among cards in your graveyard, " +
                        "create a 3/3 green Ooze creature token that's tapped and attacking.")
                .addHint(CardTypesInGraveyardHint.YOU));
    }

    private InexorableBlob(final InexorableBlob card) {
        super(card);
    }

    @Override
    public InexorableBlob copy() {
        return new InexorableBlob(this);
    }
}
