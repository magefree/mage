
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.CatSoldierCreatureToken;

/**
 *
 * @author LevelX2
 */
public final class VanguardOfBrimaz extends CardImpl {

    public VanguardOfBrimaz(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // <i>Heroic</i> &mdash; Whenever you cast a spell that targets Vanguard of Brimaz, create a 1/1 white Cat Soldier creature token with vigilance.
        this.addAbility(new HeroicAbility(new CreateTokenEffect(new CatSoldierCreatureToken()), false));
    }

    private VanguardOfBrimaz(final VanguardOfBrimaz card) {
        super(card);
    }

    @Override
    public VanguardOfBrimaz copy() {
        return new VanguardOfBrimaz(this);
    }
}
