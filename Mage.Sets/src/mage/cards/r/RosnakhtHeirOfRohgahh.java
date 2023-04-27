package mage.cards.r;

import mage.MageInt;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.BattleCryAbility;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.KherKeepKoboldToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RosnakhtHeirOfRohgahh extends CardImpl {

    public RosnakhtHeirOfRohgahh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KOBOLD);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Battle cry
        this.addAbility(new BattleCryAbility());

        // Heroic -- Whenever you cast a spell that targets Rosnakht, Heir of Rohgahh, create a 0/1 red Kobold creature token named Kobolds of Kher Keep.
        this.addAbility(new HeroicAbility(new CreateTokenEffect(new KherKeepKoboldToken())));
    }

    private RosnakhtHeirOfRohgahh(final RosnakhtHeirOfRohgahh card) {
        super(card);
    }

    @Override
    public RosnakhtHeirOfRohgahh copy() {
        return new RosnakhtHeirOfRohgahh(this);
    }
}
