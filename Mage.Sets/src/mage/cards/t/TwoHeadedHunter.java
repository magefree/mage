package mage.cards.t;

import mage.MageInt;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TwoHeadedHunter extends AdventureCard {

    public TwoHeadedHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{4}{R}", "Twice the Rage", "{1}{R}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // Twice the Rage
        // Target creature gains double strike until end of turn.
        this.getSpellCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance()));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        this.finalizeAdventure();
    }

    private TwoHeadedHunter(final TwoHeadedHunter card) {
        super(card);
    }

    @Override
    public TwoHeadedHunter copy() {
        return new TwoHeadedHunter(this);
    }
}
