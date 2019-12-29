package mage.cards.g;

import mage.MageInt;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GnarlbackRhino extends CardImpl {

    public GnarlbackRhino(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.RHINO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you cast a spell that targets Gnarlback Rhino, draw a card.
        this.addAbility(new HeroicAbility(new DrawCardSourceControllerEffect(1), false, false));
    }

    private GnarlbackRhino(final GnarlbackRhino card) {
        super(card);
    }

    @Override
    public GnarlbackRhino copy() {
        return new GnarlbackRhino(this);
    }
}
