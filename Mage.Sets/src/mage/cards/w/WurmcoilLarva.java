package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.PhyrexianWurm12DeathtouchToken;
import mage.game.permanent.token.PhyrexianWurm21LifelinkToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class WurmcoilLarva extends CardImpl {

    public WurmcoilLarva(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Wurmcoil Larva dies, create a 1/2 black Phyrexian Wurm artifact creature token with deathtouch and a 2/1 black Phyrexian Wurm artifact creature token with lifelink.
        Ability ability = new DiesSourceTriggeredAbility(new CreateTokenEffect(new PhyrexianWurm12DeathtouchToken()), false);
        ability.addEffect(new CreateTokenEffect(new PhyrexianWurm21LifelinkToken())
                .setText("and a 2/1 black Phyrexian Wurm artifact creature token with lifelink"));
        this.addAbility(ability);
    }

    private WurmcoilLarva(final WurmcoilLarva card) {
        super(card);
    }

    @Override
    public WurmcoilLarva copy() {
        return new WurmcoilLarva(this);
    }
}
