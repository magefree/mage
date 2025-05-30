package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.EncoreAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.DragonToken2;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BroodmateTyrant extends CardImpl {

    public BroodmateTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{R}{G}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Broodmate Tyrant enters the battlefield, create a 5/5 red Dragon creature token with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new DragonToken2())));

        // Encore {5}{B}{R}{G}
        this.addAbility(new EncoreAbility(new ManaCostsImpl<>("{5}{B}{R}{G}")));
    }

    private BroodmateTyrant(final BroodmateTyrant card) {
        super(card);
    }

    @Override
    public BroodmateTyrant copy() {
        return new BroodmateTyrant(this);
    }
}
