package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.condition.common.SourcePreparedCondition;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;
import mage.cards.PrepareCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class StingerquillVoxmancer extends PrepareCard {

    public StingerquillVoxmancer(UUID ownerId, CardSetInfo setInfo) {
        super(
            ownerId, setInfo,
            new CardType[]{CardType.CREATURE}, "{B/R}",
            "Vicious Verse", new CardType[]{CardType.SORCERY}, "{B/R}"
        );

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, if this creature isn't prepared, it becomes prepared.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
            new BecomePreparedSourceEffect(), false
        ).withInterveningIf(SourcePreparedCondition.UNPREPARED));

        // Vicious Verse
        // Sorcery {B/R}
        // Vicious Verse deals 1 damage to target opponent.
        this.getSpellCard().getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellCard().getSpellAbility().addTarget(new TargetOpponent());
    }

    private StingerquillVoxmancer(final StingerquillVoxmancer card) {
        super(card);
    }

    @Override
    public StingerquillVoxmancer copy() {
        return new StingerquillVoxmancer(this);
    }
}
