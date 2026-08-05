package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.condition.common.SourcePreparedCondition;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.constants.SubType;
import mage.game.permanent.token.HeartwoodToken;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class WoodworkProdigy extends PrepareCard {

    public WoodworkProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(
            ownerId, setInfo,
            new CardType[]{CardType.CREATURE}, "{2}{R/G}",
            "Soul Tether", new CardType[]{CardType.SORCERY}, "{2}{R/G}"
        );

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, if this creature isn't prepared, it becomes prepared.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
            new BecomePreparedSourceEffect(), false
        ).withInterveningIf(SourcePreparedCondition.UNPREPARED));

        // Soul Tether
        // Sorcery {2}{R/G}
        // Create a Heartwood token. (It’s a red and green artifact with “{T}: Add {R} or {G}.”)
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new HeartwoodToken()));
    }

    private WoodworkProdigy(final WoodworkProdigy card) {
        super(card);
    }

    @Override
    public WoodworkProdigy copy() {
        return new WoodworkProdigy(this);
    }
}
