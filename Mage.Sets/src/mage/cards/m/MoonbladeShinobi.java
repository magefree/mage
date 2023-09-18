package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.MelokuTheCloudedMirrorToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoonbladeShinobi extends CardImpl {

    public MoonbladeShinobi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Ninjutsu {2}{U}
        this.addAbility(new NinjutsuAbility("{2}{U}"));

        // Whenever Moonblade Shinobi deals combat damage to a player, create a 1/1 blue Illusion creature token with flying.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new CreateTokenEffect(new MelokuTheCloudedMirrorToken()), false
        ));
    }

    private MoonbladeShinobi(final MoonbladeShinobi card) {
        super(card);
    }

    @Override
    public MoonbladeShinobi copy() {
        return new MoonbladeShinobi(this);
    }
}
