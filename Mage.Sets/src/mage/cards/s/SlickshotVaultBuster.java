package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CommittedCrimeCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.watchers.common.CommittedCrimeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SlickshotVaultBuster extends CardImpl {

    public SlickshotVaultBuster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Slickshot Vault-Buster gets +2/+0 as long as you've committed a crime this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 0, Duration.WhileOnBattlefield),
                CommittedCrimeCondition.instance, "{this} gets +2/+0 as long as you've committed a crime this turn"
        )).addHint(CommittedCrimeCondition.getHint()), new CommittedCrimeWatcher());
    }

    private SlickshotVaultBuster(final SlickshotVaultBuster card) {
        super(card);
    }

    @Override
    public SlickshotVaultBuster copy() {
        return new SlickshotVaultBuster(this);
    }
}
