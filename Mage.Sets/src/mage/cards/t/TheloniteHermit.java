
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author LevelX2
 */
public final class TheloniteHermit extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("All Saprolings");

    static {
        filter.add(SubType.SAPROLING.getPredicate());
    }

    public TheloniteHermit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Saproling creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false)));

        // Morph {3}{G}{G}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{3}{G}{G}")));

        // When Thelonite Hermit is turned face up, create four 1/1 green Saproling creature tokens.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new CreateTokenEffect(new SaprolingToken(), 4)));
    }

    private TheloniteHermit(final TheloniteHermit card) {
        super(card);
    }

    @Override
    public TheloniteHermit copy() {
        return new TheloniteHermit(this);
    }
}
