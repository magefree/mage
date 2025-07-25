package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.InexorableBlobOozeToken;

import java.util.UUID;

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
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(
                new InexorableBlobOozeToken(), 1, true, true
        )).withInterveningIf(DeliriumCondition.instance).setAbilityWord(AbilityWord.DELIRIUM).addHint(CardTypesInGraveyardCount.YOU.getHint()));
    }

    private InexorableBlob(final InexorableBlob card) {
        super(card);
    }

    @Override
    public InexorableBlob copy() {
        return new InexorableBlob(this);
    }
}
