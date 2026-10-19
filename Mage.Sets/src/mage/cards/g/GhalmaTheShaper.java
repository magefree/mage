package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.MyrToken;

import java.util.UUID;

public final class GhalmaTheShaper extends CardImpl {

    public GhalmaTheShaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Ghalma the Shaper attacks, conjure a card named Tempered Steel into your hand, then create a 1/1 colorless Myr artifact creature token.
        Ability ability = new AttacksTriggeredAbility(new ConjureCardEffect("Tempered Steel"));
        ability.addEffect(new CreateTokenEffect(new MyrToken()).concatBy(", then "));
        this.addAbility(ability);
    }

    private GhalmaTheShaper(final GhalmaTheShaper card) {
        super(card);
    }

    @Override
    public GhalmaTheShaper copy() {
        return new GhalmaTheShaper(this);
    }
}
