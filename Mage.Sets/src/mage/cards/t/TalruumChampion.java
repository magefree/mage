package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksOrBlockedByCreatureSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author fireshoes
 */
public final class TalruumChampion extends CardImpl {

    public TalruumChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever Talruum Champion blocks or becomes blocked by a creature, that creature loses first strike until end of turn.
        Effect effect = new LoseAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("that creature loses first strike until end of turn");
        this.addAbility(new BlocksOrBlockedByCreatureSourceTriggeredAbility(effect));
    }

    private TalruumChampion(final TalruumChampion card) {
        super(card);
    }

    @Override
    public TalruumChampion copy() {
        return new TalruumChampion(this);
    }
}
