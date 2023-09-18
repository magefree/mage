package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FacelessHaven extends CardImpl {

    public FacelessHaven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.SNOW);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {S}{S}{S}: Faceless Haven becomes a 4/3 creature with vigilance and all creature types until end of turn. It's still a land.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new FacelessHavenToken(), CardType.LAND, Duration.EndOfTurn
        ).setText("{this} becomes a 4/3 creature with vigilance and all creature types until end of turn. It's still a land"), new ManaCostsImpl<>("{S}{S}{S}")));
    }

    private FacelessHaven(final FacelessHaven card) {
        super(card);
    }

    @Override
    public FacelessHaven copy() {
        return new FacelessHaven(this);
    }
}

class FacelessHavenToken extends TokenImpl {

    FacelessHavenToken() {
        super("", "4/3 creature with vigilance and all creature types");
        cardType.add(CardType.CREATURE);
        subtype.setIsAllCreatureTypes(true);
        power = new MageInt(4);
        toughness = new MageInt(3);
        addAbility(VigilanceAbility.getInstance());
    }

    private FacelessHavenToken(final FacelessHavenToken token) {
        super(token);
    }

    public FacelessHavenToken copy() {
        return new FacelessHavenToken(this);
    }
}
