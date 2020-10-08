package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MalakirBloodPriest extends CardImpl {

    public MalakirBloodPriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Malakir Blood-Priest enters the battlefield, each opponent loses X life and you gain X life, where X is your number of creatures in your party.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new LoseLifeOpponentsEffect(PartyCount.instance).setText("each opponent loses X life")
        );
        ability.addEffect(new GainLifeEffect(
                PartyCount.instance, "and you gain X life, " +
                "where X is the number of creatures in your party. "
                + PartyCount.getReminder()
        ));
        this.addAbility(ability.addHint(PartyCountHint.instance));
    }

    private MalakirBloodPriest(final MalakirBloodPriest card) {
        super(card);
    }

    @Override
    public MalakirBloodPriest copy() {
        return new MalakirBloodPriest(this);
    }
}
