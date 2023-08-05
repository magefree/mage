package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.NotMyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.hint.common.NotMyTurnHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class WardenOfTheWall extends CardImpl {

    public WardenOfTheWall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Warden of the Wall enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // As long as it's not your turn, Warden of the Wall is a 2/3 Gargoyle artifact creature with flying.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BecomesCreatureSourceEffect(new GargoyleToken(), CardType.ARTIFACT, Duration.WhileOnBattlefield),
                NotMyTurnCondition.instance,
                "As long as it's not your turn, Warden of the Wall is a 2/3 Gargoyle artifact creature with flying"))
                .addHint(NotMyTurnHint.instance));
    }

    private WardenOfTheWall(final WardenOfTheWall card) {
        super(card);
    }

    @Override
    public WardenOfTheWall copy() {
        return new WardenOfTheWall(this);
    }
}

class GargoyleToken extends TokenImpl {

    public GargoyleToken() {
        super("", "2/3 Gargoyle artifact creature with flying");
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.GARGOYLE);
        power = new MageInt(2);
        toughness = new MageInt(3);
        addAbility(FlyingAbility.getInstance());
    }

    public GargoyleToken(final GargoyleToken token) {
        super(token);
    }

    public GargoyleToken copy() {
        return new GargoyleToken(this);
    }

}
