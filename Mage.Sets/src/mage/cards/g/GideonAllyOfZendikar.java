
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.game.command.emblems.GideonAllyOfZendikarEmblem;
import mage.game.permanent.token.KnightAllyToken;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author fireshoes
 */
public final class GideonAllyOfZendikar extends CardImpl {

    public GideonAllyOfZendikar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIDEON);

        this.setStartingLoyalty(4);

        // +1: Until end of turn, Gideon, Ally of Zendikar becomes a 5/5 Human Soldier Ally creature with indestructible that's still a planeswalker. Prevent all damage that would be dealt to him this turn.
        LoyaltyAbility ability = new LoyaltyAbility(new BecomesCreatureSourceEffect(new GideonAllyOfZendikarToken(), "planeswalker", Duration.EndOfTurn), 1);
        Effect effect = new PreventAllDamageToSourceEffect(Duration.EndOfTurn);
        effect.setText("Prevent all damage that would be dealt to him this turn");
        ability.addEffect(effect);
        this.addAbility(ability);

        // 0: Create a 2/2 white Knight Ally creature token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new KnightAllyToken()), 0));

        // -4: You get an emblem with "Creatures you control get +1/+1."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new GideonAllyOfZendikarEmblem()), -4));
    }

    private GideonAllyOfZendikar(final GideonAllyOfZendikar card) {
        super(card);
    }

    @Override
    public GideonAllyOfZendikar copy() {
        return new GideonAllyOfZendikar(this);
    }
}

class GideonAllyOfZendikarToken extends TokenImpl {

    public GideonAllyOfZendikarToken() {
        super("", "5/5 Human Soldier Ally creature with indestructible");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.SOLDIER);
        subtype.add(SubType.ALLY);
        power = new MageInt(5);
        toughness = new MageInt(5);

        addAbility(IndestructibleAbility.getInstance());
    }
    public GideonAllyOfZendikarToken(final GideonAllyOfZendikarToken token) {
        super(token);
    }

    public GideonAllyOfZendikarToken copy() {
        return new GideonAllyOfZendikarToken(this);
    }
}
