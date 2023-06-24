
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.game.command.emblems.GideonOfTheTrialsEmblem;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;

/**
 *
 * @author JRHerlehy
 */
public final class GideonOfTheTrials extends CardImpl {

    public GideonOfTheTrials(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIDEON);

        //Starting Loyalty: 3
        this.setStartingLoyalty(3);

        // +1: Until your next turn, prevent all damage target permanent would deal.
        Effect effect = new PreventDamageByTargetEffect(Duration.UntilYourNextTurn);
        effect.setText("Until your next turn, prevent all damage target permanent would deal");
        LoyaltyAbility ability = new LoyaltyAbility(effect, 1);
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

        // 0: Until end of turn, Gideon of the Trials becomes a 4/4 Human Soldier creature with indestructible that's still a planeswalker. Prevent all damage that would be dealt to him this turn.
        ability = new LoyaltyAbility(new BecomesCreatureSourceEffect(new GideonOfTheTrialsToken(), CardType.PLANESWALKER, Duration.EndOfTurn), 0);
        effect = new PreventAllDamageToSourceEffect(Duration.EndOfTurn);
        effect.setText("Prevent all damage that would be dealt to him this turn");
        ability.addEffect(effect);
        this.addAbility(ability);

        // 0: You get an emblem with "As long as you control a Gideon planeswalker, you can't lose the game and your opponent can't win the game."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new GideonOfTheTrialsEmblem()), 0));

    }

    private GideonOfTheTrials(final GideonOfTheTrials card) {
        super(card);
    }

    @Override
    public GideonOfTheTrials copy() {
        return new GideonOfTheTrials(this);
    }
}

class GideonOfTheTrialsToken extends TokenImpl {

    public GideonOfTheTrialsToken() {
        super("", "4/4 Human Soldier creature with indestructible");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(4);
        toughness = new MageInt(4);
        this.addAbility(IndestructibleAbility.getInstance());
    }
    public GideonOfTheTrialsToken(final GideonOfTheTrialsToken token) {
        super(token);
    }

    public GideonOfTheTrialsToken copy() {
        return new GideonOfTheTrialsToken(this);
    }
}
