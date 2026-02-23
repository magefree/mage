package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.MutagenToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GenghisFrog extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.MUTANT);

    public GenghisFrog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Genghis Frog or another Mutant you control enters, create a Mutagen token.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new CreateTokenEffect(new MutagenToken()), filter, false, true
        ));
    }

    private GenghisFrog(final GenghisFrog card) {
        super(card);
    }

    @Override
    public GenghisFrog copy() {
        return new GenghisFrog(this);
    }
}
