
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author fireshoes
 */
public final class GideonMartialParagon extends CardImpl {

    public GideonMartialParagon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{W}");
        this.addSuperType(SuperType.LEGENDARY);

        this.subtype.add(SubType.GIDEON);

        this.setStartingLoyalty(5);

        // +2: Untap all creatures you control. Those creatures get +1/+1 until end of turn.
        LoyaltyAbility ability = new LoyaltyAbility(new UntapAllEffect(new FilterControlledCreaturePermanent()), 2);
        Effect effect = new BoostControlledEffect(1, 1, Duration.EndOfTurn);
        effect.setText("Those creatures get +1/+1 until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);

        // 0: Until end of turn, Gideon, Martial Paragon, becomes a 5/5 Human Soldier creature with indestructible that's still a planeswalker.
        // Prevent all damage that would be dealt to him this turn.
        ability = new LoyaltyAbility(new BecomesCreatureSourceEffect(new GideonMartialParagonToken(), "planeswalker", Duration.EndOfTurn), 0);
        effect = new PreventAllDamageToSourceEffect(Duration.EndOfTurn);
        effect.setText("Prevent all damage that would be dealt to him this turn");
        ability.addEffect(effect);
        this.addAbility(ability);

        // -10: Creatures you control get +2/+2 until end of turn. Tap all creatures your opponents control.
        ability = new LoyaltyAbility(new BoostControlledEffect(2, 2, Duration.EndOfTurn), -10);
        effect = new TapAllEffect(new FilterOpponentsCreaturePermanent());
        effect.setText("Tap all creatures your opponents control");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private GideonMartialParagon(final GideonMartialParagon card) {
        super(card);
    }

    @Override
    public GideonMartialParagon copy() {
        return new GideonMartialParagon(this);
    }
}

class GideonMartialParagonToken extends TokenImpl {

    public GideonMartialParagonToken() {
        super("", "5/5 Human Soldier creature with indestructible");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(5);
        toughness = new MageInt(5);

        addAbility(IndestructibleAbility.getInstance());
    }
    public GideonMartialParagonToken(final GideonMartialParagonToken token) {
        super(token);
    }

    public GideonMartialParagonToken copy() {
        return new GideonMartialParagonToken(this);
    }
}
