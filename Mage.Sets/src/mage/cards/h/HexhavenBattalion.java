package mage.cards.h;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.CadetToken;

/**
 *
 * @author muz
 */
public final class HexhavenBattalion extends CardImpl {

    public HexhavenBattalion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");

        // Create three 2/2 colorless Wizard Soldier creature tokens named Cadet. Empower Jace 2.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new CadetToken(), 3));
        this.getSpellAbility().addEffect(new EmpowerJaceEffect(2));

        // Basic landcycling {2}
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{2}")));

    }

    private HexhavenBattalion(final HexhavenBattalion card) {
        super(card);
    }

    @Override
    public HexhavenBattalion copy() {
        return new HexhavenBattalion(this);
    }
}
