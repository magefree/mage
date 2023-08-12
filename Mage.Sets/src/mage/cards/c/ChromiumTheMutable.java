package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author TheElk801
 */
public final class ChromiumTheMutable extends CardImpl {

    public ChromiumTheMutable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Discard a card: Until end of turn, Chromium, the Mutable becomes a Human with base power and toughness 1/1, loses all abilities, and gains hexproof. It can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(
                new BecomesCreatureSourceEffect(
                        new ChromiumTheMutableToken(), CardType.CREATURE, Duration.EndOfTurn
                ).andLoseAbilities(true),
                new DiscardCardCost()
        );
        ability.addEffect(
                new CantBeBlockedSourceEffect(Duration.EndOfTurn)
                        .setText("It can't be blocked this turn")
        );
        this.addAbility(ability);
    }

    private ChromiumTheMutable(final ChromiumTheMutable card) {
        super(card);
    }

    @Override
    public ChromiumTheMutable copy() {
        return new ChromiumTheMutable(this);
    }
}

class ChromiumTheMutableToken extends TokenImpl {

    public ChromiumTheMutableToken() {
        super("", "Human with base power and toughness 1/1, loses all abilities, and gains hexproof");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.HUMAN);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(HexproofAbility.getInstance());
    }

    public ChromiumTheMutableToken(final ChromiumTheMutableToken token) {
        super(token);
    }

    public ChromiumTheMutableToken copy() {
        return new ChromiumTheMutableToken(this);
    }
}
