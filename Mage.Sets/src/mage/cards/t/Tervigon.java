package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.RavenousAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TyranidToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Tervigon extends CardImpl {

    public Tervigon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{1}{G}");

        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Ravenous
        this.addAbility(new RavenousAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Spawn Termagants -- Whenever Tervigon deals combat damage to a player, create that many 1/1 green Tyranid creature tokens.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new CreateTokenEffect(new TyranidToken(), SavedDamageValue.MANY), false
        ).withFlavorWord("Spawn Termagants"));
    }

    private Tervigon(final Tervigon card) {
        super(card);
    }

    @Override
    public Tervigon copy() {
        return new Tervigon(this);
    }
}
