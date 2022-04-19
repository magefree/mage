package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.GiveManaAbilityAndCastSourceAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShatteredSeraph extends CardImpl {

    public ShatteredSeraph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{U}{B}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Shattered Seraph enters the battlefield, you gain 3 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3)));

        // {2}, Exile Shattered Seraph from your hand: Target land gains "{T}: Add {W}, {U}, or {B}" until Shattered Seraph is cast from exile. You may cast Shattered Seraph for as long as it remains exiled.
        this.addAbility(new GiveManaAbilityAndCastSourceAbility("WUB"));
    }

    private ShatteredSeraph(final ShatteredSeraph card) {
        super(card);
    }

    @Override
    public ShatteredSeraph copy() {
        return new ShatteredSeraph(this);
    }
}
