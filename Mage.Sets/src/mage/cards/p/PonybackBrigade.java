package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrTurnedFaceUpTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.GoblinToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PonybackBrigade extends CardImpl {

    public PonybackBrigade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}{B}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Ponyback Brigade enters the battlefield or is turned face up, create three 1/1 red Goblin creature tokens.
        this.addAbility(new EntersBattlefieldOrTurnedFaceUpTriggeredAbility(new CreateTokenEffect(new GoblinToken(), 3)));

        // Morph {2}{R}{W}{B}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl<>("{2}{R}{W}{B}")));
    }

    private PonybackBrigade(final PonybackBrigade card) {
        super(card);
    }

    @Override
    public PonybackBrigade copy() {
        return new PonybackBrigade(this);
    }
}
