package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.KomasCoilToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KomaWorldEater extends CardImpl {

    public KomaWorldEater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(8);
        this.toughness = new MageInt(12);

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Ward {4}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{4}")));

        // Whenever Koma deals combat damage to a player, create four 3/3 blue Serpent creature tokens named Koma's Coil.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new CreateTokenEffect(new KomasCoilToken(), 4)));
    }

    private KomaWorldEater(final KomaWorldEater card) {
        super(card);
    }

    @Override
    public KomaWorldEater copy() {
        return new KomaWorldEater(this);
    }
}
