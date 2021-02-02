package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.DeathpactAngelToken;

/**
 *
 * @author Plopman
 */
public final class DeathpactAngel extends CardImpl {

    public DeathpactAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}{B}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        //Flying
        this.addAbility(FlyingAbility.getInstance());
        //When Deathpact Angel dies, create a 1/1 white and black Cleric creature token. It has "{3}{W}{B}{B}, {T}, Sacrifice this creature: Return a card named Deathpact Angel from your graveyard to the battlefield."
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new DeathpactAngelToken())));
    }

    private DeathpactAngel(final DeathpactAngel card) {
        super(card);
    }

    @Override
    public DeathpactAngel copy() {
        return new DeathpactAngel(this);
    }
}
