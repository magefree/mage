package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CuriousCadaver extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.CLUE, "a Clue");

    public CuriousCadaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When you sacrifice a Clue, return Curious Cadaver from your graveyard to your hand.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), filter,
                TargetController.YOU, SetTargetPointer.NONE, false
        ));
    }

    private CuriousCadaver(final CuriousCadaver card) {
        super(card);
    }

    @Override
    public CuriousCadaver copy() {
        return new CuriousCadaver(this);
    }
}
