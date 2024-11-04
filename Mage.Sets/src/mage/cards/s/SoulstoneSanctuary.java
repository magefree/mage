package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class SoulstoneSanctuary extends CardImpl {

    public SoulstoneSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {4}: This land becomes a 3/3 creature with vigilance and all creature types. It's still a land.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BecomesCreatureSourceEffect(new SoulStoneSanctuaryToken(), CardType.LAND, Duration.WhileOnBattlefield)
                        .setText("this land becomes a 3/3 creature with vigilance and all creature types. It's still a land"),
                new GenericManaCost(4)
        ));
    }

    private SoulstoneSanctuary(final SoulstoneSanctuary card) {
        super(card);
    }

    @Override
    public SoulstoneSanctuary copy() {
        return new SoulstoneSanctuary(this);
    }
}

class SoulStoneSanctuaryToken extends TokenImpl {

    public SoulStoneSanctuaryToken() {
        super("", "3/3 creature with vigilance and all creature types");
        cardType.add(CardType.CREATURE);
        subtype.setIsAllCreatureTypes(true);
        power = new MageInt(3);
        toughness = new MageInt(3);

        this.addAbility(VigilanceAbility.getInstance());
    }
    private SoulStoneSanctuaryToken(final SoulStoneSanctuaryToken token) {
        super(token);
    }

    public SoulStoneSanctuaryToken copy() {
        return new SoulStoneSanctuaryToken(this);
    }
}
