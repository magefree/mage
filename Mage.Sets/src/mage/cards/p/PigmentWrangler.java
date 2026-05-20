package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.common.delayed.CopyNextSpellDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PigmentWrangler extends PrepareCard {

    public PigmentWrangler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}", "Striking Palette", CardType.SORCERY, "{R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Striking Palette
        // Sorcery {R}
        // When you next cast an instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy.
        this.getSpellCard().getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new CopyNextSpellDelayedTriggeredAbility()));
    }

    private PigmentWrangler(final PigmentWrangler card) {
        super(card);
    }

    @Override
    public PigmentWrangler copy() {
        return new PigmentWrangler(this);
    }
}
