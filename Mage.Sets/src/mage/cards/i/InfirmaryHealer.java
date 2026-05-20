package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InfirmaryHealer extends PrepareCard {

    public InfirmaryHealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}", "Stream of Life", CardType.SORCERY, "{X}{G}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Stream of Life
        // Sorcery {X}{G}
        // Target player gains X life.
        this.getSpellCard().getSpellAbility().addEffect(new GainLifeTargetEffect(GetXValue.instance));
        this.getSpellCard().getSpellAbility().addTarget(new TargetPlayer());
    }

    private InfirmaryHealer(final InfirmaryHealer card) {
        super(card);
    }

    @Override
    public InfirmaryHealer copy() {
        return new InfirmaryHealer(this);
    }
}
