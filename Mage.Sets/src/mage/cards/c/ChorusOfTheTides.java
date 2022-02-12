
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ChorusOfTheTides extends CardImpl {

    public ChorusOfTheTides(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.SIREN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // <i>Heroic</i> &mdash; Whenever you cast a spell that targets Chorus of the Tides, scry 1.
        this.addAbility(new HeroicAbility(new ScryEffect(1, false)));
    }

    private ChorusOfTheTides(final ChorusOfTheTides card) {
        super(card);
    }

    @Override
    public ChorusOfTheTides copy() {
        return new ChorusOfTheTides(this);
    }
}
