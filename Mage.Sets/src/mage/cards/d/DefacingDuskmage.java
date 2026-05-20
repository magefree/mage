package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DefacingDuskmage extends PrepareCard {

    public DefacingDuskmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}", "Vandal's Edit", new CardType[]{CardType.INSTANT}, "{1}{W}{B}");

        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever an opponent draws their second card each turn, this creature becomes prepared.
        this.addAbility(new DrawNthCardTriggeredAbility(
                new BecomePreparedSourceEffect(), false, TargetController.OPPONENT, 2
        ));

        // Vandal's Edit
        // Instant {1}{W}{B}
        // Draw two cards. Each player loses 2 life.
        this.getSpellCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellCard().getSpellAbility().addEffect(new LoseLifeAllPlayersEffect(2));
    }

    private DefacingDuskmage(final DefacingDuskmage card) {
        super(card);
    }

    @Override
    public DefacingDuskmage copy() {
        return new DefacingDuskmage(this);
    }
}
