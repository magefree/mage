package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayFromTopOfLibraryEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandCard;
import mage.game.permanent.token.ZabuToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class KaZarOfTheSavageLand extends CardImpl {

    private static final FilterCard filter = new FilterLandCard("play lands");

    public KaZarOfTheSavageLand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARBARIAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may play lands from the top of your library.
        this.addAbility(new SimpleStaticAbility(new PlayFromTopOfLibraryEffect(filter)));

        // When Ka-Zar enters, create Zabu, a legendary 2/2 green Cat creature token with "Landfall -- Whenever a land you control enters, put a +1/+1 counter on Zabu."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ZabuToken())));
    }

    private KaZarOfTheSavageLand(final KaZarOfTheSavageLand card) {
        super(card);
    }

    @Override
    public KaZarOfTheSavageLand copy() {
        return new KaZarOfTheSavageLand(this);
    }
}
