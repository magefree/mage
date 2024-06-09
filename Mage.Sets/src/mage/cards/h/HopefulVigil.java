package mage.cards.h;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.KnightToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HopefulVigil extends CardImpl {

    public HopefulVigil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // When Hopeful Vigil enters the battlefield, create a 2/2 white Knight creature token with vigilance.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KnightToken())));

        // When Hopeful Vigil is put into a graveyard from the battlefield, scry 2.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new ScryEffect(2, false)));

        // {2}{W}: Sacrifice Hopeful Vigil.
        this.addAbility(new SimpleActivatedAbility(new SacrificeSourceEffect(), new ManaCostsImpl<>("{2}{W}")));
    }

    private HopefulVigil(final HopefulVigil card) {
        super(card);
    }

    @Override
    public HopefulVigil copy() {
        return new HopefulVigil(this);
    }
}
