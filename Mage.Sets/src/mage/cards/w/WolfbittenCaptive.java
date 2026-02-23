package mage.cards.w;

import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author Loki
 */
public final class WolfbittenCaptive extends TransformingDoubleFacedCard {

    public WolfbittenCaptive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{G}",
                "Krallenhorde Killer",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G"
        );

        // Wolfbitten Captive
        this.getLeftHalfCard().setPT(1, 1);

        // {1}{G}: Wolfbitten Captive gets +2/+2 until end of turn. Activate this ability only once each turn.
        this.getLeftHalfCard().addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2, 2, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{G}")));

        // At the beginning of each upkeep, if no spells were cast last turn, transform Wolfbitten Captive.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Krallenhorde Killer
        this.getRightHalfCard().setPT(2, 2);

        // {3}{G}: Krallenhorde Killer gets +4/+4 until end of turn. Activate this ability only once each turn.
        this.getRightHalfCard().addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(4, 4, Duration.EndOfTurn), new ManaCostsImpl<>("{3}{G}")));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Krallenhorde Killer.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private WolfbittenCaptive(final WolfbittenCaptive card) {
        super(card);
    }

    @Override
    public WolfbittenCaptive copy() {
        return new WolfbittenCaptive(this);
    }
}
