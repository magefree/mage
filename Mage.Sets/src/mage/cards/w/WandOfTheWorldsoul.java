package mage.cards.w;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.NextSpellCastHasAbilityEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class WandOfTheWorldsoul extends CardImpl {

    public WandOfTheWorldsoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");

        // Wand of the Worldsoul enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // {T}: The next spell you cast this turn has convoke.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new NextSpellCastHasAbilityEffect(new ConvokeAbility()),
                new TapSourceCost()));
    }

    private WandOfTheWorldsoul(final WandOfTheWorldsoul card) {
        super(card);
    }

    @Override
    public WandOfTheWorldsoul copy() {
        return new WandOfTheWorldsoul(this);
    }
}
