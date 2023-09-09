
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.TokenImpl;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class Hydroform extends CardImpl {

    public Hydroform(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}{U}");


        // Target land becomes a 3/3 Elemental creature with flying until end of turn. It's still a land.
        this.getSpellAbility().addEffect(new BecomesCreatureTargetEffect(new HydroformToken(), false, true, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    private Hydroform(final Hydroform card) {
        super(card);
    }

    @Override
    public Hydroform copy() {
        return new Hydroform(this);
    }
}

class HydroformToken extends TokenImpl {

    public HydroformToken() {
        super("", "3/3 Elemental creature with flying");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());
    }
    private HydroformToken(final HydroformToken token) {
        super(token);
    }

    public HydroformToken copy() {
        return new HydroformToken(this);
    }
}
