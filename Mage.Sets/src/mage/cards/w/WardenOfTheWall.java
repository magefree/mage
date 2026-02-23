package mage.cards.w;

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
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class WardenOfTheWall extends CardImpl {

    public WardenOfTheWall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Warden of the Wall enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // As long as it's not your turn, Warden of the Wall is a 2/3 Gargoyle artifact creature with flying.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
            new BecomesCreatureSourceEffect(
                new CreatureToken(
                    2, 3, "2/3 Gargoyle artifact creature with flying", SubType.GARGOYLE
                ).withType(CardType.ARTIFACT).withAbility(FlyingAbility.getInstance()),
                CardType.ARTIFACT,
                Duration.WhileOnBattlefield
            ),
            NotMyTurnCondition.instance,
            "During turns other than yours, {this} is a 2/3 Gargoyle artifact creature with flying"
        )).addHint(NotMyTurnHint.instance));
    }

    private WardenOfTheWall(final WardenOfTheWall card) {
        super(card);
    }

    @Override
    public WardenOfTheWall copy() {
        return new WardenOfTheWall(this);
    }
}
