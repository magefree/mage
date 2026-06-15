package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.TreasureToken;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class LukeCageHeroForHire extends CardImpl {

    public LukeCageHeroForHire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of combat on your turn, create a Treasure token.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new CreateTokenEffect(new TreasureToken())));
    }

    private LukeCageHeroForHire(final LukeCageHeroForHire card) {
        super(card);
    }

    @Override
    public LukeCageHeroForHire copy() {
        return new LukeCageHeroForHire(this);
    }
}
